package com.sjnewell;

def buildMap(data) {
    def ret = [:]
    data.each { key, value ->
        ret[key] = value
    }
    return ret
}

def setIfEmpty(data, key, value) {
    if(!data.containsKey(key)) {
        data[key] = value
    }
}

def setOrAppend(data, key, value) {
    if(data.containsKey(key)) {
        data[key] += " ${value}"
    }
    else {
        data[key] = value
    }
}

def run(data, steps) {
    def doBuild = {
        def runEachStep = { localSteps ->
            localSteps.each { step ->
                step(data)
            }
        }

        // run the pre-build stuff that everything needs
        runEachStep([
            new com.sjnewell.step.checkout(),
            new com.sjnewell.step.copyArtifacts(),
            new com.sjnewell.step.prepare()
        ])

        runEachStep(steps)

        // and the post-build stuff that everything needs
        runEachStep([
            new com.sjnewell.step.package(),
            new com.sjnewell.step.archive()
        ])
    }

    if(data.containsKey('node_label')) {
        node(data.node_label) {
            doBuild()
        }
    }
    else {
        node {
            doBuild()
        }
    }
}
