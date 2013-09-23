package roomplanner

class PlannerRequest {

	Date 	timestamp
	String 	licenseKey
//	String 	user
	Long 	requestDuration

    static constraints = {
    }

   	static mapping = {
		timestamp column: '`timestamp`'
	}

}
