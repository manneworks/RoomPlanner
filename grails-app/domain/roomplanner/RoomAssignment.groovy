package roomplanner

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import org.drools.planner.api.domain.entity.PlanningEntity
import org.drools.planner.api.domain.variable.PlanningVariable
import org.drools.planner.api.domain.variable.ValueRange
import org.drools.planner.api.domain.variable.ValueRangeType

@PlanningEntity(/*difficultyWeightFactoryClass = QueenDifficultyWeightFactory.class)*/)
class RoomAssignment {

	Room room
	Reservation reservation
	//Boolean movable = true

    static constraints = {
    }
	
	@PlanningVariable(/*strengthComparatorClass = RoomStrengthComparator.class*/)
	@ValueRange(type = ValueRangeType.FROM_SOLUTION_PROPERTY, solutionProperty = "rooms")
	public Room getRoom() {
		return room
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RoomAssignment clone() {
		RoomAssignment clone = new RoomAssignment();
		clone.id = id;
		clone.reservation = reservation;
		clone.room = room;
		return clone;
	}

	/**
	 * The normal methods {@link #equals(Object)} and {@link #hashCode()} cannot be used because the rule engine already
	 * requires them (for performance in their original state).
	 * @see #solutionHashCode()
	 */
	public boolean solutionEquals(Object o) {
		if (this == o) {
			return true;
		} else if (o instanceof RoomAssignment) {
			RoomAssignment other = (RoomAssignment) o;
			return new EqualsBuilder()
					.append(id, other.id)
					.append(reservation, other.reservation)
					.append(room, other.room)
					.isEquals();
		} else {
			return false;
		}
	}

	/**
	 * The normal methods {@link #equals(Object)} and {@link #hashCode()} cannot be used because the rule engine already
	 * requires them (for performance in their original state).
	 * @see #solutionEquals(Object)
	 */
	public int solutionHashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(reservation)
				.append(room)
				.toHashCode();
	}

	@Override
	public String toString() {
		return reservation + " @ " + room;
	}

}
