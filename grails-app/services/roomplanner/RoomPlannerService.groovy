package roomplanner

import org.optaplanner.core.api.solver.SolverFactory
import org.optaplanner.core.config.solver.XmlSolverFactory
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.impl.score.director.ScoreDirector

import roomplanner.api.RoomCategory as RoomCategoryDto
import roomplanner.api.Room as RoomDto
import roomplanner.api.Reservation as ReservationDto
import roomplanner.api.RoomAssignment as RoomAssignmentDto
import roomplanner.api.Plan as PlanDto
import roomplanner.api.Score as ScoreDto
import roomplanner.api.ScoreDetail as ScoreDetailDto
import roomplanner.api.Pricelist as PricelistDto
import roomplanner.api.PricelistItem as PricelistItemDto
import roomplanner.api.Status as StatusDto

class RoomPlannerService {

	def grailsApplication
	def adminService

    /**

    */
    def doPlan(def license, def roomCategoriesDto, def roomsDto, def reservationsDto, def roomAssignmentsDto, def pricelistDto) {

		def startTime = System.currentTimeMillis()

		def (roomCategories, rooms, reservations, roomAssignments) = 
			convertFromDto(
				roomCategoriesDto, roomsDto, reservationsDto, roomAssignmentsDto
			)

		log.trace("Rooms: " + rooms)
		log.trace("RoomCategories: " + roomCategories)
		log.trace("Reservations: " + reservations)
		log.trace("RoomAssignments: " + roomAssignments)

		def planDto

		try {
			Schedule solvedSchedule = solveProblem(roomCategories, rooms, reservations, roomAssignments)
			planDto = buildDtoResponse(solvedSchedule, roomsDto, reservationsDto)

			log.debug("Score: [${planDto.score.hardScoreConstraints}hard/${planDto.score.softScoreConstraints}soft] Feasible: ${planDto.score.feasible}")
		} catch (Throwable e) {
			log.error("Error solving", e)
		}
		finally {
			def endTime = System.currentTimeMillis()

			new PlannerRequest(
				licenseKey: license.key,
				timestamp: startTime,
				requestDuration: (endTime - startTime)
				).save(flush:true)
		}
		planDto

    }

    /**

    */
    def getStatus() {

    	def status = adminService.getStatus()

    	new StatusDto(
    		uptime: status.uptime,
    		requestsServed: status.requestsServed
    		)
    }

    /**

    */
	private def convertFromDto (
		List<RoomCategoryDto> roomCategoriesDto, 
		List<RoomDto> roomsDto, 
		List<ReservationDto> reservationsDto, 
		List<RoomAssignmentDto> roomAssignmentsDto
	) {
		def roomCategories = roomCategoriesDto.collect { roomCategory ->
			new RoomCategory( 
				id: roomCategory.id
			) 
		}
		def rooms = roomsDto.collect { room ->
			new Room( 
				id: room.id,
				roomCategory: roomCategories.find { it.id == room.roomCategory.id },
				adults: room.adults
			) 
		}
		def reservations = reservationsDto.collect { reservation ->
			new Reservation(
				id: reservation.id,
				roomCategory: roomCategories.find { it.id == reservation.roomCategory.id },
				adults: reservation.adults,
				bookingInterval: reservation.bookingInterval
			) 
		}
		def roomAssignments = roomAssignmentsDto.collect { roomAssignment ->
			new RoomAssignment( 
				id: roomAssignment.id,
				room: rooms.find { it.id == roomAssignment.room.id },
				reservation:  reservations.find { it.id == roomAssignment.reservation.id },
				moveable: false
			) 
		}

		[ roomCategories, rooms, reservations, roomAssignments ]
	}

