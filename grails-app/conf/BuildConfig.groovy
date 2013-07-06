grails.project.work.dir = "../../work/RoomPlanner"

grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

def env = System.getProperty('grails.env')

if (env in ['development', 'test']) {
    grails.plugin.location.'roomplanner-api' = '../roomplanner-api'
}

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

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"

        mavenRepo name: 'HMS',
                  root: 'http://192.168.0.35:8080/artifactory/HMS'
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		
        compile 'org.optaplanner:optaplanner-core:6.0.0.Beta4'
        compile 'org.drools:drools-core:6.0.0.Beta4'
        runtime 'org.drools:drools-compiler:6.0.0.Beta4'
    }

    plugins {
        compile ":cxf:1.1.1"
        compile ":remoting:1.3"

        runtime ":resources:1.2.RC2"
        runtime ":database-migration:1.3.5"

        runtime ":hibernate:$grailsVersion"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:$grailsVersion"

        compile ":codenarc:0.18.1"
        test ":code-coverage:1.2.6"


        if (env == 'jenkins') {
            runtime "grails-roomplanner-api:grails-roomplanner-api:0.1"
        }

    }
}

codenarc.reports = {
    Jenkins('xml') {                    
        outputFile = 'target/analysis-reports/CodeNarcReport.xml'
        title = 'CodeNarc Analysis Report'             
    }
}
