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
    def requiredFlags = flags.usefulFlags()  + ' ' +
                        flags.debugFlags()   + ' ' +
                        flags.warningFlags()
                        flags.sanitizerFlags(body.sanitizer)
    genericBuild {
        steps = [
            new checkout_step(),
            new configure_step(),
            new build_step(),
            new test_step()
        ]

        config.buildDir = 'build'
        config.ninja = true
        config.cc = 'clang'
        config.cxx = 'clang++'
        if(config.containsKey('commonFlags')) {
            config.commonFlags = "${requiredFlags} ${commonFlags}"
        }
        else {
            config.commonFlags = requiredFlags
        }

        args = config
    }
}
