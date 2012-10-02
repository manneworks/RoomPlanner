package roomplanner

import org.drools.planner.config.SolverFactory
import org.drools.planner.config.XmlSolverFactory
import org.drools.planner.core.Solver
import org.drools.planner.core.score.director.ScoreDirector

class RoomPlannerService {

	static expose = ['cxf']
	
	/**
	 * 
	 * @param rooms
	 * @param roomCategories
	 * @param reservations
	 * @param roomAssignments
	 * @return
	 */
    Schedule doPlan(List<Room> rooms, List<RoomCategory> roomCategories, List<Reservation> reservations, List<RoomAssignment> roomAssignments) {
		 Schedule solvedSchedule = null;
		 
		 try {
		 SolverFactory solverFactory = new XmlSolverFactory("/roomScheduleSolverConfig.xml");
		 Solver solver = solverFactory.buildSolver();
 
		 Schedule unsolvedSchedule = new Schedule();
		 
		 unsolvedSchedule.scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector();
		 
		 // add problem facts to a solution
		 unsolvedSchedule.rooms.addAll(rooms)
		 unsolvedSchedule.roomCategories.addAll(roomCategories)
		 unsolvedSchedule.reservations.addAll(reservations)
		 unsolvedSchedule.roomAssignments.addAll(roomAssignments)
		 createRoomAssignmentList(unsolvedSchedule)	
		 
		 // start solving		
		 unsolvedSchedule.scoreDirector.setWorkingSolution(unsolvedSchedule);
		 solver.setPlanningProblem(unsolvedSchedule);
		 solver.solve();
	 
		 solvedSchedule = (Schedule) solver.getBestSolution();

		 // get constraints info
		 solvedSchedule.scoreDirector = solver.getScoreDirectorFactory().buildScoreDirector();
		 solvedSchedule.scoreDirector.setWorkingSolution(solvedSchedule)
		 solvedSchedule.scoreDirector.calculateScore()
 
		 } catch (Throwable e) {
			 e.printStackTrace();
		 }
		 finally {
			 return solvedSchedule;
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
