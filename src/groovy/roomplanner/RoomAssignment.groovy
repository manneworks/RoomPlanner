package roomplanner

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

import org.optaplanner.core.api.domain.entity.PlanningEntity
import org.optaplanner.core.api.domain.variable.PlanningVariable
import org.optaplanner.core.api.domain.value.ValueRange
import org.optaplanner.core.api.domain.value.ValueRangeType

@PlanningEntity(/*difficultyWeightFactoryClass = QueenDifficultyWeightFactory.class)*/)
@XmlAccessorType(XmlAccessType.NONE)
class RoomAssignment implements Serializable {

	@XmlAttribute	Long id
	@XmlElement		Room room
	@XmlElement		Reservation reservation
	@XmlElement		Boolean moveable = true

	
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

	@Override
	public String toString() {
		return reservation.toString() + " @ " + room.toString();
	}

}
