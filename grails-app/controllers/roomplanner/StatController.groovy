package roomplanner

import java.util.concurrent.TimeUnit

class StatController {

	def grailsApplication

    def index() { 

    	long uptime = System.nanoTime() - grailsApplication.config.startNanoTime
    	long uptimeSeconds= TimeUnit.NANOSECONDS.toSeconds(uptime)
    	// Convert to format
    	long days    = TimeUnit.SECONDS.toDays(uptimeSeconds);
    	long hours   = TimeUnit.SECONDS.toHours(uptimeSeconds) -
        	           TimeUnit.DAYS.toHours(days);
	    long minutes = TimeUnit.SECONDS.toMinutes(uptimeSeconds) -
	                   TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(uptimeSeconds));
	    long seconds = TimeUnit.SECONDS.toSeconds(uptimeSeconds) -
	                   TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(uptimeSeconds));

    	long requestCount = PlannerRequest.count()

    	[
    		elapsedTimeString: "${days} day(s) ${hours} hour(s) ${minutes} minute(s) ${seconds} seconds",
    		requestCount: requestCount
    	]
    }
}
