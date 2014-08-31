package roomplanner

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("Room")
class Room {
	Long id
	RoomCategory roomCategory
	String name
	Integer adults
	Boolean nonSmoking = true
}

