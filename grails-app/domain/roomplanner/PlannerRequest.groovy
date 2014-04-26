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
    }

   	static mapping = {
		timestamp column: '`TIMESTAMP`'
	}

}
