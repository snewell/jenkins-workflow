package com.sjnewell.step;

def execute(args) {
    def archiveStuff = { patterns ->
        for(pattern in patterns) {
            archiveArtifacts pattern
        }
    }

    archiveStuff args.archivePatterns
    if(args.containsKey('buildDir')) {
        archiveStuff args.buildArchivePatterns
    }
}


