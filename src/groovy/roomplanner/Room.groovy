package roomplanner

class Room {

	Long id
	RoomCategory roomCategory
	String name
	Integer adults
	Boolean nonSmoking = true
	
    static constraints = {
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		"Room: " + id +
		" Name: " + name +
		" Adults: " + adults + 
		" " + roomCategory.toString()
	}
}

