package com.sjnewell.step;

def execute(args) {
    def scanResultsDir = "${args.buildDir}/${args.scanResultsDir}"
    def resultsDir = new File("${pwd()}/${scanResultsDir}")
    if(!(resultsDir.list() as List).empty) {
        // directory wasn't cleared, so scan-build found something
        archiveArtifacts "${scanResultsDir}/**"
        currentBuild.result = "UNSTABLE"
    }
}


