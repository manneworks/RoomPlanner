package roomplanner

class RoomCategory /*implements Comparable*/ {

	Long id
	String name
	
    static constraints = {
    }
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		"RoomCategory: " + id +
		" Name: " + name
	}

//	@Override
//	public int compareTo(Object o) {
//		RoomCategory other = (RoomCategory)o
//		return (this.id - other.id);
//	}
//
}
