package com.sjnewell.step;

// This step runs cppcheck on a CMake project
//
// DEPENDENCIES:
//   - cppcheck
//
// USAGE:
//   You need to provide a valid build directory.
//

def call(args) {
    stage('cppcheck') {
        def outputFile = 'cppcheck-output'
        try {
            sh "cppcheck --enable=all --project=${args.buildDir}/compile_commands.json |" +
               "grep -v '^Checking' |" +
               "grep -v '^[[:digit:]][[:digit:]]*/[[:digit:]][[:digit:]]*' >${outputFile}"
        }
        catch(err) {
            // cppcheck complains about include paths when piped for some reason, but the
            // output still seems fine
        }
        archiveArtifacts outputFile
    }
}
