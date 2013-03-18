package roomplanner

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.NONE)
class RoomCategory implements Serializable {

	@XmlAttribute	Long id
	@XmlElement		String name
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		"RoomCategory: " + id +
		" Name: " + name
	}

}
