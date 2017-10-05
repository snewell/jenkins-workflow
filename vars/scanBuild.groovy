// Build a C/C++ project configured with CMake and run Clang's static analysis
// tool.  The scan results will *not* be archived, mostly because I don't know
// how to easily check for the existance of static analysis results (clang will
// delete the report if it doesn't find issues).  If there are issues they'll
// be in the "scan-results" directory.
//
// DEPENDENCIES:
//   - cmake
//   - ninja
//   - scan-build
//
// USAGE:
//   You need to provide a valid git uri Jenkins can handle (this value is
//   passed directly to Jenkins's git command).
//
// EXAMPLE:
//  scanBuild {
//    git = '/path/to/git/uri'
//  }
//
// ASSUMPTIONS:
//   - The default toolchain accepts the "-coverage" flag
//   - Only one level of filtering is required (*_test.cpp)
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
            new build_step()
        ]

        git = config.git
        buildDir = 'build'
        buildPrefix = 'scan-build'
        configPrefix = 'scan-build -o scan-results'
        ninja = true
        commonFlags = flags.usefulFlags()  + ' ' +
                      flags.debugFlags()   + ' ' +
                      flags.warningFlags()
    }
}
