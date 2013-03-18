package roomplanner

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class Room implements Serializable {

	@XmlAttribute	Long id
	@XmlElement		RoomCategory roomCategory
	@XmlElement		String name
	@XmlElement 	Integer adults
	@XmlElement		Boolean nonSmoking = true
	

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

