package roomplanner

class PlannerRequest {

	Date 	timestamp
	String 	licenseCode
	String 	user
	Long 	requestDuration

    static constraints = {
    	timestamp nullable: true
    	licenseCode nullable: true
    	user nullable: true
    	requestDuration nullable: true
    }

}
