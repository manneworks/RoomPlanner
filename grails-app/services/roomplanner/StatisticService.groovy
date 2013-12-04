package roomplanner

import grails.transaction.Transactional

@Transactional
class StatisticService {

    def getDurationDistribution() {

	    def maxDuration = PlannerRequest.createCriteria().get {
	    		projections {
	        		max "requestDuration"
	    		}
			} as Long

	    def minDuration = PlannerRequest.createCriteria().get {
	    		projections {
	        		min "requestDuration"
	    		}
			} as Long

		def colCount = 10

		log.debug("Max: $maxDuration ms; Min: $minDuration ms")

		

    }
}
