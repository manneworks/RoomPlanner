package roomplanner

import static org.junit.Assert.*
import org.junit.*

import roomplanner.api.RoomCategory as RoomCategoryDto
import roomplanner.api.Room as RoomDto
import roomplanner.api.Reservation as ReservationDto
import roomplanner.api.RoomAssignment as RoomAssignmentDto
import roomplanner.api.Plan as PlanDto

class RoomPlannerRemoteServiceTests {

    def roomsDto
    def roomCategoriesDto
    def reservationsDto
    def roomAssignmentsDto

    @Before
    void setUp() {
        // Setup logic here

    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testHessian() {
        def roomPlannerHessianService

        PlanDto planDto = roomPlannerHessianService.doPlan(
            roomsDto, roomCategoriesDto, reservationsDto, roomAssignmentsDto)
    }
}
