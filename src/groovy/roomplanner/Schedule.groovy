package roomplanner

import org.apache.commons.lang.builder.HashCodeBuilder

import org.optaplanner.core.api.domain.solution.PlanningSolution
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider
import org.optaplanner.core.api.domain.solution.Solution
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.impl.score.buildin.hardsoft.HardSoftScoreDefinition
import org.optaplanner.core.impl.score.director.ScoreDirector

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamOmitField
import com.thoughtworks.xstream.annotations.XStreamConverter
import org.optaplanner.persistence.xstream.impl.score.XStreamScoreConverter

@PlanningSolution
@XStreamAlias("Schedule")
class Schedule implements Solution<HardSoftScore> {

	Long id

	@XStreamConverter(value = XStreamScoreConverter.class, types = [HardSoftScoreDefinition.class])
	HardSoftScore score

	@XStreamOmitField
	ScoreDirector scoreDirector
	
	private List<Room> rooms = new ArrayList<Room>()
	private List<Reservation> reservations = new ArrayList<Reservation>()
	private List<RoomCategory> roomCategories = new ArrayList<RoomCategory>()
	private List<RoomAssignment> roomAssignments = new ArrayList<RoomAssignment>()
	
	
	@PlanningEntityCollectionProperty
	public List<RoomAssignment> getRoomAssignments() {
		return roomAssignments;
	}
	
	public void setRoomAssignments(List<RoomAssignment> roomAssignments) {
		this.roomAssignments = roomAssignments
	}
	
	@ValueRangeProvider(id="roomRange")
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

	public HardSoftScore getScore() {
		return score;
	}

	public void setScore(HardSoftScore score) {
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
 // @Override
 // public Schedule cloneSolution() {
 //        Schedule clone = new Schedule();
 //        clone.id = id;
 //        clone.rooms = rooms;
 //        clone.reservations = reservations;
 //        clone.roomCategories = roomCategories;
 //        List<RoomAssignment> clonedRoomAssignments = new ArrayList<RoomAssignment>(roomAssignments.size());
 //        for (RoomAssignment roomAssignment: roomAssignments) {
 //            RoomAssignment clonedRoomAssignment = roomAssignment.clone();
 //            clonedRoomAssignments.add(clonedRoomAssignment);
 //        }
 //        clone.roomAssignments = clonedRoomAssignments;
 //        clone.score = score;
 //        return clone;
 // }
	
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

	def getScoreDetailList() {
		def result = []
		// Get score constraint occurences
		scoreDirector.getConstraintMatchTotals().each() { constraintMatchTotal ->
		    def constraintName = constraintMatchTotal.getConstraintName();
			def weightTotal = constraintMatchTotal.getWeightTotalAsNumber();
			log.trace("Constraint: \"$constraintName\" Total weight: $weightTotal")
		    constraintMatchTotal.getConstraintMatchSet().each() { constraintMatch ->
		        def justificationList = constraintMatch.getJustificationList();
		        def weight = constraintMatch.getWeightAsNumber();
		        log.trace("  Weight: $weight [$justificationList]")

		        result << new ScoreDetail(
		        	constraintName: constraintName,
		        	roomAssignments: justificationList,
		        	weight: weight,
		        	)
	    	}
		}
		result
	}

}
