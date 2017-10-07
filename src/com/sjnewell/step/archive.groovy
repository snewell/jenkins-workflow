package com.sjnewell.step;

def execute(args) {
    def src = pwd()

    def archivePatterns = []
    if(args.containsKey('archivePatterns')) {
        archivePatterns = args.archivePatterns
    }

    dir(args.buildDir) {
        for(pattern in archivePatterns) {
            echo "archiving ${pattern}"
            archiveArtifacts pattern
        }
    }
}


