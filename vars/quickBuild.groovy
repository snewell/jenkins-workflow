// Build a C/C++ project configured with CMake and count the warnings.
//
// This is intended to be used with extra compilers, not as a primary build
// tool.
//
// DEPENDENCIES:
//   - cmake
//   - make
//
// USAGE:
//   You need to provide a valid git uri Jenkins can handle (this value is
//   passed directly to Jenkins's git command).
//
//   cc - C compiler to use
//   cxx - C++ compiler to use
//
// EXAMPLE:
//  quickBuild {
//    git = '/path/to/git/uri'
//    cc = 'gcc-7'
//    cxx = 'g++-7'
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
                        flags.warningFlags() + ' ' +
                        flags.coverageFlags()
    genericBuild {
        steps = [
            new checkout_step(),
            new configure_step(),
            new build_step()
        ]

        config.buildDir = 'build'
        config.countWarnings = true
        if(config.containsKey('commonFlags')) {
            config.commonFlags = "${requiredFlags} ${commonFlags}"
        }
        else {
            config.commonFlags = requiredFlags
        }

        args = config
    }
}
