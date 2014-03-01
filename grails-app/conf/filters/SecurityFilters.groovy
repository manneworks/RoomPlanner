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
                    log.trace("Try credentials: $credentials")
                    
                    def username, password
                    try {
                        username = credentials?.getAt(0)
                    } catch (Exception e) {
                        username = null
                    }
                    try {
                        password = credentials?.getAt(1)    
                    } catch (Exception e) {
                        password = null
                    }
                    

                    log.trace("Looking for [$username]/[$password]")
                    def user = SystemUser.findByUsernameAndPassword(username, password)  

                    if (!user) {  
                        log.error("User $credentials are not known")
                        response.status = 401
                        response.addHeader("WWW-Authenticate", "Basic realm=\"Secured Area\"")
                        return false
                    }
                    log.trace("Found [$username]/[$password]")
                    
                    return true

                } catch (Exception e) {
                    log.error("Error processing Basic HTTP Authenication", e)
                    response.status = 500
                    return false
                }                
            }  
        }  
    }
}
