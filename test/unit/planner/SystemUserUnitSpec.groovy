package planner

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import roomplanner.AdminService
import roomplanner.SystemUser

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(SystemUser)
@Mock([SystemUser])
class SystemUserUnitSpec extends Specification {

	def adminService

	def setup() {
		adminService = new AdminService()
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
