package roomplanner

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock(Partner)
@TestFor(Partner)
class PartnerSpec extends Specification {

	def 'Check object creation'() {
		when:
			Partner p = new Partner(
				username: "test",
				password: "test",
				enabled: true
				).save()
		then:
			Partner.list().size() == 1
		cleanup:
			p.delete(flush:true)
	}

	def 'Check object deleteion'() {
		given:
			Partner p = new Partner(
				username: "test",
				password: "test",
				enabled: true
				).save()
			assert Partner.list().size() == 1
		when:
			p.delete(flush:true)
		then:
			Partner.list().size() == 0
	}
}
