package roomplanner

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("ScoreDetail")
class ScoreDetail {

	String constraintName
	List<RoomAssignment> roomAssignments
	Double weight
	
}