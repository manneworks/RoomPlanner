package roomplanner

import grails.transaction.Transactional

@Transactional
class StatisticService {

    def getDurationDistribution(def colCount) {

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

	    def avgDuration = PlannerRequest.createCriteria().get {
	    		projections {
	        		avg "requestDuration"
	    		}
			} as Long

		log.trace("Max: $maxDuration ms; Min: $minDuration ms")

		def base = Math.exp((Math.log(maxDuration) - Math.log(minDuration))/(2*colCount))

		def labels = (0..colCount).collect { Math.round( minDuration * Math.pow(base, 2*it) ) }
		def values = (0..colCount).collect { 
			PlannerRequest.countByRequestDurationBetween( 
				Math.round( minDuration * Math.pow(base, 2*it-1) ), 
				Math.round( minDuration * Math.pow(base, 2*it+1) )
			)
		}

		log.trace("Values: $values")
		log.trace("Labels: $labels")

		[
			minDuration: minDuration,
			maxDuration: maxDuration,
			avgDuration:avgDuration,
			labels: labels,
			values: values
		]
    }
}
