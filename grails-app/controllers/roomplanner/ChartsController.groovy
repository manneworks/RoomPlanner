package roomplanner

class ChartsController {

	def statisticService

    def index() { 
    	def chartData = statisticService.getDurationDistribution()
    }
}
