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
        steps.each { step ->
            step.execute(data)
        }
    }
}
