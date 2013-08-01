import grails.util.Environment	

// Place your Spring DSL code here
beans = {
	customLoggingInInterceptor(roomplanner.utils.CustomLoggingInInterceptor)
    customLoggingOutInterceptor(roomplanner.utils.CustomLoggingOutInterceptor)

 //    Environment.executeForCurrentEnvironment {
	
	// 	test {
	// 		roomPlannerRemoteServiceClient(roomplanner.remote.RoomPlannerRemoteService)
	// 	}
	// }
}
