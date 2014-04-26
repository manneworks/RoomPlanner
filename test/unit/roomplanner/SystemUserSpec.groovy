package roomplanner

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(SystemUser)
@Mock([SystemUser])
class SystemUserSpec extends Specification {

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
