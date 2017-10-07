// Launch builds for each of the supported sanitizers.  sanitizer will wait
// until each of the sanitizer builds finishes before reporting success or
// failure.
//
// DEPENDENCIES:
//   (none)
//
// USAGE:
//   You'll need a Jenkins build that builds using a sanitizer passed as a
//   parameter.
//
// EXAMPLE:
//  sanitizers {
//    sanitizerBuild = 'some-sanitizer-build'
//    sanitizerParam = 'sanitizer'
//  }
//

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    parallel address: {
        build job: config.sanitizerBuild,
              parameters: [
                string(name: config.sanitizer, value: 'address')
              ]
    }, thread: {
        build job: config.sanitizerBuild,
              parameters: [
                string(name: config.sanitizer, value: 'thread')
              ]
    }, udef: {
        build job: config.sanitizerBuild,
              parameters: [
                string(name: config.sanitizer, value: 'undefined')
              ]
    }
}
