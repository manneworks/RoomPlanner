package roomplanner

import grails.transaction.Transactional

import org.joda.time.DateTime

//@Transactional
class StatisticService {

    def getDurationDistribution(int colCount) {

    	if (!(colCount > 0)) {
    		log.error("Column count must be a positive number. Value given: [$colCount]")
    		throw new IllegalArgumentException("Column count must be a positive number. Value given: [$colCount]")
    	}

    	def maxDuration = 0
    	def minDuration = 0
    	def avgDuration = 0
    	def base = 1

    	if (PlannerRequest.count() != 0) {

	    	maxDuration = PlannerRequest.createCriteria().get {
	    		projections {
	        		max "requestDuration"
	    		}
			} as Long

	    	minDuration = PlannerRequest.createCriteria().get {
	    		projections {
	        		min "requestDuration"
	    		}
			} as Long

	    	avgDuration = PlannerRequest.createCriteria().get {
	    		projections {
	        		avg "requestDuration"
	    		}
			} as Long

			log.trace("Max: $maxDuration ms; Min: $minDuration ms")

			base = Math.exp((Math.log(maxDuration) - Math.log(minDuration))/(2*colCount))
		}

		if (base == 1) {  // means no or single item to graph
			colCount = 0
		}

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

    def getRequestCountDistribution() {

    	def dateNow = new DateTime()

    	// show monthly chart for last year
    	def range = (12..0)

    	def labels = range.collect { "'" + dateNow.minusMonths(it).toString("dd.MM.yyyy") + "'" } 
    	def values = range.collect {
    		PlannerRequest.countByTimestampBetween(
    			dateNow.minusMonths(it+1).getMillis(),
    			dateNow.minusMonths(it).getMillis()
    		)
    	}

    	def maxCount = values.max()
    	def minCount = values.min()
    	def avgCount = (long) (values.sum()/range.size())

		log.trace("Values: $values")
		log.trace("Labels: $labels")
		log.trace("Max: $maxCount; Min: $minCount; Avg: $avgCount")

    	[
    		maxCount: maxCount,
    		minCount: minCount,
    		avgCount: avgCount,
    		labels: labels,
    		values: values
    	]
    }
}
