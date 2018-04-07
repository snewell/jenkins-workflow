package com.sjnewell.step;

def call(args) {
    def archiveStuff = { patterns ->
        for(pattern in patterns) {
            archiveArtifacts pattern
        }
    }

    archiveStuff args.archivePatterns
    if(args.containsKey('buildDir')) {
        dir(args.buildDir) {
            archiveStuff args.buildArchivePatterns
        }
    }
}


