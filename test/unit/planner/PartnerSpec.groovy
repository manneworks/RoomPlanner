package planner

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import grails.test.mixin.TestFor
import grails.test.mixin.Mock

import roomplanner.Partner

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock(Partner)
@TestFor(Partner)
@TestMixin(GrailsUnitTestMixin)
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
