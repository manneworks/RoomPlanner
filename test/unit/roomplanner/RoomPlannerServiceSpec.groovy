package roomplanner

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RoomPlannerService)
@Mock(PlannerRequest)
class RoomPlannerServiceSpec extends Specification {

	def roomPlannerService

	def setup() {
		roomPlannerService = new RoomPlannerService(grailsApplication: grailsApplication)
	}

	def 'Empty parameters returns empty plan' () {
    	when: 
    		def plan = roomPlannerService.doPlan([],[],[],[])

    	then:
    		plan != null
    		plan.score.feasible
    		plan.score.hardScoreConstraints == 0
    		plan.score.softScoreConstraints == 0
    		plan.roomAssignments.size() == 0
    }
}
