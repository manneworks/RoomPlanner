package roomplanner

/**
	Provides an entry point for Hessian protocol
*/
class RoomPlannerRemoteService implements roomplanner.api.IRoomPlannerService {

	def roomPlannerService

	static expose = [ 'hessian' ]

	/**
		Implements doPlan method
	*/
	@Override
	def doPlan(
		def license,
		def roomCategoriesDto, 
		def roomsDto, 
		def reservationsDto, 
		def roomAssignmentsDto,
		def pricelistDto
	) {

		roomPlannerService.doPlan(license, roomCategoriesDto, roomsDto, reservationsDto, roomAssignmentsDto, pricelistDto)

    }

}
