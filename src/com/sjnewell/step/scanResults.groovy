package com.sjnewell.step;

def call(args) {
    def resultsDir = new File("${pwd()}/${args.scanResultsDir}")
    if(!(resultsDir.list() as List).empty) {
        resultsDir = null
        // directory wasn't cleared, so scan-build found something
        archiveArtifacts "${scanResultsDir}/**"
        currentBuild.result = "UNSTABLE"
    }
}
