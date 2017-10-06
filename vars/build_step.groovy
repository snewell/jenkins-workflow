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

        if(args.containsKey('buildPrefix')) {
            buildPrefix = args.buildPrefix
        }
        if(args.containsKey('ninja')) {
            buildTool = 'ninja'
        }

        dir(args.buildDir) {
            sh "${buildPrefix} ${buildTool}"
        }
    }
}
