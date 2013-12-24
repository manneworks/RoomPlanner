package roomplanner

import command.ListParams

class RequestController {

	def adminService

    def index(ListParams listParams) {
    	
    	def requestInstanceList = adminService.getRequestList(listParams)
    	def requestInstanceCount = adminService.requestsServedTotal()
    	[
    		requestInstanceList: requestInstanceList,
    		requestInstanceCount: requestInstanceCount,
    	]
    }

    def showRequestDetail() {
        def requestInstance = PlannerRequest.get(params.id)
        if (!requestInstance) {
            flash.message = message(code: 'default.not.found.message', args: [
                message(code: 'license.label', default: 'Request'),
                params.id
            ])
            redirect(action: "index")
            return
        }

        log.debug(requestInstance)
        
        [requestInstance: requestInstance]
    }
}
