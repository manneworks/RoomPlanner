package roomplanner

import org.drools.planner.api.domain.solution.PlanningEntityCollectionProperty;
import org.drools.planner.core.score.buildin.hardandsoft.HardAndSoftScore
import org.drools.planner.core.solution.Solution

class Schedule implements Solution<HardAndSoftScore> {

	HardAndSoftScore score
	
	static hasMany = [
		rooms : Room, 
		reservations : Reservation,
		roomCategories : RoomCategory,  
		roomAssignments : RoomAssignment
	]
	
    static constraints = {
    }
	
	@PlanningEntityCollectionProperty
	public List<RoomAssignment> getRoomAssignments() {
		return roomAssignments;
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
	}
}
