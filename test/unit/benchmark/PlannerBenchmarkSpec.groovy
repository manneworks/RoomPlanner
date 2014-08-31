package benchmark

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

import org.optaplanner.benchmark.api.PlannerBenchmarkFactory
import org.optaplanner.benchmark.api.PlannerBenchmark

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class PlannerBenchmarkSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void 'Run benchmarking'() {
		given:
			PlannerBenchmarkFactory plannerBenchmarkFactory = 
				PlannerBenchmarkFactory.createFromXmlResource(
	                "benchmark/roomPlannerBenchmarkConfig.xml");

	        PlannerBenchmark plannerBenchmark = plannerBenchmarkFactory.buildPlannerBenchmark();
    
        when:
	        plannerBenchmark.benchmark();
    
        then:
    	    1 == 1
	}
}
