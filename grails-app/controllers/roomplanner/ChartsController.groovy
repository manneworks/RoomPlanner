package roomplanner

import org.joda.time.DateTime

class ChartsController {

	def statisticService

    def index() { 
    	def chart1Data = statisticService.getDurationDistribution(10)
        def chart2Data = statisticService.getRequestCountDistribution()
    	[
    		chart1MinValue: chart1Data.minDuration,
    		chart1MaxValue: chart1Data.maxDuration,
    		chart1AvgValue: chart1Data.avgDuration,
    		chart1Labels: chart1Data.labels,
    		chart1Values: chart1Data.values,
            chart2Start: new DateTime().minusDays(30),
            chart2End: new DateTime(),
            chart2MinValue: chart2Data.minCount,
            chart2MaxValue: chart2Data.maxCount,
            chart2AvgValue: chart2Data.avgCount,
            chart2Labels: chart2Data.labels,
            chart2Values: chart2Data.values
    	]
    }
}
