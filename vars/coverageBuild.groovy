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
//   passed directly to Jenkins's git command).
//
// EXAMPLE:
//  coverageBuild {
//    git = '/path/to/git/uri'
//  }
//
// ASSUMPTIONS:
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
        steps = ['checkout', 'configure', 'build', 'test', 'coverage']

        git = config.git
        buildDir = 'build'
        commonFlags = flags.usefulFlags()  + ' ' +
                      flags.debugFlags()   + ' ' +
                      flags.warningFlags() + ' ' +
                      flags.coverageFlags()
        testResults = ['**/*_results.xml']
        coverageFilters = ['*_test.cpp']
    }
}
