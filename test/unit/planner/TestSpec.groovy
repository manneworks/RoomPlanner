package planner

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class TestSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "testTest"() {
		given:
			int a = 1
		when: 
			a = a + 1
		then:
			a == 2
	}
}
