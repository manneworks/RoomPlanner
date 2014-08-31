package benchmark

import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Interval

import roomplanner.Schedule
import roomplanner.RoomCategory
import roomplanner.Room
import roomplanner.Reservation
import roomplanner.RoomAssignment

import com.thoughtworks.xstream.XStream

class SolutionGenerator {

	static def generate() {
		XStream xstream = new XStream()
		xstream.setMode(XStream.ID_REFERENCES);
		Schedule solution = generateRandomData(15, 12)
		String xml = xstream.toXML(solution)

		def directory = 'src/groovy/benchmark/data'
		def fileName = 'sample'
		def extension = '.xml'

		new File("$directory/$fileName$extension").withWriter { out ->
   			out.println xml
		}
	}

	static def generateRandomData(def roomCount, def reservationCount) {
		int NUMBER_OF_CATEGORIES = 5
		int NUMBER_OF_ROOMS = roomCount
		int NUMBER_OF_RESERVATIONS = reservationCount
		
		int ADULTS_MAX = 3
		int MAX_DURATION_DAYS = 7
		
		def seed = new Random()

		def roomCategories = []
		def rooms = []
		def reservations = []
		def roomAssignments = []

		// generate room categories
		for (i in 1..NUMBER_OF_CATEGORIES) {
			def rc = new RoomCategory(
				id: i
				)
			roomCategories << rc
		}
		
		// generate rooms
		for (i in 1..NUMBER_OF_ROOMS) {
			Room r = new Room(
				id: i,
				name: randomString(8),
				roomCategory: roomCategories[seed.nextInt(roomCategories.size())],
				adults: seed.nextInt(ADULTS_MAX)+1
			)
			rooms << r
		}
		
		// generate reservations
		DateTime nowDate = DateTime.now()
		for (i in 1..NUMBER_OF_RESERVATIONS) {
			DateTime fromDate = nowDate.minusDays(MAX_DURATION_DAYS).plusDays(seed.nextInt(MAX_DURATION_DAYS)+3).withTime(12,0,0,0)
			DateTime toDate = fromDate.plusDays(seed.nextInt(MAX_DURATION_DAYS)+1).withTime(12,0,0,0)

			Reservation r = new Reservation(
				id: i,
				bookingInterval: new Interval(fromDate.toDate().getTime(), toDate.toDate().getTime()),
				roomCategory: roomCategories[seed.nextInt(roomCategories.size())],
				adults: seed.nextInt(ADULTS_MAX)+1
			)
			reservations << r
		}

		// generate roomAssignments


		// save solution
		Schedule solution = new Schedule(
			id: 1,
			roomCategories: roomCategories,
			rooms: rooms,
			reservations: reservations,
			roomAssignments: roomAssignments
			)
		
		solution
	}

	private static String randomString(long max_length) {
		def seed = new Random()
		def alphabet = 'A'..'z'
		def result = ""
		(1..max_length).each {
			result = result + alphabet[seed.nextInt(alphabet.size())]
		}	
		result
	}
}
