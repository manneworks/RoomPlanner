package roomplanner

import command.ListParams

class PartnerController {

	def adminService

    def index(ListParams listParams) {
    	
    	def partnerInstanceList = adminService.getPartnerList(listParams)
    	def partnerInstanceCount = adminService.getPartnerCount()
    	[
    		partnerInstanceList: partnerInstanceList,
    		partnerInstanceCount: partnerInstanceCount
    	]
    }

    def createNewPartner() {
    	def partner = adminService.createPartner()
    	redirect(action: "showPartnerDetail", id: partner.id)
    }

	def disablePartner() {
		adminService.disablePartner(params.id)
		redirect(action: "index")
	}

	def deletePartner() {
		adminService.deletePartner(params.id)		
		redirect(action: "index")
	}

    def showPartnerDetail() {
		def partnerInstance = Partner.get(params.id)
		if (!partnerInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'license.label', default: 'Partner'),
				params.id
			])
			redirect(action: "index")
			return
		}
		
		[partnerInstance: partnerInstance]
    }
}
