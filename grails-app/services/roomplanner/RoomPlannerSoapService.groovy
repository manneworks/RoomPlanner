package roomplanner

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.apache.cxf.interceptor.InInterceptors
import org.apache.cxf.interceptor.OutInterceptors

import roomplanner.utils.CustomLoggingInInterceptor
import roomplanner.utils.CustomLoggingOutInterceptor

import roomplanner.api.RoomCategory as RoomCategoryDto
import roomplanner.api.Room as RoomDto
import roomplanner.api.Reservation as ReservationDto
import roomplanner.api.RoomAssignment as RoomAssignmentDto
import roomplanner.api.Plan as PlanDto
import roomplanner.api.Score as ScoreDto

// @GrailsCxfEndpoint(
// 	importInterceptors  = ["customLoggingInInterceptor"],
// 	outInterceptors = ["customLoggingOutInterceptor"]
// )
//@InInterceptors( classes = [CustomLoggingInInterceptor])
//@OutInterceptors( classes = [CustomLoggingOutInterceptor])
class RoomPlannerSoapService {

	static expose = EndpointType.JAX_WS

	def grailsApplication
	
	/**
	 * 
	 * @param rooms
	 * @param roomCategories
	 * @param reservations
	 * @param roomAssignments
	 * @return
	 */
	@WebMethod( operationName = 'doPlan' )
	@WebResult( name = 'plan' )
    PlanDto doPlan(
		@WebParam(name="roomList") List<RoomDto> roomsDto, 
		@WebParam(name="roomCategoryList") List<RoomCategoryDto> roomCategoriesDto, 
		@WebParam(name="reservationList") List<ReservationDto> reservationsDto, 
		@WebParam(name="roomAssignmentList") List<RoomAssignmentDto> roomAssignmentsDto
		) {

		def (roomCategories, rooms, reservations, roomAssignments) = 
			SolverHelper.convertFromDto(
				roomCategoriesDto, roomsDto, reservationsDto, roomAssignmentsDto,
			)
				 
		log.trace("Rooms: " + rooms)
		log.trace("RoomCategories: " + roomCategories)
		log.trace("Reservations: " + reservations)
		log.trace("RoomAssignments: " + roomAssignments)

		def planDto

		try {
			Schedule solvedSchedule = SolverHelper.solveProblem(grailsApplication, roomCategories, rooms, reservations, roomAssignments)
			planDto = SolverHelper.buildDtoResponse(solvedSchedule, roomsDto, reservationsDto)

			log.debug("Score: [${planDto.score.hardScoreConstraints}hard/${planDto.score.softScoreConstraints}soft] Feasible: ${planDto.score.feasible}")
		} catch (Throwable e) {
			log.error("Error solving", e)
		}
		finally {
			new PlannerRequest().save()
		}
		planDto
    }
	
	void printClassPath(def classLoader) {
	    def urlPaths = classLoader.getURLs()
	    println "classLoader: $classLoader"
	    println urlPaths*.toString()
	    if (classLoader.parent) {
	        printClassPath(classLoader.parent)
	    }
	}

}