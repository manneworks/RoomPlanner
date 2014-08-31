package roomplanner.solver

import java.io.Serializable
import java.util.Comparator

import org.apache.commons.lang.builder.CompareToBuilder
import roomplanner.Room

class RoomStrengthComparator implements Comparator<Room>, Serializable {

    int compare(Room a, Room b) {
        if (a == null) {
            if (b == null) {
                return 0;
            }
            return -1;
        } else if (b == null) {
            return 1;
        }

        new CompareToBuilder()
            .append(b.getAdults(), a.getAdults()) // Descending (smaller rooms are stronger)
            .append(a.getId(), b.getId())
            .toComparison();
    }

}
