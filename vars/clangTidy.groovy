// Run clang-tidy against a project.
//
// DEPENDENCIES:
//   - cmake
//   - clang-tidy
//
// USAGE:
//   You need to provide a valid git uri Jenkins can handle (this value is
//   passed directly to Jenkins's git command).
//
// EXAMPLE:
//  clangTidy {
//    git = '/path/to/git/uri'
//  }
//

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def flags = new com.sjnewell.compileFlags()
    genericBuild {
        steps = [
            new checkout_step(),
            new configure_step(),
            new clangTidy_step()
        ]

        config.buildDir = 'build'
        config.export = true

        args = config
    }
}
