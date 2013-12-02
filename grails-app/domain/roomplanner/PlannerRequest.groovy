package roomplanner

class PlannerRequest {

	Long	timestamp
	String 	licenseKey
	Long 	requestDuration

    static constraints = {
    }

   	static mapping = {
		timestamp column: '`timestamp`'
	}

}
