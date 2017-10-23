package com.sjnewell.step;

// This step runs cppcheck on a CMake project
//
// DEPENDENCIES:
//   - cppcheck
//
// USAGE:
//   You need to provide a valid build directory.
//

def execute(args) {
    stage('cppcheck') {
        def outputFile = 'cppcheck-output'
        sh "cppcheck --enable=all --project=${args.buildDir}/compile_commands.json >${outputFile}"
        archiveArtifacts outputFile
    }
}
