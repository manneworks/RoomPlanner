package roomplanner

import org.apache.commons.lang.builder.HashCodeBuilder
import org.drools.ClassObjectFilter
import org.drools.WorkingMemory
import org.drools.planner.api.domain.solution.PlanningEntityCollectionProperty
import org.drools.planner.core.score.buildin.hardandsoft.HardAndSoftScore
import org.drools.planner.core.score.constraint.ConstraintOccurrence
import org.drools.planner.core.score.director.ScoreDirector
import org.drools.planner.core.score.director.drools.DroolsScoreDirector
import org.drools.planner.core.solution.Solution

class Schedule implements Solution<HardAndSoftScore> {

	HardAndSoftScore score
	ScoreDirector scoreDirector
	
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
	
	public ScoreDirector getScoreDirector() {
		return scoreDirector
	}
	
	public void setScoreDirector(ScoreDirector scoreDirector) {
		this.scoreDirector = scoreDirector
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
		if (this.is(o)) {
			return true;
		}
		if (id == null || !(o instanceof Schedule)) {
			return false;
		} else {
			Schedule other = (Schedule) o;
			if (roomAssignments.size() != other.roomAssignments.size()) {
				return false;
			}
			
			roomAssignments.eachWithIndex { obj, i ->
				// Notice: we don't use equals()
				if (!obj.solutionEquals(other.roomAssignments[i]))
					return false
			}
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

	public List<ScoreDetail> getScoreDetailList() {
		if (!(this.scoreDirector instanceof DroolsScoreDirector)) {
			return null;
		}
		Map<String, ScoreDetail> scoreDetailMap = new HashMap<String, ScoreDetail>();
		WorkingMemory workingMemory = ((DroolsScoreDirector) this.scoreDirector).getWorkingMemory();
		if (workingMemory == null) {
			return Collections.emptyList();
		}
		Iterator<ConstraintOccurrence> it = (Iterator<ConstraintOccurrence>) workingMemory.iterateObjects(
				new ClassObjectFilter(ConstraintOccurrence.class));
		while (it.hasNext()) {
			ConstraintOccurrence constraintOccurrence = it.next();
			ScoreDetail scoreDetail = scoreDetailMap.get(constraintOccurrence.getRuleId());
			if (scoreDetail == null) {
				scoreDetail = new ScoreDetail(constraintOccurrence.getRuleId(), constraintOccurrence.getConstraintType(), constraintOccurrence.getCauses());
				scoreDetailMap.put(constraintOccurrence.getRuleId(), scoreDetail);
			}
			scoreDetail.addConstraintOccurrence(constraintOccurrence);
		}
		List<ScoreDetail> scoreDetailList = new ArrayList<ScoreDetail>(scoreDetailMap.values());
		Collections.sort(scoreDetailList);
		return scoreDetailList;
	}
 
}
