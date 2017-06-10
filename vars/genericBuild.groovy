// A generic configuration for building a variety of projects types.  This
// configuration requires specifying the steps to follow and optional
// configuration for each step.
//
// DEPENDENCIES:
//   (depends on the build options)
//
// USAGE:
//   You need to provide a series of steps to follow.  Different steps have
//   different requirements.
//
// EXAMPLE:
//  genericBuild {
//    steps = ['checkout', 'configure', 'build', 'test']
//    git = '/path/to/a/git/repo'
//    buildDir = 'build'
//    commonFlags = '-pipe'
//    cFlags = '-std=c99'
//  }
//
// STEPS:
//   checkout: Clone a git repository
//      git [required] - uri of the git repository to clone
//
//   configure: Configure the project with cmake
//      buildDir [required] - the directory to perform the build
//      cFlags
//      cxxFlags
//      commonFlags [optional] - the flags to use in the build
//
//      cc
//      cxx [optional] - the C and C++ compilers to use
//
//      ninja [optional] - true if ninja should be used as the build system
//      configPrefix [optional] - a program to invoke before running cmake
//
//   build: Build the project
//      buildPrefix [optional] - a program to run before building step
//
//   test: Run unit tests
//   coverage: Gather code coverage reports
//

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        def src = pwd()
        def buildTool = 'make'
        def buildPrefix = ''

        if(config.containsKey('ninja') && config.ninja) {
            buildTool = 'ninja'
        }
        if(config.containsKey('buildPrefix')) {
            buildPrefix = config.buildPrefix
        }

        for(step in config.steps) {
            if(step == "checkout") {
                stage('Checkout') {
                    git config.git
                }
            }
            else if(step == "configure") {
                stage('Configure') {
                    dir(config.buildDir) {
                        deleteDir()

                        cmake = new com.sjnewell.cmake()
                        if(config.containsKey('commonFlags')) {
                            cmake.setCommonFlags(config.commonFlags)
                        }
                        if(config.containsKey('cFlags')) {
                            cmake.setCFlags(config.cFlags)
                        }
                        if(config.containsKey('cxxFlags')) {
                            cmake.setCxxFlags(config.cxxFlags)
                        }
                        if(config.containsKey('cc')) {
                            cmake.setCCompiler(config.cc)
                        }
                        if(config.containsKey('cxx')) {
                            cmake.setCxxCompiler(config.cxx)
                        }
                        if(config.containsKey('configPrefix')) {
                            cmake.setPrefix(config.configPrefix)
                        }
                        if(buildTool == 'ninja') {
                            cmake.useNinja()
                        }
                        cmake.configure(src)
                    }
                }
            }
            else if(step == 'build') {
                stage('Build') {
                    dir(config.buildDir) {
                        sh "${buildPrefix} ${buildTool}"
                    }
                }
            }
            else if(step == 'test') {
                stage('Test') {
                    dir(config.buildDir) {
                        sh "${buildPrefix} ${buildTool} test"

                        // gather the test results if anything was specified
                        if(config.containsKey('testResults')) {
                            for(pattern in config.testResults) {
                                junit pattern
                            }
                        }
                    }
                }
            }
            else if(step == 'coverage') {
                stage('Coverage') {
                    dir(config.buildDir) {
                        coverage = new com.sjnewell.lcov()
                        def count = 0
                        coverage.gather(src, "coverage.info.${count}")
                        if(config.containsKey('coverageFilters')) {
                            for(filter in config.coverageFilters) {
                                def next = count + 1
                                coverage.trim("coverage.info.${count}",
                                            "coverage.info.${next}",
                                            filter)
                                count = next
                            }
                        }
                        coverage.process("coverage.info.${count}", 'coverage')
                        archiveArtifacts 'coverage/**'
                    }
                }
            }
        }
    }
}
