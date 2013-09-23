package roomplanner

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AdminService)
@Mock(Partner)
class AdminServiceSpec extends Specification {

	def adminService

	def setup() {
		adminService = new AdminService()
	}

	def 'Check create new partner' () {
		when:
			assert adminService != null
			def p = adminService.createPartner()
			assert p != null

		then:
			Partner.list().size() == 1

		cleanup:
			p.delete(flush:true)
	}

	def 'Check create predefined partner' () {
		when:
			assert adminService != null
			def p = adminService.createPartner("abc","def")
			assert p != null

		then:
			Partner.list().size() == 1
			adminService.checkPartner("abc","def") == true
			adminService.checkPartner("abc","xyz") == false
			adminService.checkPartner("xyz","def") == false
			adminService.checkPartner("opq","rst") == false

		cleanup:
			p.delete(flush:true)
	}

	def 'Check partner credentials' () {
		when:
			assert adminService != null
			def p = adminService.createPartner()
			def username = p.username
			def password = p.password
			assert p != null

		then:
			adminService.checkPartner(username,password) == true
			adminService.checkPartner("test","test") == false

		cleanup:
			p.delete(flush:true)
	}

}