    /**

    */
	private PlanDto buildDtoResponse(
			Schedule solvedSchedule, 
			List<RoomDto> roomsDto, 
			List<ReservationDto> reservationsDto
	) {
		PlanDto planDto = new PlanDto()

		def constraintsMatchList = []
		solvedSchedule.getScoreDetailList().each { scoreDetail ->
			def roomAssignments = []
			scoreDetail.roomAssignments.each { roomAssignment ->
				roomAssignments << 
			new RoomAssignmentDto(
				id: roomAssignment.id,
				room: roomsDto.find { it.id == roomAssignment.room.id },
				reservation: reservationsDto.find { it.id == roomAssignment.reservation.id },
				moveable: roomAssignment.moveable
				)
			}
			constraintsMatchList <<
				new ScoreDetailDto(
					constraintName: scoreDetail.constraintName,
					roomAssignments: roomAssignments,
					weight: scoreDetail.weight
					)
		}

		planDto.score = new ScoreDto(
			 feasible: solvedSchedule.score.feasible,
			 hardScoreConstraints: solvedSchedule.score.hardScore,
			 softScoreConstraints: solvedSchedule.score.softScore,
			 scoreDetails: constraintsMatchList
		)
		planDto.roomAssignments = []
		solvedSchedule.roomAssignments.each { roomAssignment ->
		planDto.roomAssignments <<
			new RoomAssignmentDto(
				id: roomAssignment.id,
				room: roomsDto.find { it.id == roomAssignment.room.id },
				reservation: reservationsDto.find { it.id == roomAssignment.reservation.id },
				moveable: roomAssignment.moveable
				)
		}

		planDto
	}

    /**

    */
	private Schedule solveProblem(def roomCategories, def rooms, def reservations, def roomAssignments) {

		Schedule solvedSchedule = null
		try {

			Schedule unsolvedSchedule = new Schedule()

			log.trace("Add problem facts")

			unsolvedSchedule.rooms.addAll(rooms)
			unsolvedSchedule.roomCategories.addAll(roomCategories)
			unsolvedSchedule.reservations.addAll(reservations)
			unsolvedSchedule.roomAssignments.addAll(roomAssignments)
			createRoomAssignmentList(unsolvedSchedule)	
			
			//printClassPath(this.getClass().getClassLoader())
/**/
			Solver solver = null
			synchronized (this) {

				def configValue = grailsApplication.config.solverObject

				if (configValue) {
					log.trace("Get solver from applicationContext")	 
					solver = configValue
				} else {
		    		log.trace("Configure solver")
					SolverFactory solverFactory = new XmlSolverFactory()

					try {
 						solverFactory.configure(grailsApplication.config.solver.configurationXML)
					} catch (Exception e) {
						log.error("Cannot configure solver: " + e.message)
						throw new Exception()
					}
					
		    		log.trace("Build solver")
					solver = solverFactory.buildSolver()

		    		log.trace("Build scoreDirector")
					ScoreDirector scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector();

		    		log.trace("Store solver in grailsApplication")
					grailsApplication.config.solverObject = solver
					grailsApplication.config.scoreDirectorObject = scoreDirector
				}

				unsolvedSchedule.scoreDirector = grailsApplication.config.scoreDirectorObject

				log.trace("Start solving")

				unsolvedSchedule.scoreDirector.setWorkingSolution(unsolvedSchedule);
				solver.setPlanningProblem(unsolvedSchedule);
				solver.solve();
			 
				solvedSchedule = (Schedule) solver.getBestSolution();

				log.trace("Get constraints info")
				solvedSchedule.scoreDirector = grailsApplication.config.scoreDirectorObject
				solvedSchedule.scoreDirector.setWorkingSolution(solvedSchedule)
				solvedSchedule.scoreDirector.calculateScore()
			} // synchronized
/**/			
		} catch (Exception e) {
			log.error("Error solving", e)
		}
		solvedSchedule
	}

    /**

    */
	private void createRoomAssignmentList(Schedule schedule) {
		List<Reservation> reservationList = schedule.reservations;
		List<RoomAssignment> roomAssignmentList = new ArrayList<RoomAssignment>(reservationList.size());
		long id = 0L;
		reservationList.each() { reservation ->
			RoomAssignment roomAssignment = new RoomAssignment();
			roomAssignment.id = id;
			id++;
			roomAssignment.reservation = reservation;
			roomAssignment.moveable = true;
			// Notice that we leave the PlanningVariable properties on null
			roomAssignmentList.add(roomAssignment);
		}
		schedule.roomAssignments.addAll(roomAssignmentList);
	}

    /**

    */
	private void printClassPath(def classLoader) {
		
		if (classLoader == null) {
        	classLoader = ClassLoader.getSystemClassLoader();
 	    }

	    def urlPaths = classLoader.getURLs()
	    println "classLoader: $classLoader"
	    println urlPaths*.toString()
	    if (classLoader.parent) {
	        printClassPath(classLoader.parent)
	    }
	}

}
