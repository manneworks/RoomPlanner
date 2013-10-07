package roomplanner

class SystemUser {

	String username
	String password

    static constraints = {
    	username nullable: false, blank: false, unique: true
    	password nullable: false, blank: false
    }

   	static mapping = {
		password column: '`password`'
	}
}
