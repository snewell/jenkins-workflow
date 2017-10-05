// Build a C/C++ project configured with CMake using a Clang's sanitizers.
//
// DEPENDENCIES:
//   - cmake
//   - ninja
//   - clang
//
// USAGE:
//   You need to provide a valid git uri Jenkins can handle (this value is
//   passed directly to Jenkins's git command).  You'll also need to provide
//   a sanitizer for clang to use.
//
// EXAMPLE:
//  sanitizerBuild {
//    git = '/path/to/git/uri'
//    sanitizer = 'address'
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
            new build_step(),
            new test_step()
        ]

        git = config.git
        buildDir = 'build'
        ninja = true
        cc = 'clang'
        cxx = 'clang++'
        commonFlags = flags.usefulFlags()  + ' ' +
                      flags.debugFlags()   + ' ' +
                      flags.warningFlags() + ' ' +
                      flags.sanitizerFlags(config.sanitizer)
    }
}
