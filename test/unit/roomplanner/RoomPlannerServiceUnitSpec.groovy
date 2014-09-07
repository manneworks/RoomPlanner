package roomplanner

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification
import grails.test.mixin.TestFor
import grails.test.mixin.Mock

import org.joda.time.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RoomPlannerService)
@Mock(PlannerRequest)
@TestMixin(GrailsUnitTestMixin)
class RoomPlannerServiceUnitSpec extends Specification {

	def roomPlannerService

	def setup() {
		roomPlannerService = new RoomPlannerService(grailsApplication: grailsApplication)
	}

	def 'Empty parameters returns empty plan' () {
		given: 
			def license = new roomplanner.api.License(key: "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX")
    	when: 
    		def plan = roomPlannerService.doPlan(license,[],[],[],[], null)

    	then:
    		plan != null
    		plan.score.feasible
    		plan.score.hardScoreConstraints == 0
    		plan.score.softScoreConstraints == 0
    		plan.roomAssignments.size() == 0
    }

    def 'Check statistic object creation' () {
		given: 
			def license = new roomplanner.api.License(key: "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX")
			def count = PlannerRequest.list().size()
    	when: 
    		def plan = roomPlannerService.doPlan(license,[],[],[],[], null)

    	then:
    		plan != null
    		PlannerRequest.list().size() == (count + 1)
    }

    def 'Run with no room assignment' () {
    	given:
			def license = new roomplanner.api.License(key: "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX")
    		def (roomCategories, rooms, reservations) = buildHotelData()

    	when: 
    		def plan = roomPlannerService.doPlan(license, roomCategories, rooms, reservations, [], null)
		
		then:
			plan != null
			plan.roomAssignments.each() {
				it.room.nonSmoking == it.reservation.nonSmoking
			}
    }

    def 'Run with room assignment' () {
    	given:
			def license = new roomplanner.api.License(key: "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX")
    		def (roomCategories, rooms, reservations) = buildHotelData()
			def seed = new Random()
    		def roomAssignments = (301..301).collect {
    			new roomplanner.api.RoomAssignment(
    				id: it,
    				room: rooms[seed.nextInt(rooms.size())],
    				reservation: reservations[seed.nextInt(reservations.size())],
    				moveable: false
    				)
    		}
    	when: 
    		def plan = roomPlannerService.doPlan(license, roomCategories, rooms, reservations, roomAssignments, null)
		
		then:
			plan != null
			plan.roomAssignments.each() {
				it.room.id == roomAssignments[0].room.id
				it.reservation.id == roomAssignments[0].reservation.id
			}
    }

    private def buildHotelData() {

		def seed = new Random()
		def MAX_ADULTS = 3
		def MAX_DURATION_DAYS = 7

		def roomCategories = (1..5).collect {
			new roomplanner.api.RoomCategory(
				id: it
				)
		}
		def rooms = (101..115).collect {
			new roomplanner.api.Room(
				id: it,
				roomCategory: roomCategories[seed.nextInt(roomCategories.size())],
				name: "Room $it",
				adults: seed.nextInt(MAX_ADULTS)+1,
				nonSmoking: seed.nextBoolean()
				)
		}
		def reservations = (201..205).collect {
			DateTime fromDate = new DateTime().minusDays(seed.nextInt(MAX_DURATION_DAYS))
			DateTime toDate = fromDate.plusDays(seed.nextInt(MAX_DURATION_DAYS)+1)

			new roomplanner.api.Reservation(
				id: it,
				roomCategory: roomCategories[seed.nextInt(roomCategories.size())],
				bookingInterval: new Interval(fromDate, toDate),
				adults: seed.nextInt(MAX_ADULTS)+1,
				nonSmoking: seed.nextBoolean()
				)
		}
		[ roomCategories, rooms, reservations ]
    }
}
