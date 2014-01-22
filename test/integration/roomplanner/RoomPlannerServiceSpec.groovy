package roomplanner

import grails.test.mixin.*
import spock.lang.*

class RoomPlannerServiceSpec extends Specification {

	def roomPlannerService

	def setup() {
		roomPlannerService = new RoomPlannerService()
	}

	def 'Check getStatus method' () {
		when:
			def status = roomPlannerService.getStatus()
		then:
			status != null
	}
}
