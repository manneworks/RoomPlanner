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
                    log.debug("Try credentials: $credentials")
                    
                    def username = credentials?.getAt(0)
                    def password = credentials?.getAt(1)

                    log.debug("Looking for [$username]/[$password]")
                    def user = SystemUser.findByUsernameAndPassword(username, password)  

                    if (!user) {  
                        log.error("User $credentials are not known")
                        response.status = 401
                        response.addHeader("WWW-Authenticate", "Basic realm=\"Secured Area\"")
                        return false
                    }
                    
                    // response.setHeader('Cache-Control', 'no-cache, no-store')
                    // response.setDateHeader('Expires', (new Date()-1).time )
                    // response.setHeader('Pragma', 'no-cache')                    

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
