package com.sjnewell;

def buildMap(data) {
    def ret = [:]
    data.each { key, value ->
        ret[key] = value
    }
    return ret
}

def run(data, steps) {
    node {
        def runEachStep = { localSteps ->
            localSteps.each { step ->
                step.execute(data)
            }
        }

        // run the pre-build stuff that everything needs
        runEachStep([
            new com.sjnewell.step.checkout(),
            new com.sjnewell.step.prepare(),
            new com.sjnewell.step.copyArtifacts()
        ])

        runEachStep(steps)

        // and the post-build stuff that everything needs
        runEachStep([
            new com.sjnewell.step.archive()
        ])
    }
}
