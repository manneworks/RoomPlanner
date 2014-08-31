package roomplanner

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable

import com.thoughtworks.xstream.annotations.XStreamAlias

import roomplanner.solver.RoomStrengthComparator
import roomplanner.solver.RoomAssignmentDifficultyWeightFactory

@PlanningEntity(difficultyWeightFactoryClass = RoomAssignmentDifficultyWeightFactory.class)
@XStreamAlias("RoomAssignment")
class RoomAssignment {

	Long id
	Room room
	Reservation reservation
	Boolean moveable = true

	@PlanningVariable(valueRangeProviderRefs=["roomRange"], strengthComparatorClass = RoomStrengthComparator.class)
	public Room getRoom() {
		return room
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RoomAssignment clone() {
//		RoomAssignment clone = new RoomAssignment();
//		clone.id = id;
//		clone.reservation = reservation;
//		clone.room = room;
//		clone.moveable = moveable;
//		return clone;
		
		RoomAssignment.metaClass.getProperties().findAll() { it.getSetter() != null }
			.inject( new RoomAssignment() ) { roomAssignment, metaProp ->
				metaProp.setProperty(roomAssignment, metaProp.getProperty(this))
				roomAssignment
			}
	}

	/**
	 * The normal methods {@link #equals(Object)} and {@link #hashCode()} cannot be used because the rule engine already
	 * requires them (for performance in their original state).
	 * @see #solutionHashCode()
	 */
	public boolean solutionEquals(Object o) {
		if (this.is(o)) {
			return true;
		} else if (o instanceof RoomAssignment) {
			RoomAssignment other = (RoomAssignment) o;
			return new EqualsBuilder()
					.append(id, other.id)
					.append(reservation, other.reservation)
					.append(room, other.room)
					.append(moveable, other.moveable)
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
				.append(moveable)
				.toHashCode();
	}

}
