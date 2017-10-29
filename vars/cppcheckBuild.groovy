// Run cppcheck against a project.
//
// DEPENDENCIES:
//   - cmake
//   - cppcheck
//
// USAGE:
//   You need to provide a valid git uri Jenkins can handle (this value is
//   passed directly to Jenkins's git command).
//
// EXAMPLE:
//  cppcheckBuild {
//    git = '/path/to/git/uri'
//  }
//

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def build = new com.sjnewell.genericBuild()
    def data = build.buildMap(config)
    build.setIfEmpty(data, 'buildDir', 'build')
    data.export = true

    def steps = [
        new com.sjnewell.step.configure(),
        new com.sjnewell.step.cppcheck()
    ]
    build.run(data, steps)
}
