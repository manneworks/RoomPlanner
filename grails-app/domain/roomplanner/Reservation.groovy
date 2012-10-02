package roomplanner

import org.joda.time.ReadableInterval

class Reservation {

	Long id
	RoomCategory roomCategory
	ReadableInterval bookingInterval
	Integer adults
	Boolean nonSmoking = true
		
    static constraints = {
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		"Reservation: " + id +
		" Period: " + bookingInterval.toString() +
		" Adults: " + adults +
		" " + roomCategory.toString()
	}
	
	
}