package factory

import org.optaplanner.core.impl.heuristic.move.Move
import org.optaplanner.core.impl.heuristic.selector.move.factory.MoveListFactory;
import org.optaplanner.core.api.domain.solution.Solution

import roomplanner.Room
import roomplanner.RoomChangeMove
import roomplanner.Schedule

public class RoomChangeMoveFactory implements MoveListFactory<Schedule> {

	public List<Move> createMoveList(Solution solution) {
        Schedule schedule = (Schedule) solution
        List<Move> moveList = new ArrayList<Move>()

		schedule.roomAssignments.each { roomAssignment ->
			schedule.rooms.each { toRoom ->
				moveList << new RoomChangeMove(roomAssignment, toRoom)
			}
		}
        
        moveList
	}
}
	