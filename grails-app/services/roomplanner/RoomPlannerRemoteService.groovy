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
