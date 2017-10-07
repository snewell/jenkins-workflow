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
    genericBuild {
        steps = [
            new checkout_step(),
            new configure_step(),
            new build_step()
        ]

        config.buildDir = 'build'
        config.buildPrefix = 'scan-build'
        config.configPrefix = 'scan-build -o scan-results'
        config.ninja = true
        if(config.containsKey('commonFlags')) {
            config.commonFlags = "${requiredFlags} ${commonFlags}"
        }
        else {
            config.commonFlags = requiredFlags
        }

        args = config
    }
}
