// A generic configuration for building a variety of projects types.  This
// build grabs a node and runs the requested steps.
//
// By itself, this build does *nothing*.
//
// DEPENDENCIES:
//   (depends on the build steps)
//
// USAGE:
//   You need to provide a series of steps to follow.  Different steps have
//   different requirements.  Provided steps are included and end in '_step'.
//
// EXAMPLE:
//   genericBuild {
//     steps = [
//          new checkout_step(),
//          new configure_step()
//          new build_step()
//          new test_step(
//     ]
//    git = '/path/to/a/git/repo'
//    buildDir = 'build'
//    commonFlags = '-pipe'
//    cFlags = '-std=c99'
//  }
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
