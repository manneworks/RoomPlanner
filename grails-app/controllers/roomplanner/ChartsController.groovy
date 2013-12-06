package roomplanner

class ChartsController {

	def statisticService

    def index() { 
    	def chart1Data = statisticService.getDurationDistribution(10)
    	[
    		chart1MinValue: chart1Data.minDuration,
    		chart1MaxValue: chart1Data.maxDuration,
    		chart1AvgValue: chart1Data.avgDuration,
    		chart1Labels: chart1Data.labels,
    		chart1Values: chart1Data.values
    	]
    }
}
