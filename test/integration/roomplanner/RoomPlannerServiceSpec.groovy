package roomplanner

import grails.plugin.spock.IntegrationSpec

class RoomPlannerServiceSpec extends IntegrationSpec {

	def roomPlannerService

	def 'Check getStatus method' () {
		when:
			def status = roomPlannerService.getStatus()
		then:
			status != null
	}
}
