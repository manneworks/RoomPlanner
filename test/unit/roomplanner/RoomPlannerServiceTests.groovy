package roomplanner

import grails.test.mixin.*

import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Level
import org.apache.log4j.LogManager
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Interval
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
		log = LogManager.getLogger("org.drools.planner")

        // use groovy metaClass to put the log into your class
        RoomPlannerService.class.metaClass.getLog << {-> log}

        roomPlannerService = new RoomPlannerService()
	}
	
   void testDoPlanCall() {

	   def rc1 = new RoomCategory(id: 1, name: 'Standard')
	   def rc2 = new RoomCategory(id: 2, name: 'Superior')

	   def roomCategories = [rc1, rc2]
	   
	   def rooms = [
			new Room(id: 1, name: '101', roomCategory: rc1, adults: 2),
			new Room(id: 2, name: '102', roomCategory: rc1, adults: 2),
			new Room(id: 3, name: '103', roomCategory: rc2, adults: 1)
		]

		def i1 = new Interval(new DateTime(2012,10,1,14,0), new DateTime(2012,10,3,12,0))
		def i2 = new Interval(new DateTime(2012,10,2,14,0), new DateTime(2012,10,6,12,0))
		def i3 = new Interval(new DateTime(2012,10,4,14,0), new DateTime(2012,10,5,12,0))
		
		def reservations = [
			new Reservation(
				id: 1,
				bookingInterval: i2,
				roomCategory: rc2,
				adults: 1
			),
//			new Reservation(
//				id: 2,
//				bookingInterval: i1,
//				roomCategory: rc1,
//				adults: 4
//			),
			new Reservation(
				id: 3,
				bookingInterval: i1,
				roomCategory: rc1,
				adults: 2
			),
			new Reservation(
				id: 4,
				bookingInterval: i3,
				roomCategory: rc1,
				adults: 2
			),
			new Reservation(
				id: 5,
				bookingInterval: i2,
				roomCategory: rc1,
				adults: 2
			)
			
		]
		
		def roomAssignments = [
		]
   
		solve(rooms, roomCategories, reservations, roomAssignments)
   }
   
   void testGeneratedData() {

	   def rooms = []
	   def roomCategories = []
	   def reservations = []
	   def roomAssignments = []
	   
	   int NUMBER_OF_ROOMS = 15
	   int NUMBER_OF_RESERVATIONS = 25
	   
	   def years = [2012]
	   def months = [10]
	   def days = 15..30
	   int ADULTS_MAX = 3
	   def roomCategoriesIndex = 1..5
	   int MAX_DURATION_DAYS = 7
	   
	   def seed = new Random()
	   
	   // generate room categories
	   roomCategoriesIndex.each {
		   roomCategories << new RoomCategory(
			   id: it
		   )
	   }
	   
	   // generate rooms
	   for (i in 1..NUMBER_OF_ROOMS) {
		   rooms << new Room(
			   id: i,
			   roomCategory: roomCategories[seed.nextInt(roomCategories.size())],
			   adults: seed.nextInt(ADULTS_MAX-1)+1
		   ) 
	   }
	   
	   // generate reservations
	   for (i in 1..NUMBER_OF_RESERVATIONS) {
		   reservations << new Reservation(
			   id: i,
			   bookingInterval: new Interval(
				   new DateTime(
					   years[seed.nextInt(years.size())],
					   months[seed.nextInt(months.size())],
					   days[seed.nextInt(days.size())],
					   12,
					   0
					   ),
				   new Duration((seed.nextInt(MAX_DURATION_DAYS-1)+1)*60000*60*24)
			   ),
			   roomCategory: roomCategories[seed.nextInt(roomCategories.size())],
			   adults: seed.nextInt(ADULTS_MAX-1)+1
		   )
	   }
	   	   	   
	   solve(rooms, roomCategories, reservations, roomAssignments)
   }
	
   /**
    * 
    * @param rooms
    * @param roomCategories
    * @param reservations
    * @param roomAssignments
    * @return
    */
   private solve(def rooms, def roomCategories, def reservations, def roomAssignments) {
	   
	   System.out.println "Room Categories"
	   roomCategories.each { System.out.println(it) }
	   System.out.println "Rooms"
	   rooms.each { System.out.println(it) }
	   System.out.println "Reservations"
	   reservations.each { System.out.println(it) }
	   
	   
        Schedule schedule = roomPlannerService.doPlan(rooms, roomCategories, reservations, roomAssignments)
		
		System.out.println("Score: " + schedule.score)
		System.out.println("Feasible: " + (schedule.score.feasible))
		
		def listOfBrokenConstraints = schedule.getScoreDetailList()
		if (!listOfBrokenConstraints.isEmpty()) {
			System.out.println "List of broken constraints"
			listOfBrokenConstraints.each { group ->
				System.out.println(group)
//				ScoreDetail item = (ScoreDetail)group
//				item.causes.each { cause ->
//					System.out.println(cause)
//				}
			}
		}
		
		schedule.roomAssignments.each {
			System.out.println(it.reservation.toString() + " => " + it.room.toString())
		}
    }
}
