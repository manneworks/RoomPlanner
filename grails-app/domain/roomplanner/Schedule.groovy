package roomplanner

import org.apache.commons.lang.builder.HashCodeBuilder
import org.drools.planner.api.domain.solution.PlanningEntityCollectionProperty
import org.drools.planner.core.score.buildin.hardandsoft.HardAndSoftScore
import org.drools.planner.core.solution.Solution

class Schedule implements Solution<HardAndSoftScore> {

	HardAndSoftScore score
	
	private List<Room> rooms = new ArrayList<Room>()
	private List<Reservation> reservations = new ArrayList<Reservation>()
	private List<RoomCategory> roomCategories = new ArrayList<RoomCategory>()
	private List<RoomAssignment> roomAssignments = new ArrayList<RoomAssignment>()
	
    static constraints = {
    }
	
	@PlanningEntityCollectionProperty
	public List<RoomAssignment> getRoomAssignments() {
		return roomAssignments;
	}
	
	public void setRoomAssignments(List<RoomAssignment> roomAssignments) {
		this.roomAssignments = roomAssignments
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms
	}

	public List<RoomCategory> getRoomCategories() {
		return roomCategories;
	}
	
	public void setRoomCategries(List<RoomCategory> roomCategories) {
		this.roomCategories= roomCategories
	}

	public List<Reservation> getReservations() {
		return reservations;
	}
	
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations
	}

	public HardAndSoftScore getScore() {
		return score;
	}

	public void setScore(HardAndSoftScore score) {
		this.score = score;
	}

	/* (non-Javadoc)
	 * @see org.drools.planner.core.solution.Solution#cloneSolution()
	 */
	@Override
	public Schedule cloneSolution() {
        Schedule clone = new Schedule();
        clone.id = id;
        clone.rooms = rooms;
        clone.reservations = reservations;
        clone.roomCategories = roomCategories;
        List<RoomAssignment> clonedRoomAssignments = new ArrayList<RoomAssignment>(roomAssignments.size());
        for (RoomAssignment roomAssignment: roomAssignments) {
            RoomAssignment clonedRoomAssignment = roomAssignment.clone();
            clonedRoomAssignments.add(clonedRoomAssignment);
        }
        clone.roomAssignments = clonedRoomAssignments;
        clone.score = score;
        return clone;
	}
	
	/* (non-Javadoc)
	 * @see org.drools.planner.core.solution.Solution#getProblemFacts()
	 */
	@Override
	public Collection<? extends Object> getProblemFacts() {
		List<Object> facts = new ArrayList<Object>();
        facts.addAll(rooms);
        facts.addAll(reservations);
        facts.addAll(roomCategories);
        // Do not add the planning entity's (roomAssignment) because that will be done automatically	
        return facts;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (id == null || !(o instanceof Schedule)) {
			return false;
		} else {
			Schedule other = (Schedule) o;
			if (roomAssignments.size() != other.roomAssignments.size()) {
				return false;
			}
//			for (Iterator<RoomAssignment> itr = roomAssignments.iterator(), otherItr = other.roomAssignments.iterator(); itr.hasNext();) {
//				RoomAssignment roomAssignment = itr.next();
//				RoomAssignment otherRoomAssignment = otherItr.next();
//				// Notice: we don't use equals()
//				if (!roomAssignment.solutionEquals(otherRoomAssignment)) {
//					return false;
//				}
//			}
			return true;
		}
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		for (RoomAssignment roomAssignment : roomAssignments) {
			// Notice: we don't use hashCode()
			hashCodeBuilder.append(roomAssignment.solutionHashCode());
		}
		return hashCodeBuilder.toHashCode();
	}

}
