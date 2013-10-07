modules = {
    application {
        resource url:'js/application.js'
    }
    
    'bootstrap-dummy' { resource url: [plugin: 'twitter-bootstrap', dir: 'less', file: 'bootstrap.less'], attrs:[rel: "stylesheet/less", type:'css'] }
    
    plannerBootstrap {
	    dependsOn 'jquery, bootstrap-dummy, font-awesome'
    	resource url:'less/custom-bootstrap.less', attrs:[rel: "stylesheet/less", type:'css']
    }
}