package roomplanner

class Setting {

	String key
	String value

    static constraints = {
    	key unique: true
    	value nullable: true
    }

    static mapping = {
		key column: '`key`'
		value column: '`value`'
	}
}
