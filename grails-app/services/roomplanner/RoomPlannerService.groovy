package roomplanner

import org.drools.planner.config.SolverFactory
import org.drools.planner.config.XmlSolverFactory
import org.drools.planner.core.Solver

class RoomPlannerService {

	static expose = ['cxf']
	
	/**
	 * 
	 * @param rooms
	 * @param roomCategories
	 * @param reservations
	 * @return
	 */
    Schedule doPlan(List<Room> rooms, List<RoomCategory> roomCategories, List<Reservation> reservations) {
		 Schedule solvedSchedule = null;
		 try {
		 SolverFactory solverFactory = new XmlSolverFactory("/roomScheduleSolverConfig.xml");
		 Solver solver = solverFactory.buildSolver();
 
		 Schedule unsolvedSchedule = new Schedule();
		 
		 solver.setPlanningProblem(unsolvedSchedule);
		 solver.solve();
	 
		 solvedSchedule = (Schedule) solver.getBestSolution();
		 
		 } catch (Throwable e) {
			 e.printStackTrace();
		 }
		 finally {
			 return solvedSchedule;
		 }
    }
}
