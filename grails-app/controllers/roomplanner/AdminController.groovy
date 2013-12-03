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
}
