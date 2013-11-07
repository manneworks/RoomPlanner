package roomplanner

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult

import org.grails.cxf.utils.EndpointType
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.cxf.utils.GrailsCxfEndpointProperty

import roomplanner.api.RoomCategory as RoomCategoryDto
import roomplanner.api.Room as RoomDto
import roomplanner.api.Reservation as ReservationDto
import roomplanner.api.RoomAssignment as RoomAssignmentDto
import roomplanner.api.Plan as PlanDto
import roomplanner.api.License as LicenseDto
import roomplanner.api.Pricelist as PricelistDto

@GrailsCxfEndpoint(
	expose = EndpointType.JAX_WS,
	properties = [
		@GrailsCxfEndpointProperty(name = "ws-security.enable.nonce.cache", value = "false"), 
		@GrailsCxfEndpointProperty(name = "ws-security.enable.timestamp.cache", value = "false")
	]
	)
// 	importInterceptors  = ["customLoggingInInterceptor"],
// 	outInterceptors = ["customLoggingOutInterceptor"]
class RoomPlannerSoapService {

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
		@WebParam(name="roomAssignmentList") List<RoomAssignmentDto> roomAssignmentsDto,
		@WebParam(name="priceList") PricelistDto pricelistDto
		) {

		roomPlannerService.doPlan(licenseDto, roomCategoriesDto, roomsDto, reservationsDto, roomAssignmentsDto, pricelistDto)

    }
}