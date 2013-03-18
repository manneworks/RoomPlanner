package roomplanner

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import joda.IntervalStringAdapter

import org.joda.time.Interval

@XmlAccessorType(XmlAccessType.NONE)
class Reservation implements Serializable {

	@XmlAttribute	Long id
	@XmlElement		RoomCategory roomCategory
		
	@XmlJavaTypeAdapter(IntervalStringAdapter.class)
	@XmlElement Interval bookingInterval
	
	@XmlElement		Integer adults
	@XmlElement		Boolean nonSmoking = true
		

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