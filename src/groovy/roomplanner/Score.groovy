package roomplanner

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("Score")
class Score {
	Boolean feasible
	Double hardScoreConstraints
	Double softScoreConstraints
}
