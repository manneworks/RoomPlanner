package factory

import org.drools.planner.core.move.Move
import org.drools.planner.core.heuristic.selector.move.factory.MoveListFactory;
import org.drools.planner.core.solution.Solution

import roomplanner.Room
import roomplanner.RoomAssignment
import roomplanner.RoomChangeMove
import roomplanner.Schedule

public class RoomChangeMoveFactory implements MoveListFactory {

	@Override
	public List<Move> createMoveList(Solution solution) {
        Schedule schedule = (Schedule) solution;
        List<Move> moveList = new ArrayList<Move>();

		schedule.roomAssignments.each { roomAssignment ->
			schedule.rooms.each { toRoom ->
				moveList << new RoomChangeMove(roomAssignment, toRoom)
			}
		}
        return moveList;
	}
}
	