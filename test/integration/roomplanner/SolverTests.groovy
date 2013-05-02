package roomplanner

import static org.junit.Assert.*

import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Interval
import org.junit.*

class SolverTests {
	
	def roomPlannerService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSomething() {
	   def rc1 = new RoomCategory(id: 1, name: 'Standard')
	   def rc2 = new RoomCategory(id: 2, name: 'Superior')
	   def rc3 = new RoomCategory(id: 3, name: 'Apartments')
	   
	   def roomCategories = [rc1, rc2, rc3]
	   
	   def rooms = [
			new Room(id: 1, name: '101', roomCategory: rc3, adults: 2)
		]

		def i1 = new Interval(new DateTime(2012,10,1,14,0), new DateTime(2012,10,3,12,0))
		
		def reservations = [
			new Reservation(
				id: 1,
				bookingInterval: i1,
				roomCategory: rc3,
				adults: 3
			)
		]
		
		def roomAssignments = [
		]
   
   		System.out.println("Start solving...")
		solve(rooms, roomCategories, reservations, roomAssignments)
    }
	
	@Test
	void testGeneratedOnce() {
		testGeneratedData()
	}

	@Test
	void testGeneratedLoop() {
		int NUMBER_OF_ITERATIONS = 10
		for (i in 1..NUMBER_OF_ITERATIONS) {
			testGeneratedData()
		}
	}

    private void testGeneratedData() {

	   def rooms = []
	   def roomCategories = []
	   def reservations = []
	   def roomAssignments = []
	   
	   int NUMBER_OF_ROOMS = 25
	   int NUMBER_OF_RESERVATIONS = 50
	   
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


	private void solve(def rooms, def roomCategories, def reservations, def roomAssignments) {
		
		System.out.println "Room Categories"
		roomCategories.each { System.out.println(it) }
		System.out.println "Rooms"
		rooms.each { System.out.println(it) }
		System.out.println "Reservations"
		reservations.each { System.out.println(it) }
		
		
		 Plan plan = roomPlannerService.doPlan(rooms, roomCategories, reservations, roomAssignments)
		 
		 System.out.println("Score: " + plan.score.hardScoreConstraints + '/' + plan.score.softScoreConstraints)
		 System.out.println("Feasible: " + (plan.score.feasible))
		 
		 def listOfBrokenConstraints = [] //plan.score.scoreDetails
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
		 
		 plan.roomAssignments.each {
			 System.out.println(it.reservation.toString() + " => " + it.room.toString())
		 }
	 }
 
}
