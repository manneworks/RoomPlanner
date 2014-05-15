package roomplanner

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode

@ToString(includeNames = true, includeFields = true)
@EqualsAndHashCode
class Partner {

	String username
	String password
	boolean enabled = true

    static constraints = {
    	username nullable: false, blank: false, unique: true
    	password nullable: false, blank: false
    	enabled nullable: false
    }

   	static mapping = {
		password column: '`PASSWORD`'
	}

}
