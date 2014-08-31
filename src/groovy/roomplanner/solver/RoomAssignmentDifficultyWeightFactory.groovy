package roomplanner.solver

import org.apache.commons.lang.builder.CompareToBuilder
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory

import roomplanner.Schedule
import roomplanner.RoomAssignment

class RoomAssignmentDifficultyWeightFactory
        implements SelectionSorterWeightFactory<Schedule, RoomAssignment> {

    Comparable createSorterWeight(Schedule schedule, RoomAssignment roomAssignment) {
        int hardDisallowedCount = 0;
        int softDisallowedCount = 0;
        // for (Room room : schedule.getRoomList()) {
        //     hardDisallowedCount += (room.countHardDisallowedAdmissionPart(bedDesignation.getAdmissionPart())
        //             * room.getCapacity());
        //     softDisallowedCount += (room.countSoftDisallowedAdmissionPart(bedDesignation.getAdmissionPart())
        //             * room.getCapacity());
        // }
        return new RoomAssignmentDifficultyWeight(roomAssignment, hardDisallowedCount, softDisallowedCount);
    }

    static class RoomAssignmentDifficultyWeight implements Comparable<RoomAssignmentDifficultyWeight> {

        private final RoomAssignment roomAssignment;
        private int hardDisallowedCount;
        private int softDisallowedCount;

        public RoomAssignmentDifficultyWeight(RoomAssignment roomAssignment,
                int hardDisallowedCount, int softDisallowedCount) {
            this.roomAssignment = roomAssignment;
            this.hardDisallowedCount = hardDisallowedCount;
            this.softDisallowedCount = softDisallowedCount;
        }

        public int compareTo(RoomAssignmentDifficultyWeight other) {
            return new CompareToBuilder()
                    .append(roomAssignment.id, other.roomAssignment.id)
                    .toComparison();
        }

    }

}
