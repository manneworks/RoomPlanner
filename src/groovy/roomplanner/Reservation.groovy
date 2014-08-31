package roomplanner

import org.joda.time.Interval
import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("Reservation")
class Reservation {
	Long id
	RoomCategory roomCategory
	Interval bookingInterval
	Integer adults
	Boolean nonSmoking = true
}