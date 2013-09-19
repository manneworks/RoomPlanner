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
import roomplanner.api.License as LicenseDto

// @GrailsCxfEndpoint(
// 	importInterceptors  = ["customLoggingInInterceptor"],
// 	outInterceptors = ["customLoggingOutInterceptor"]
// )
//@InInterceptors( classes = [CustomLoggingInInterceptor])
//@OutInterceptors( classes = [CustomLoggingOutInterceptor])
class RoomPlannerSoapService {

	static expose = EndpointType.JAX_WS

	def roomPlannerService
	
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
    	@WebParam(name="license") LicenseDto licenseDto,
		@WebParam(name="roomList") List<RoomDto> roomsDto, 
		@WebParam(name="roomCategoryList") List<RoomCategoryDto> roomCategoriesDto, 
		@WebParam(name="reservationList") List<ReservationDto> reservationsDto, 
		@WebParam(name="roomAssignmentList") List<RoomAssignmentDto> roomAssignmentsDto
		) {

		roomPlannerService.doPlan(licenseDto, roomCategoriesDto, roomsDto, reservationsDto, roomAssignmentsDto)

    }
}