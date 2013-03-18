package roomplanner

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult

import org.drools.planner.config.SolverFactory
import org.drools.planner.config.XmlSolverFactory
import org.drools.planner.core.Solver
import org.drools.planner.core.score.director.ScoreDirector

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.apache.cxf.interceptor.InInterceptors
import org.apache.cxf.interceptor.OutInterceptors

import roomplanner.utils.CustomLoggingInInterceptor
import roomplanner.utils.CustomLoggingOutInterceptor

// @GrailsCxfEndpoint(
// 	importInterceptors  = ["customLoggingInInterceptor"],
// 	outInterceptors = ["customLoggingOutInterceptor"]
// )
//@InInterceptors( classes = [CustomLoggingInInterceptor])
//@OutInterceptors( classes = [CustomLoggingOutInterceptor])
class RoomPlannerService {

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
    Plan doPlan(
		@WebParam(name="roomList") List<Room> rooms, 
		@WebParam(name="roomCategoryList") List<RoomCategory> roomCategories, 
		@WebParam(name="reservationList") List<Reservation> reservations, 
		@WebParam(name="roomAssignmentList") List<RoomAssignment> roomAssignments
		) {

		Plan plan = new Plan();

		if (rooms == null) { rooms = new ArrayList<Room>() }
		if (roomCategories == null) { roomCategories = new ArrayList<RoomCategory>() }
		if (reservations == null) { reservations = new ArrayList<Reservation>() }
		if (roomAssignments == null) { roomAssignments = new ArrayList<RoomAssignment>() }
				 
		log.trace("Rooms: " + rooms)
		log.trace("RoomCategories: " + roomCategories)
		log.trace("Reservations: " + reservations)
		log.trace("RoomAssignments: " + roomAssignments)

		try {

			// log.debug("Build solver")
			// SolverFactory solverFactory = new XmlSolverFactory("/drools/roomScheduleSolverConfig.xml");
			// Solver solver = solverFactory.buildSolver();

			Schedule unsolvedSchedule = new Schedule()
			Schedule solvedSchedule = null

			// add problem facts to a solution
			log.trace("Add problem facts")

			unsolvedSchedule.rooms.addAll(rooms)
			unsolvedSchedule.roomCategories.addAll(roomCategories)
			unsolvedSchedule.reservations.addAll(reservations)
			unsolvedSchedule.roomAssignments.addAll(roomAssignments)
			createRoomAssignmentList(unsolvedSchedule)	
			
			synchronized (this) {
				log.trace("Get solver from applicationContext")	 
				Solver solver = grailsApplication.config.solver

				// unsolvedSchedule.scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector();
				unsolvedSchedule.scoreDirector = grailsApplication.config.scoreDirector
				 
				// start solving	
				log.trace("Start solving")

				unsolvedSchedule.scoreDirector.setWorkingSolution(unsolvedSchedule);
				solver.setPlanningProblem(unsolvedSchedule);
				solver.solve();
			 
				solvedSchedule = (Schedule) solver.getBestSolution();

				// get constraints info
				// solvedSchedule.scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector();
				solvedSchedule.scoreDirector = grailsApplication.config.scoreDirector
				solvedSchedule.scoreDirector.setWorkingSolution(solvedSchedule)
				solvedSchedule.scoreDirector.calculateScore()
			} // synchronized

			log.trace("Build score object")

			plan.roomAssignments = solvedSchedule.roomAssignments
			plan.score = new Score(
				 feasible: solvedSchedule.score.feasible,
				 hardScoreConstraints: solvedSchedule.score.hardScore,
				 softScoreConstraints: solvedSchedule.score.softScore
				 //scoreDetails: solvedSchedule.getScoreDetailList()   
			)
			log.debug("Score: [${plan.score.hardScoreConstraints}hard/${plan.score.softScoreConstraints}soft] Feasible: ${plan.score.feasible}")
	 
		} catch (Throwable e) {
			log.error("Error solving", e)
		}
		finally {
			new PlannerRequest().save()
			return plan
		}
    }
	
	private void createRoomAssignmentList(Schedule schedule) {
		List<Reservation> reservationList = schedule.reservations;
		List<RoomAssignment> roomAssignmentList = new ArrayList<RoomAssignment>(reservationList.size());
		long id = 0L;
		reservationList.each() { reservation ->
			RoomAssignment roomAssignment = new RoomAssignment();
			roomAssignment.setId(id);
			id++;
			roomAssignment.setReservation(reservation);
			roomAssignment.moveable = true;
			// Notice that we leave the PlanningVariable properties on null
			roomAssignmentList.add(roomAssignment);
		}
		schedule.roomAssignments.addAll(roomAssignmentList);
	}

}
