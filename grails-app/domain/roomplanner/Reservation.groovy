package roomplanner

class Reservation {

	Long id
	Long roomCategoryId
	Date dateFrom
	Date dateTo
	Integer adults
	//Boolean nonSmoking
		
    static constraints = {
    }
	
}