package roomplanner.solver

import org.apache.commons.lang.builder.CompareToBuilder
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory

import roomplanner.Schedule
import roomplanner.RoomAssignment

class RoomAssignmentDifficultyWeightFactory
        implements SelectionSorterWeightFactory<Schedule, RoomAssignment> {

    Comparable createSorterWeight(Schedule schedule, RoomAssignment roomAssignment) {
        def roomSizeCountMap = [:]
        def roomTypeCountMap = [:]
        // for (Room room : schedule.getRoomList()) {
        //     hardDisallowedCount += (room.countHardDisallowedAdmissionPart(bedDesignation.getAdmissionPart())
        //             * room.getCapacity());
        //     softDisallowedCount += (room.countSoftDisallowedAdmissionPart(bedDesignation.getAdmissionPart())
        //             * room.getCapacity());
        // }
        new RoomAssignmentDifficultyWeight(roomAssignment, roomSizeCountMap, roomTypeCountMap)
    }

    static class RoomAssignmentDifficultyWeight implements Comparable<RoomAssignmentDifficultyWeight> {

        RoomAssignment roomAssignment
        def roomSizeCountMap
        def roomTypeCountMap

        public RoomAssignmentDifficultyWeight(RoomAssignment roomAssignment,
                def roomSizeCountMap, def roomTypeCountMap) {
            this.roomAssignment = roomAssignment
            this.roomSizeCountMap = roomSizeCountMap
            this.roomTypeCountMap = roomTypeCountMap
        }

        public int compareTo(RoomAssignmentDifficultyWeight other) {
            return new CompareToBuilder()
                    .append(roomAssignment.reservation.bookingInterval.toDurationMillis(), other.roomAssignment.reservation.bookingInterval.toDurationMillis())
                    .append(roomAssignment.id, other.roomAssignment.id)
                    .toComparison();
        }

    }

}
