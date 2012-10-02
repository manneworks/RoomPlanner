package initializer

import org.drools.planner.core.phase.custom.CustomSolverPhaseCommand
import org.drools.planner.core.score.Score
import org.drools.planner.core.score.buildin.hardandsoft.DefaultHardAndSoftScore
import org.drools.planner.core.score.director.ScoreDirector

import roomplanner.Reservation
import roomplanner.Room
import roomplanner.RoomAssignment
import roomplanner.Schedule

class SolutionInitializer implements CustomSolverPhaseCommand {

	@Override
	public void changeWorkingSolution(ScoreDirector scoreDirector) {
		Schedule schedule = (Schedule) scoreDirector.getWorkingSolution();
		initializeRoomAssignmentList(scoreDirector, schedule);
	}

	private void initializeRoomAssignmentList(ScoreDirector scoreDirector, Schedule schedule) {
		List<RoomAssignment> roomAssignmentList = createRoomAssignmentList(schedule);
		// Assign one room at a time
		List<Room> unassignedRoomList = schedule.getRooms();

		for (RoomAssignment roomAssignment: roomAssignmentList) {
			Score bestScore = DefaultHardAndSoftScore.valueOf(Integer.MIN_VALUE);
			Room bestRoom = null;

			boolean added = false;
			// Try every room for that reservation
			// TODO by reordening the seats so index 0 has a different table then index 1 and so on,
			// this will probably be faster because perfectMatch will be true sooner
			for (Room room: unassignedRoomList) {
				if (!added) {
					scoreDirector.beforeEntityAdded(roomAssignment);
					roomAssignment.setRoom(room);
					scoreDirector.afterEntityAdded(roomAssignment);
					added = true;
				} else {
					scoreDirector.beforeVariableChanged(roomAssignment, "room");
					roomAssignment.setRoom(room);
					scoreDirector.afterVariableChanged(roomAssignment, "room");
				}
				Score score = scoreDirector.calculateScore();
				if (score.compareTo(bestScore) > 0) {
					bestScore = score;
					bestRoom= room;
				}
			}
			if (bestRoom == null) {
				throw new IllegalStateException("The bestRoom(" + bestRoom+ ") cannot be null.");
			}
			scoreDirector.beforeVariableChanged(roomAssignment, "room");
			roomAssignment.setRoom(bestRoom);
			scoreDirector.afterVariableChanged(roomAssignment, "room");
			// There will always be enough allowed seats: ok to do this for this problem, but not ok for most problems
			//unassignedRoomList.remove(bestRoom);
		}

		//schedule.setRoomAssignments(roomAssignmentList);
		schedule.roomAssignments.addAll(roomAssignmentList)
		
	}

	private List<RoomAssignment> createRoomAssignmentList(Schedule schedule) {
		List<RoomAssignment> roomAssignmentList = new ArrayList<RoomAssignment>(schedule.getReservations().size());
		for (Reservation reservation: schedule.getReservations()) {
			RoomAssignment roomAssignment = new RoomAssignment();
			roomAssignment.setId(reservation.getId());
			roomAssignment.setReservation(reservation);
			roomAssignmentList.add(roomAssignment);
		}
		return roomAssignmentList;
	}

}
