package roomplanner

import grails.test.mixin.*
import spock.lang.*

@Mock([Setting, PlannerRequest])
class RoomPlannerServiceSpec extends Specification {

	def roomPlannerService

	def setup() {
		roomPlannerService = new RoomPlannerService()
		roomPlannerService.adminService = new AdminService()
		roomPlannerService.adminService.grailsApplication = new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()		
	}

	def 'Check getStatus method' () {
		when:
			def status = roomPlannerService.getStatus()
		then:
			status != null
	}
}
