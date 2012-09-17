package roomplanner

import org.drools.planner.core.score.director.ScoreDirector

class RoomChangeMoveHelper {

	public static void moveRoomAssignment(ScoreDirector scoreDirector, RoomAssignment roomAssignment,
	Room toRoom) {
		scoreDirector.beforeVariableChanged(roomAssignment, "roomAssignment");
		roomAssignment.setRoom(toRoom);
		scoreDirector.afterVariableChanged(roomAssignment, "roomAssignemt");
	}

	private RoomChangeMoveHelper() {
	}
}
