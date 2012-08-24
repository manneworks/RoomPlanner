package roomplanner

import org.drools.planner.api.domain.variable.PlanningVariable
import org.drools.planner.api.domain.variable.ValueRange
import org.drools.planner.api.domain.variable.ValueRangeType

class RoomAssignment {

	Room room
	Reservation reservation

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

}
