package com.sjnewell.step;

// step([
//      $class: 'CopyArtifact',
//      filter: '**/TexHelper.cmake',
//      fingerprintArtifacts: true,
//      flatten: true,
//      projectName: 'latex-common',
//      target: 'foo'])

def call(args) {
    if(args.containsKey('copyArtifacts')) {
        args.copyArtifacts.each{ info ->
            def arguments = [$class: 'CopyArtifact']

            info.each { key, value ->
                arguments[key] = value
            }
            step(arguments)
        }
    }
}
