package roomplanner

import roomplanner.api.RoomCategory as RoomCategoryDto
import roomplanner.api.Room as RoomDto
import roomplanner.api.Reservation as ReservationDto
import roomplanner.api.RoomAssignment as RoomAssignmentDto
import roomplanner.api.Plan as PlanDto

/**
	Provides an entry point for Hessian protocol
*/
class RoomPlannerRemoteService implements roomplanner.api.IRoomPlannerService {

	static expose = [ 'hessian' ]

	/**
		Implements doPlan method
	*/
	@Override
	PlanDto doPlan(
		List<RoomCategoryDto> roomCategoriesDto, 
		List<RoomDto> roomsDto, 
		List<ReservationDto> reservationsDto, 
		List<RoomAssignmentDto> roomAssignmentsDto
	) {
		PlanDto planDto = null


		planDto
	}

}
