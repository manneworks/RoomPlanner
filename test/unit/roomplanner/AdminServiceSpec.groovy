package roomplanner

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AdminService)
@Mock([Partner, SystemUser])
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
			p.password.size() == 16

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

	def 'Check create duplicate partner' () {
		given:
			assert adminService != null
			def p1 = adminService.createPartner("abc","def")
			assert p1 != null
		
		when:
			def p2 = adminService.createPartner("abc","def")
		
		then:
			Exception e = thrown()

		cleanup:
			p1.delete(flush:true)
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

	def 'Check systemUser creation' () {
		when:
			assert adminService != null
			def su = adminService.createSystemUser("supervisor","password")
			assert su != null

		then:
			SystemUser.list().size() == 1

		cleanup:
			su.delete(flush:true)

	}

	def 'Check duplicate systemUser creation' () {
		given:
			assert adminService != null
			def su1 = adminService.createSystemUser("supervisor","password")
			assert su1 != null

		when:
			def su2 = adminService.createSystemUser("supervisor","password")

		then:
			Exception e = thrown()

		cleanup:
			su1.delete(flush:true)

	}

}
