package roomplanner

import grails.test.mixin.*
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(RoomPlannerRemoteService)
class RoomPlannerRemoteServiceSpec extends Specification {

	def roomPlannerRemoteServiceClient 

	def setup() {
		roomPlannerRemoteServiceClient = new RoomPlannerRemoteServiceClient()
	}

	def "Call roomplanner via remoting" () {
		when: 
    		def plan = roomPlannerRemoteServiceClient.doPlan([],[],[],[])

    	then:
    		plan != null
    		plan.score.feasible
    		plan.score.hardScoreConstraints == 0
    		plan.score.softScoreConstraints == 0
    		plan.roomAssignments.size() == 0
	}
}