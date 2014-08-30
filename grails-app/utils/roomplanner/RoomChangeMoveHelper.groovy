package roomplanner

import org.optaplanner.core.impl.score.director.ScoreDirector

class RoomChangeMoveHelper {

	public static void moveRoomAssignment(ScoreDirector scoreDirector, RoomAssignment roomAssignment,
	Room toRoom) {
		scoreDirector.beforeVariableChanged(roomAssignment, "room");
		roomAssignment.setRoom(toRoom);
		scoreDirector.afterVariableChanged(roomAssignment, "room");
	}

	private RoomChangeMoveHelper() {
	}
}
