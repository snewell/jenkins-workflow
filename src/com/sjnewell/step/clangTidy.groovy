package com.sjnewell.step;

// This step runs clang-tidy on a CMake project
//
// DEPENDENCIES:
//   - clang-tidy
//
// USAGE:
//   You need to provide a valid build directory.
//
// EXAMPLE:
//   This is designed to be part of genericBuild.
//
//   genericBuild {
//      steps = [
//          // ...
//          new clangTidy_step(),
//          // ...
//      ]
//
//      buildDir = 'build'
//   }
//

def call(args) {
    stage('Clang Tidy') {
        def outputFile = 'clang-tidy-output'
        // Chainsaw the clang-tidy command:
        //  1. find anything ending in c or cpp
        //  2. strip out files in the build directory
        //  3. run clang-tidy
        sh "find . -name '*.c' -o -name '*.cpp' |" +
           "grep -v ${args.buildDir} |" +
           "xargs clang-tidy -p ${args.buildDir} " +
           "-checks=modernize-*,performance-*,readability-* " +
           "-header-filter=. >${outputFile}"
        archiveArtifacts outputFile
    }
}
