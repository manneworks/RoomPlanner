extraSrcDirs = ["$basedir/src/rules"]

eventCompileStart = {
   for (String path in extraSrcDirs) {
	  projectCompiler.srcDirectories << path
   }
   copyResources buildSettings.resourcesDir
}

eventCreateWarStart = { warName, stagingDir ->
   copyResources "$stagingDir/WEB-INF/classes"
   
   def buildNumber = System.getenv('BUILD_NUMBER')
   if(buildNumber) {
       ant.propertyfile(file:"${stagingDir}/WEB-INF/classes/application.properties") {
           entry(key:'build.number', value: buildNumber)
       }
   }
}

private copyResources(destination) {
   ant.copy(todir: destination,
			failonerror: false,
			preservelastmodified: true) {
	  for (String path in extraSrcDirs) {
		 fileset(dir: path) {
			exclude(name: '*.groovy')
			exclude(name: '*.java')
		 }
	  }
   }
}