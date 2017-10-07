package com.sjnewell.step;

def execute(args) {
    dir(args.buildDir) {
        def resultsDir = new File("${pwd()}/${args.scanResultsDir}")
        if(!(resultsDir.list() as List).empty) {
            // directory wasn't cleared, so scan-build found something
            archiveArtifacts "${args.scanResultsDir}/**"
            currentBuild.result = "UNSTABLE"
        }
    }
}


