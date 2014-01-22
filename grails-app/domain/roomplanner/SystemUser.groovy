package roomplanner

class SystemUser {

	Long realmCode
	String username
	String password

    static constraints = {
    	realmCode nullable: true
    	username nullable: false, blank: false, unique: true
    	password nullable: false, blank: false
    }

   	static mapping = {
		password column: '`PASSWORD`'
	}
}
