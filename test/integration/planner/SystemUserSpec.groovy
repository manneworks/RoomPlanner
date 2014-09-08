package planner

import grails.test.mixin.*
import spock.lang.*

import roomplanner.SystemUser
import roomplanner.AdminService

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(SystemUser)
class SystemUserSpec extends Specification {

	def adminService

	def setup() {
		adminService = new AdminService()
		adminService.grailsApplication = new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()
	}

	void "Test object search"() {
		given:
			adminService.createSystemUser(
					"planner",
					"test"
				)
		when:
			def user = SystemUser.findByUsernameAndPassword("planner","test")

		then:
			user != null

	}
}