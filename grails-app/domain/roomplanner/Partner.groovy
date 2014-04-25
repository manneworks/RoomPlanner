package roomplanner

import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class Partner {

	String username
	String password
	boolean enabled = true

    static constraints = {
    	username nullable: false, blank: false, unique: true
    	password nullable: false, blank: false
    }

   	static mapping = {
		password column: '`PASSWORD`'
	}

}
