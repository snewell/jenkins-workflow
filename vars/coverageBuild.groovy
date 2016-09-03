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
// ASSUMPTIONS:
//   - The default toolchain accepts the "-coverage" flag
//   - Only one level of filtering is required (*_test.cpp)

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        def src = pwd()
        stage('Checkout') {
            git "${config.git}"
        }

        stage('Configure') {
            dir('build') {
                deleteDir()
                cmake = new com.sjnewell.cmake()
                cmake.setCxxFlags("-coverage")
                cmake.configure(src)
            }
        }

        stage('Build') {
            dir('build') {
                sh 'make'
            }
        }

        stage('Test') {
            dir('build') {
                sh 'make test'
            }
        }

        stage('Coverage') {
            dir('build') {
                coverage = new com.sjnewell.lcov()
                coverage.gather(src, 'coverage.info')
                coverage.trim('coverage.info', 'coverage.info.trimmed', '*_test.cpp')
                coverage.process('coverage.info.trimmed', 'coverage')
                archiveArtifacts 'coverage/**'
            }
        }
    }
}
