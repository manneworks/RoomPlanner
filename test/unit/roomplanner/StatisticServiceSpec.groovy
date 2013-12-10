package roomplanner

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(StatisticService)
@Mock([PlannerRequest])
class StatisticServiceSpec extends Specification {

	def statisticService

	def setup() {
		statisticService = new StatisticService()
	}

	def 'check for zero or negative argument' () {
		given:
			assert statisticService != null
		when:
			statisticService.getDurationDistribution(0)
		then:
			thrown IllegalArgumentException
		when:
			statisticService.getDurationDistribution(-1)
		then:
			thrown IllegalArgumentException
	}

	def 'check graph for zero items' () {
		given:
			assert statisticService != null
		when: 
			def graphData = statisticService.getDurationDistribution(10)
		then:
			graphData.minDuration == 0
			graphData.maxDuration == 0
			graphData.avgDuration == 0
			graphData.labels.size() == 1
			graphData.values.size() == 1
	}

	def 'check graph for single item' () {
		given:
			assert statisticService != null
			def request = new PlannerRequest(
				licenseKey: "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX",
				timestamp: System.currentTimeMillis(),
				requestDuration: 100,
				).save(flush:true)
		
		when: 
			def graphData = statisticService.getDurationDistribution(10)
		
		then:
			graphData.minDuration == 100
			graphData.maxDuration == 100
			graphData.avgDuration == 100
			graphData.labels.size() == 1
			graphData.values.size() == 1
		
		cleanup:
			request.delete(flush:true)
	}

	def 'check graph for multiple items' () {
		given:
			assert statisticService != null
			def rnd = new Random()
			(100..rnd.nextInt(1000)+100).each {
				new PlannerRequest(
					licenseKey: "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX",
					timestamp: System.currentTimeMillis(),
					requestDuration: rnd.nextInt(1000),
					).save(flush:true)
			}
		
		when: 
			def graphData = statisticService.getDurationDistribution(10)
		
		then:
			graphData.minDuration >= 0
			graphData.maxDuration < 1000
			graphData.avgDuration >= 0
			graphData.avgDuration < 1000
			graphData.labels.size() == 11
			graphData.values.size() == 11
	}

	def 'check for single column' () {
		given:
			assert statisticService != null
			def rnd = new Random()
			(100..rnd.nextInt(1000)+100).each {
				new PlannerRequest(
					licenseKey: "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX",
					timestamp: System.currentTimeMillis(),
					requestDuration: rnd.nextInt(1000),
					).save(flush:true)
			}
		
		when: 
			def graphData = statisticService.getDurationDistribution(1)
		
		then:
			graphData.minDuration >= 0
			graphData.maxDuration < 1000
			graphData.avgDuration >= 0
			graphData.avgDuration < 1000
			graphData.labels.size() == 2
			graphData.values.size() == 2
	}

	def 'check request count chart' () {
		given:
			assert statisticService != null
			def millisInAYear = 1000L*60L*60L*24L*365L
			def rnd = new Random()
			(100..rnd.nextInt(1000)+100).each {
				new PlannerRequest(
					licenseKey: "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX",
					timestamp: System.currentTimeMillis() - rnd.nextLong() % millisInAYear,
					requestDuration: rnd.nextInt(1000),
					).save(flush:true)
			}
		
		when: 
			def graphData = statisticService.getRequestCountDistribution()
		
		then:
			graphData.labels.size() == 13
			graphData.values.size() == 13
	}
}
