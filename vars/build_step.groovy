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

def call(args) {
    stage('Build') {
        def buildTool = 'make'
        def buildPrefix = ''
        def stderrPath = ''
        def stderrRedirect = ''

        if(args.containsKey('buildPrefix')) {
            buildPrefix = args.buildPrefix
        }
        if(args.containsKey('countWarnings') && args.countWarnings) {
            stderrPath = 'warnings'
            stderrRedirect = " 2>${stderrPath}"
        }
        if(args.containsKey('ninja')) {
            buildTool = 'ninja'
        }

        dir(args.buildDir) {
            sh "${buildPrefix} ${buildTool} ${stderrRedirect}"

            if(args.containsKey('countWarnings') && args.countWarnings) {
                // gcc/clang print the warning in the pattern [-Wname], so
                // chainsaw it
                //  1. grep for just the warning name
                //  2. sort them
                //  3. count the sorted list using uniq -c
                def warningsCount = 'warnings.count'
                sh "grep -o \\\\[-W..*\\\\] ${stderrPath} | sort | uniq -c >${warningsCount}"
                archiveArtifacts warningsCount
            }
        }
    }
}
