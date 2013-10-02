grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "../../work/RoomPlanner"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: false, //[maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: false, //[maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: false, //[maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

def env = System.getProperty('grails.env')

if (env in ['development', 'test']) {
    grails.plugin.location.'roomplanner-api' = '../roomplanner-api'
    grails.plugin.location.'roombix-ui' = '../roombix-ui'
    grails.server.port.http = 8080
}
else {
   grails.server.port.http = 80
}


grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        if (env == 'jenkins'|| env == 'prod') {
            mavenRepo 'http://192.168.0.35:8080/artifactory/HMS'
        }

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        runtime 'mysql:mysql-connector-java:5.1.26'

        compile 'org.optaplanner:optaplanner-core:6.0.0.CR4'
        compile 'joda-time:joda-time:2.3'

        // WSS4J
        compile 'org.apache.ws.security:wss4j:1.6.7'
        compile 'org.apache.cxf:cxf-rt-ws-security:2.6.2'

        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        compile ":cxf:1.1.1"
        compile ":remoting:1.3"

        compile ":quartz2:2.1.6.2"

        runtime ":resources:1.2.1"

        // compile ":lesscss-resources:1.3.3"
        // runtime ":jquery:1.10.2"
        // compile ":jquery-ui:1.8.24"
        // compile ":jquery-mobile:1.1.0.5"

        runtime ":database-migration:1.3.6"

        //runtime ":hibernate:3.6.10.1" 
        runtime ":hibernate4:4.1.11.1"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:7.0.42"

        test (":spock:0.7") {
            exclude "spock-grails-support"
            export = false
        }   

        test (":codenarc:0.19") {
            export = false
        }

        if (env == 'jenkins') {
            test (":code-coverage:1.2.6") {
                export = false
            }
        }
        //test ":build-test-data:2.0.5"

        if (env == 'jenkins' || env == 'prod') {
           runtime "grails-roomplanner-api:grails-roomplanner-api:0.5.20"
           runtime "grails-roombix-ui:grails-roombix-ui:0.1.4"
        }

    }
}

codenarc.reports = {
    Jenkins('xml') {                    
        outputFile = 'target/analysis-reports/CodeNarcReport.xml'
        title = 'CodeNarc Analysis Report'             
    }
}
