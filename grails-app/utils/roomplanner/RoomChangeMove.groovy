package roomplanner

import groovy.transform.EqualsAndHashCode

import org.apache.commons.lang.ObjectUtils

import org.optaplanner.core.impl.heuristic.move.Move
import org.optaplanner.core.impl.score.director.ScoreDirector

@EqualsAndHashCode
class RoomChangeMove implements Move {

	RoomAssignment roomAssignment
	Room toRoom

	public RoomChangeMove(RoomAssignment roomAssignment, Room toRoom) {
		this.roomAssignment = roomAssignment;
		this.toRoom = toRoom;
	}

	@Override
	public Move createUndoMove(ScoreDirector scoreDirector) {
		return new RoomChangeMove(roomAssignment, roomAssignment.getRoom());
	}

	@Override
	public void doMove(ScoreDirector scoreDirector) {
		RoomChangeMoveHelper.moveRoomAssignment(scoreDirector, roomAssignment, toRoom)
	}

	@Override
	public Collection<? extends Object> getPlanningEntities() {
		return Collections.singletonList(roomAssignment);
	}

	@Override
	public Collection<? extends Object> getPlanningValues() {
		return Collections.singletonList(toRoom);
	}

	@Override
	public boolean isMoveDoable(ScoreDirector scoreDirector) {
		boolean doable = (roomAssignment.moveable && !ObjectUtils.equals(roomAssignment.getRoom(), toRoom));
		return doable
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Move Room " + roomAssignment.getRoom().getId().toString() + " => Room " + toRoom.getId().toString()
	}
}