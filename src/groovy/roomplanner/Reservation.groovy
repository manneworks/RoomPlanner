package roomplanner

import org.joda.time.Interval

class Reservation {
	Long id
	RoomCategory roomCategory
	Interval bookingInterval
	Integer adults
	Boolean nonSmoking = true
}