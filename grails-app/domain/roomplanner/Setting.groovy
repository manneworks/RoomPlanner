package roomplanner

class Setting {

	String key
	String value

    static constraints = {
    	key unique: true
    	value nullable: true
    }
}
