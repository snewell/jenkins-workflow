package com.sjnewell.step;

def call(args) {
    archiveArtifacts allowEmptyArchive: true, artifacts: "${args.scanResultsDir}/**"
}
