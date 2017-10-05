// A generic configuration for building a variety of projects types.  This
// configuration requires specifying the steps to follow and optional
// configuration for each step.
//
// DEPENDENCIES:
//   (depends on the build options)
//
// USAGE:
//   You need to provide a series of steps to follow.  Different steps have
//   different requirements.
//
// EXAMPLE:
//  genericBuild {
//    steps = ['checkout', 'configure', 'build', 'test']
//    git = '/path/to/a/git/repo'
//    buildDir = 'build'
//    commonFlags = '-pipe'
//    cFlags = '-std=c99'
//  }
//
// STEPS:
//   checkout: Clone a git repository
//      git [required] - uri of the git repository to clone
//
//   configure: Configure the project with cmake
//      buildDir [required] - the directory to perform the build
//      cFlags
//      cxxFlags
//      commonFlags [optional] - the flags to use in the build
//
//      cc
//      cxx [optional] - the C and C++ compilers to use
//
//      ninja [optional] - true if ninja should be used as the build system
//      configPrefix [optional] - a program to invoke before running cmake
//
//   build: Build the project
//      buildPrefix [optional] - a program to run before building step
//
//   test: Run unit tests
//   coverage: Gather code coverage reports
//

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        for(step in config.steps) {
            step config
        }
    }
}
