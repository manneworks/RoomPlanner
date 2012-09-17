package initializer

import org.drools.planner.core.phase.custom.CustomSolverPhaseCommand
import org.drools.planner.core.score.director.ScoreDirector

import roomplanner.RoomAssignment
import roomplanner.Schedule

class SolutionInitializer implements CustomSolverPhaseCommand {

	@Override
	public void changeWorkingSolution(ScoreDirector scoreDirector) {
		Schedule schedule = (Schedule) scoreDirector.getWorkingSolution();
		initializeRoomAssignmentList(scoreDirector, schedule);
	}

	private void initializeRoomAssignmentList(ScoreDirector scoreDirector, Schedule schedule) {
		schedule.reservations.each() { 	schedule.roomAssignments.add(new RoomAssignment(room: schedule.rooms.get(0), reservation: it)) }
	}
}
