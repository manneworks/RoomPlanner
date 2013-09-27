package filters

import roomplanner.SystemUser

class SecurityFilters {  

    def filters = {  
    
        basicAuth(controller:'admin', action:'*') {  
            before = {  
                try {
                    def authString = request.getHeader('Authorization')   

                    if (!authString) {
                        response.status = 401
                        response.addHeader("WWW-Authenticate", "Basic realm=\"Secured Area\"")
                        return false
                    }   

                    def encodedPair = authString - 'Basic '  
                    def decodedPair = new String(new sun.misc.BASE64Decoder().decodeBuffer(encodedPair))
                    def credentials = decodedPair.split(':')  
                    def user = SystemUser.findByUsernameAndPassword(credentials[0],credentials[1])  

                    if (!user) {  
                        log.error("User $credentials not known")
                        response.status = 401
                        return false
                    }
                    
                    return true

                } catch (Exception e) {
                    log.error("Error processing Basic HTTP Authenication", e)
                    response.status = 403
                    return false
                }                
            }  
        }  
    }
}
