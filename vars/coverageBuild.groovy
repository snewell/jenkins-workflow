// Build a C/C++ project configured with CMake and gather the code coverage
// report after running tests.  The coverage report will be available in the
// archived artifacts after the pipeline completes (look in the root directory
// for index.html).
//
// DEPENDENCIES:
//   - cmake
//   - make
//   - lcov
//   - gcov
//
// USAGE:
//   You need to provide a valid git uri Jenkins can handle (this value is
//   passed directly to Jenkins's git command).  Test results (in a compatible
//   format) should be provided in the testResults variable (each entry in the
//   list is passed directly to Jenkins's junit command).  Filtering for code
//   coverage should be specified in the coverageFilter variable (filters are
//   used by a com.sjnewell.lcov instance).
//
// EXAMPLE:
//  coverageBuild {
//    git = '/path/to/git/uri'
//    testResults = ['**/*_results.xml']
//    coverageFilters = ['*_test.cpp']
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
            new build_step(),
            new test_step(),
            new coverage_step()
        ]

        config.buildDir = 'build'
        if(config.containsKey('commonFlags')) {
            config.commonFlags = "${requiredFlags} ${config.commonFlags}"
        }
        else {
            config.commonFlags = requiredFlags
        }

        args = config
    }
}
