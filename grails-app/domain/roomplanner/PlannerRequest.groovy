package roomplanner

import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class PlannerRequest {

	Long	timestamp
	String 	licenseKey
	Long 	requestDuration

    static constraints = {
    }

   	static mapping = {
		timestamp column: '`TIMESTAMP`'
	}

}
