package roomplanner

/**
	Provides an entry point for Hessian protocol
*/
class RoomPlannerRemoteService implements roomplanner.api.IRoomPlannerService {

	def grailsApplication

	static expose = [ 'hessian' ]

	/**
		Implements doPlan method
	*/
	@Override
	def doPlan(
		def roomCategoriesDto, 
		def roomsDto, 
		def reservationsDto, 
		def roomAssignmentsDto
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

}
