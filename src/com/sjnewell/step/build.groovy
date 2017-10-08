package com.sjnewell.step;

// This step builds a CMake project.
//
// DEPENDENCIES:
//   - one of:
//     - make
//     - ninja
//
// USAGE:
//   You need to provide a valid build directory.
//
//   You can optionally specify the following variables:
//     - buildPrefix - something preprended to the build command
//     - nina - use ninja as the build tool (defaults to make)
//     - countWarnings - count warnings emitted by compiler
//
// EXAMPLE:
//   This is designed to be part of genericBuild.
//
//   genericBuild {
//      steps = [
//          // ...
//          new build_step(),
//          // ...
//      ]
//
//      buildDir = 'build'
//      ninja = true
//      // ...
//   }
//

def execute(args) {
    stage('Build') {
        def buildPrefix = ''
        def stderrPath = ''
        def stderrRedirect = ''

        if(args.containsKey('buildPrefix')) {
            buildPrefix = args.buildPrefix
        }
        if(args.containsKey('countWarnings') && args.countWarnings) {
            stderrPath = "${args.buildDir}/warnings"
            stderrRedirect = " 2>${stderrPath}"
        }

        sh "${buildPrefix} cmake --build ${args.buildDir} ${stderrRedirect}"

        if(args.containsKey('countWarnings') && args.countWarnings) {
            // gcc/clang print the warning in the pattern [-Wname], so
            // chainsaw it
            //  1. grep for just the warning name
            //  2. sort them
            //  3. count the sorted list using uniq -c
            def warningsCount = "${stderrPath}.count"
            sh "grep -o \\\\[-W..*\\\\] ${stderrPath} |" +
                "sort |" +
                "uniq -c >${warningsCount}"
            archiveArtifacts stderrPath
            archiveArtifacts warningsCount
        }
    }
}
