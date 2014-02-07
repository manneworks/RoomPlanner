
import grails.util.GrailsUtil
import roomplanner.AdminService
import roomplanner.Partner
import roomplanner.Setting

import org.apache.ws.security.handler.WSHandlerConstants
import org.apache.ws.security.WSConstants
import org.apache.ws.security.validate.Validator
import org.apache.ws.security.WSSecurityEngine
import org.apache.ws.security.validate.UsernameTokenValidator
import org.apache.ws.security.WSSecurityException
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
import org.apache.cxf.frontend.ServerFactoryBean
import org.apache.ws.security.message.token.UsernameToken
import org.apache.ws.security.handler.RequestData

import javax.xml.namespace.QName

class BootStrap {

    def grailsApplication

	ServerFactoryBean roomPlannerSoapServiceFactory

    def init = { servletContext ->

		def startTimeInstance = Setting.findOrCreateByKey('startTime')
		startTimeInstance.value = System.currentTimeMillis()
		startTimeInstance.save()

		def adminService = new AdminService()

		//Register some wss4j security
		log.trace("Register WSS4J security stuff...")
        Map<String, Object> inProps = [:];
        inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        Map<QName, Validator> validatorMap = new HashMap<QName, Validator>();
        
        validatorMap.put(WSSecurityEngine.USERNAME_TOKEN, new UsernameTokenValidator() {
            @Override
            protected void verifyPlaintextPassword(UsernameToken usernameToken, RequestData data) 
            throws WSSecurityException {
             	log.trace("Checking user credentials [$usernameToken.name]:[$usernameToken.password]...")
             	def partner = adminService.checkPartner(usernameToken.name, usernameToken.password)
             	if (!partner) {
                    throw new WSSecurityException("Wrong partner credentials")
                } 
                log.trace("...done.")
            }
        });

        inProps.put(WSS4JInInterceptor.VALIDATOR_MAP, validatorMap);
        roomPlannerSoapServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inProps))
        log.trace("...done.")

		switch (GrailsUtil.environment) {
			case "development":
				adminService.createPartner(
					"184f4c1f-d814-4124-9adb-4bb4d445d0a6",
					"qAEX2X2NKXYhvvtz"
				)
				adminService.createSystemUser(
					"planner",
					"test"
				)
				break
		}
		
		log.info("Application started...")
    }


    def destroy = {
    }
}
