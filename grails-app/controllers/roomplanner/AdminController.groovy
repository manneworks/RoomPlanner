package roomplanner

class AdminController {

	def adminService

    def index() { 

    	def statusMap = adminService.getStatus()
    	[
    		status: statusMap
    	]
    }
}
