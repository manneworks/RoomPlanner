package roomplanner

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode

@ToString(includeNames = true, includeFields = true)
@EqualsAndHashCode
class PlannerRequest {

	Long	timestamp
	String 	licenseKey
	Long 	requestDuration

    static constraints = {
    	timestamp nullable: false
    	licenseKey nullable: false
    	requestDuration nullable: false
    }

   	static mapping = {
		timestamp column: '`TIMESTAMP`'
	}

}
