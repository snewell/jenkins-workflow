package com.sjnewell.step;

class scanResults {
    def steps
    def scanResultsDir

    scanResults(steps, scanResultsDir) {
        this.steps = steps
        this.scanResultsDir = scanResultsDir
    }

    def call(args) {
        def resultsDir = new File("${steps.pwd()}/${scanResultsDir}")
        if(!(resultsDir.list() as List).empty) {
            // directory wasn't cleared, so scan-build found something
            steps.archiveArtifacts "${scanResultsDir}/**"
            steps.currentBuild.result = "UNSTABLE"
        }
    }
}



