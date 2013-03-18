package roomplanner

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
class Score implements Serializable {
	
	@XmlElement Boolean feasible
	@XmlElement Double hardScoreConstraints
	@XmlElement Double softScoreConstraints
//	@XmlElement List<ScoreDetail> scoreDetails
}
