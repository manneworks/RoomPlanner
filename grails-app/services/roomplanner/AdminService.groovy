package roomplanner

import grails.transaction.Transactional

import command.ListParams
import utils.StatisticsHelper

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
    	Partner.findByUsernameAndPasswordAndEnabled(username, password, true)
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
    	def startTime = Setting.findByKey('startTime')
    	PlannerRequest.countByTimestampGreaterThan(new Long(startTime?.value))
    }

    def requestsServedTotal() {
    	PlannerRequest.count()
    }

    def getRequestList(ListParams listParams) {
        listParams.sort = 'timestamp'
        listParams.order ='desc'
        PlannerRequest.list(listParams.params)
    }

    def getRecentRequestList() {
        def listParams = new ListParams(max:10, sort: 'timestamp', order: 'desc')
        PlannerRequest.list(listParams.params)
    }

    def getPartnerCount() {
        Partner.count()
    }

    def getPartnerList(ListParams listParams) {
        listParams.sort = 'id'
        listParams.order ='asc'
        Partner.list(listParams.params)
    }

    def uptime() {
        def startTime = Setting.findByKey('startTime')
     	def uptime = (startTime != null) ? (long)(System.currentTimeMillis() - new Long(startTime.value)) : 0
        uptime
    }

    def getStatus() {
    	def optaplannerVersion = grailsApplication.config.roomplanner.optaplanner.version
        def hibernateVersion = grailsApplication.config.roomplanner.hibernate.version
        def roomplannerApiVersion = grailsApplication.config.roomplanner.roomplannerApi.version
        def roombixUiVersion = grailsApplication.config.roomplanner.roombixUi.version
        def mysqlConnectorVersion = grailsApplication.config.roomplanner.mysql.connector.version
    	def applicationVersion = grailsApplication.metadata['app.version']
    	def uptime = uptime()
    	def requestsServed = requestsServed()
    	def requestsServedTotal = requestsServedTotal()
    	def javaVersion = System.getProperty('java.version')
        def partnerCount = getPartnerCount()

    	[
    		optaplannerVersion: optaplannerVersion,
    		applicationVersion: applicationVersion,
    		uptime: uptime,
    		requestsServed: requestsServed,
    		requestsServedTotal: requestsServedTotal,
    		javaVersion: javaVersion,
            roomplannerApiVersion: roomplannerApiVersion,
            roombixUiVersion: roombixUiVersion,
            hibernateVersion: hibernateVersion,
            mysqlConnectorVersion: mysqlConnectorVersion,
            partnerCount: partnerCount
    	]
    }

    def deletePartner(def id) {
        def partnerInstance = Partner.get(id)
        partnerInstance.delete(flush:true)
    }

    def disablePartner(def id) {
        def partnerInstance = Partner.get(id)
        partnerInstance.enabled = false
        partnerInstance.save(flush:true)
    }

    def enablePartner(def id) {
        def partnerInstance = Partner.get(id)
        partnerInstance.enabled = true
        partnerInstance.save(flush:true)
    }

}
