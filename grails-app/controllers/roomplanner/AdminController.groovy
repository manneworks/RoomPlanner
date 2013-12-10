package roomplanner

import command.ListParams

class AdminController {

	def adminService

    def index(ListParams listParams) {
    	
    	def requestInstanceList = adminService.getRequestList(listParams)
    	def requestInstanceCount = adminService.requestsServedTotal()
    	def statusMap = adminService.getStatus()
    	[
    		requestInstanceList: requestInstanceList,
    		requestInstanceCount: requestInstanceCount,
    		status: statusMap
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
