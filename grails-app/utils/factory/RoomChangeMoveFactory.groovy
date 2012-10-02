package factory

import org.drools.planner.core.move.Move
import org.drools.planner.core.move.factory.CachedMoveFactory
import org.drools.planner.core.solution.Solution

import roomplanner.Room
import roomplanner.RoomAssignment
import roomplanner.RoomChangeMove
import roomplanner.Schedule

public class RoomChangeMoveFactory extends CachedMoveFactory {

	@Override
	public List<Move> createCachedMoveList(Solution solution) {
        Schedule schedule = (Schedule) solution;
        List<Move> moveList = new ArrayList<Move>();
        //List<Room> roomList = schedule.rooms;
		
		schedule.roomAssignments.each { roomAssignment ->
			schedule.rooms.each { toRoom ->
				moveList << new RoomChangeMove(roomAssignment, toRoom)
			}
		}
		
//        for (RoomAssignment roomAssignment : schedule.roomAssignments) {
//            for (Room toRoom : roomList) {
//                moveList.add(new RoomChangeMove(roomAssignment, toRoom));
//            }
//        }
        return moveList;

	}
	
}
	