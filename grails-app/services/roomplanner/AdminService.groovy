package roomplanner

import grails.transaction.Transactional

//@Transactional
class AdminService {

	def grailsApplication 
	
	def createPartner(def username = null, def password = null) {

		if (username == null) {
			log.trace("Generating UUID...")
			username = UUID.randomUUID().toString()
			while (Partner.findAllByUsername(username).size() > 0) {
			 	username = UUID.randomUUID().toString()
			} 
			log.trace("...done. [$username]")
		}

		if (password == null) {
			log.trace("Generate password...")
			password = generatePassword()
			log.trace("...done. [$password]")
		}	

		log.trace("Creating user: [$username]:[$password]...")
		def partner = new Partner(
			username: username,
			password: password,
			enabled: true
			)

		if(!partner.save(flush:true)) {
			log.error("Partner [$username] was not created")
			throw new Exception("Partner was not created")
		}

		log.trace("...done.")
		partner
	}

    def checkPartner(def username, def password) {
    	Partner.findByUsernameAndPasswordAndEnabled(username, password, true) != null
    }

    private def generatePassword() {
    	def PASSWORD_LENGTH = 16
    	def PASSWORD_SYMBOLS = [*'A'..'Z',*'a'..'z',*'0'..'9']

    	def value = ""
    	for (i in 0..<PASSWORD_LENGTH) {
    		int index = Math.ceil(Math.random() * PASSWORD_SYMBOLS.size())
    		value = value + PASSWORD_SYMBOLS[index-1]
    	}

    	value
    }

    def createSystemUser(def username, def password) {

		log.trace("Creating system user: [$username]:[$password]...")
    	def systemUser = new SystemUser(
    		username: username,
    		password: password
    		)
		if(!systemUser.save(flush:true)) {
			log.error("SystemUser [$username] was not created")
			throw new Exception("SystemUser was not created")
		}

		log.trace("...done.")
		systemUser
    }

    def requestsServed() {
    	def startNanoTime = Setting.findByKey('startNanoTime')
    	PlannerRequest.countByTimestampGreaterThan(new Long(startNanoTime?.value))
    }

    def requestsServedTotal() {
    	PlannerRequest.count()
    }

    def uptime() {
 	    def startNanoTime = Setting.findByKey('startNanoTime')
    	def uptime = (startNanoTime != null) ? uptime = (long)(System.nanoTime() - new Long(startNanoTime.value)) / 1000000L : 0
    	uptime
    }

    def getStatus() {
    	def optaplannerVersion
    	def applicationVersion = grailsApplication.metadata['app.version']
    	def uptime = uptime()
    	def requestsServed = requestsServed()
    	def requestsServedTotal = requestsServedTotal()
    	def javaVersion

    	[
    		optaplannerVersion: optaplannerVersion,
    		applicationVersion: applicationVersion,
    		uptime: uptime,
    		requestsServed: requestsServed,
    		requestsServedTotal: requestsServedTotal,
    		javaVersion: javaVersion
    	]
    }
}
