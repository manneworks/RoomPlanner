package roomplanner

import org.apache.commons.lang.ObjectUtils
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import org.drools.planner.core.move.Move
import org.drools.planner.core.score.director.ScoreDirector

public class RoomChangeMove implements Move {

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
        return !ObjectUtils.equals(roomAssignment.getRoom(), toRoom);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(roomAssignment)
		.append(toRoom)
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o instanceof RoomChangeMove) {
			RoomChangeMove other = (RoomChangeMove) o;
			return new EqualsBuilder()
			.append(roomAssignment, other.roomAssignment)
			.append(toRoom, other.toRoom)
			.isEquals();
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return roomAssignment.toString() + " to " + toRoom.toString()
	}
}