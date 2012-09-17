package roomplanner



import grails.test.mixin.*

import java.text.SimpleDateFormat

import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Level
import org.apache.log4j.LogManager
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RoomPlannerService)
class RoomPlannerServiceTests extends GroovyTestCase {
	
	def roomPlannerService
	def log

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// build a logger...
        BasicConfigurator.configure() 
        LogManager.rootLogger.level = Level.INFO
        log = LogManager.getLogger("RoomPlannerService")

        // use groovy metaClass to put the log into your class
        RoomPlannerService.class.metaClass.getLog << {-> log}

        roomPlannerService = new RoomPlannerService()
	}
	
   void testDoPlanCall() {
		
		def rooms = [
			new Room(id: 1, name: '101', roomCategoryId: 1),
			new Room(id: 2, name: '102', roomCategoryId: 1),
			new Room(id: 3, name: '103', roomCategoryId: 2)
		]

		def roomCategories = [
			new RoomCategory(id: 1, name: 'Standard'),
			new RoomCategory(id: 2, name: 'Superior')
		]

		def reservations = [
			new Reservation(
				id: 1,
				dateFrom: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-08'),
				dateTo: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-10'),
				roomCategoryId: 2
			),
			new Reservation(
				id: 2,
				dateFrom: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-08'),
				dateTo: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-10'),
				roomCategoryId: 1
			),
			new Reservation(
				id: 3,
				dateFrom: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-12'),
				dateTo: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-14'),
				roomCategoryId: 1,
				roomId: 2
			),
			new Reservation(
				id: 4,
				dateFrom: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-09'),
				dateTo: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-11'),
				roomCategoryId: 1
			),
			new Reservation(
				id: 5,
				dateFrom: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-09'),
				dateTo: new SimpleDateFormat("yyyy-MM-dd").parse('2012-08-11'),
				roomCategoryId: 1
			)
		]
		
		def roomAssignments = [
		]

        Schedule schedule = roomPlannerService.doPlan(rooms, roomCategories, reservations, roomAssignments)
		
		
		schedule.reservations.each {
			System.out.println("Reservation "+it.id+" placed to Room "+it.roomId)
		}
    }

}
