package roomplanner

import grails.transaction.Transactional

@Transactional
class StatisticService {

    def getDurationDistribution() {

	    def maxDuration = PlannerRequest.createCriteria().get {
	    		projections {
	        		max "duration"
	    		}
			} as Long

	    def minDuration = PlannerRequest.createCriteria().get {
	    		projections {
	        		min "duration"
	    		}
			} as Long

		def colCount = 10

			

    }
}
