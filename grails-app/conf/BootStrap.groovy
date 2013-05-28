

class BootStrap {

	def grailsApplication

    def init = { servletContext ->

			grailsApplication.config.startNanoTime = System.nanoTime()
    }


    def destroy = {
    }
}
