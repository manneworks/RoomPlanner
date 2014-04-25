package roomplanner

import grails.test.mixin.*
import spock.lang.*

@Mock([Setting, PlannerRequest])
class AdminServiceSpec extends Specification {

	def adminService

	def setup() {
		adminService = new AdminService()
		adminService.grailsApplication = new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()		
	}

	def 'Check getStatus method' () {
		when:
			def status = adminService.getStatus()
		then:
			status != null
	}
}
