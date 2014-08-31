package benchmark

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class PlannerDataSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "Generate data"() {
		given:
			SolutionGenerator.generate()
		when:
			def a = 1
		then:
			true
	}
}
