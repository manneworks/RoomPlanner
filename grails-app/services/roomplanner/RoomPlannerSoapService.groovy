package roomplanner

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult

import org.optaplanner.core.api.solver.SolverFactory
import org.optaplanner.core.config.solver.XmlSolverFactory
import org.optaplanner.core.api.solver.Solver
import org.optaplanner.core.impl.score.director.ScoreDirector

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.apache.cxf.interceptor.InInterceptors
import org.apache.cxf.interceptor.OutInterceptors

import roomplanner.utils.CustomLoggingInInterceptor
import roomplanner.utils.CustomLoggingOutInterceptor

import roomplanner.api.RoomCategory as RoomCategoryDto
import roomplanner.api.Room as RoomDto
import roomplanner.api.Reservation as ReservationDto
import roomplanner.api.RoomAssignment as RoomAssignmentDto
import roomplanner.api.Plan as PlanDto
import roomplanner.api.Score as ScoreDto

// @GrailsCxfEndpoint(
// 	importInterceptors  = ["customLoggingInInterceptor"],
// 	outInterceptors = ["customLoggingOutInterceptor"]
// )
//@InInterceptors( classes = [CustomLoggingInInterceptor])
//@OutInterceptors( classes = [CustomLoggingOutInterceptor])
class RoomPlannerSoapService {

	static expose = EndpointType.JAX_WS

	def grailsApplication
	
	/**
	 * 
	 * @param rooms
	 * @param roomCategories
	 * @param reservations
	 * @param roomAssignments
	 * @return
	 */
	@WebMethod( operationName = 'doPlan' )
	@WebResult( name = 'plan' )
    PlanDto doPlan(
		@WebParam(name="roomList") List<RoomDto> roomsDto, 
		@WebParam(name="roomCategoryList") List<RoomCategoryDto> roomCategoriesDto, 
		@WebParam(name="reservationList") List<ReservationDto> reservationsDto, 
		@WebParam(name="roomAssignmentList") List<RoomAssignmentDto> roomAssignmentsDto
		) {

		def roomCategories = roomCategoriesDto?.collect { roomCategory ->
			new RoomCategory( 
				id: roomCategory.id
			) 
		}
		def rooms = roomsDto?.collect { room ->
			new Room( 
				id: room.id,
				roomCategory: roomCategories.find { it.id == room.roomCategory.id },
				adults: room.adults
			) 
		}
		def reservations = reservationsDto?.collect { reservation ->
			new Reservation(
				id: reservation.id,
				roomCategory: roomCategories.find { it.id == reservation.roomCategory.id },
				adults: reservation.adults,
				bookingInterval: reservation.bookingInterval
			) 
		}
		def roomAssignments = roomAssignmentsDto?.collect { roomAssignment ->
			new RoomAssignment( 
				id: roomAssignment.id,
				room: rooms.find { it.id == roomAssignment.room.id },
				reservation:  reservations.find { it.id == roomAssignment.reservation.id },
				moveable: false
			) 
		}
		def planDto = new PlanDto()

		if (rooms == null) { rooms = new ArrayList<Room>() }
		if (roomCategories == null) { roomCategories = new ArrayList<RoomCategory>() }
		if (reservations == null) { reservations = new ArrayList<Reservation>() }
		if (roomAssignments == null) { roomAssignments = new ArrayList<RoomAssignment>() }
				 
		log.trace("Rooms: " + rooms)
		log.trace("RoomCategories: " + roomCategories)
		log.trace("Reservations: " + reservations)
		log.trace("RoomAssignments: " + roomAssignments)

		// printClassPath(this.class.classLoader)

		try {

			Schedule unsolvedSchedule = new Schedule()
			Schedule solvedSchedule = null

			log.trace("Add problem facts")

			unsolvedSchedule.rooms.addAll(rooms)
			unsolvedSchedule.roomCategories.addAll(roomCategories)
			unsolvedSchedule.reservations.addAll(reservations)
			unsolvedSchedule.roomAssignments.addAll(roomAssignments)
			createRoomAssignmentList(unsolvedSchedule)	
			
			synchronized (this) {
				Solver solver = null

				def configValue = grailsApplication.config.solverObject

				if (configValue) {
					log.trace("Get solver from applicationContext")	 
					solver = configValue
				} else {
		    		log.trace("Configure solver")
					SolverFactory solverFactory = new XmlSolverFactory()
					
					try {
						// InputStream xmlConfigStream = this.getClass().getResourceAsStream(
						// 	grailsApplication.config.solver.configurationXML
						// )
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

			log.trace("Build score object")

			planDto.roomAssignments = solvedSchedule.roomAssignments
			plan.score = new ScoreDto(
				 feasible: solvedSchedule.score.feasible,
				 hardScoreConstraints: solvedSchedule.score.hardScore,
				 softScoreConstraints: solvedSchedule.score.softScore
				 //scoreDetails: solvedSchedule.getScoreDetailList()   
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
			log.debug("Score: [${plan.score.hardScoreConstraints}hard/${plan.score.softScoreConstraints}soft] Feasible: ${plan.score.feasible}")
	 
		} catch (Throwable e) {
			log.error("Error solving", e)
		}
		finally {
			new PlannerRequest().save()
		}
		planDto
    }
	
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

	void printClassPath(def classLoader) {
	    def urlPaths = classLoader.getURLs()
	    println "classLoader: $classLoader"
	    println urlPaths*.toString()
	    if (classLoader.parent) {
	        printClassPath(classLoader.parent)
	    }
	}

}