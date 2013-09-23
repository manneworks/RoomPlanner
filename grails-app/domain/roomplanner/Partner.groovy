package roomplanner

class Partner {

	String username
	String password
	boolean enabled = true

    static constraints = {
    	username unique:true
    }

   	static mapping = {
		password column: '`password`'
	}

}
