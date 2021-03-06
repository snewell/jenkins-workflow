package com.sjnewell.step;

// This step runs built tests.
//
// DEPENDENCIES:
//   - one of:
//     - make
//     - ninja
//
// USAGE:
//   You need to provide a valid build directory.
//
//   You can optionally specify the following variables:
//     - buildPrefix - prepended to the build tool
//     - ninja - use ninja instead of make
//     - testResults - an array patterns containing junit-formatted xml that
//                     should be gathered for test results
//
// EXAMPLE:
//   This is designed to be part of genericBuild.
//
//   genericBuild {
//      steps = [
//          // ...
//          new test_step(),
//          // ...
//      ]
//
//      buildDir = 'build'
//      ninja = true
//      testResults = ['*_results.xml', '*_test.xml']
//      // ...
//   }
//
// ASSUMPTIONS:
//   A "test" target is available.
//

def call(args) {
    stage('Test') {
        def buildPrefix = ''

        def testResults = []
        if(args.containsKey('testResults')) {
            testResults = args.testResults
        }
        if(args.containsKey('buildPrefix')) {
            buildPrefix = args.buildPrefix
        }

        try {
            sh "${buildPrefix} cmake --build ${args.buildDir} --target test"
        }
        catch(err) {
            // a test failed, so mark the build as unstable
            currentBuild.result = "UNSTABLE"
        }

        // gather the test results if anything was specified
        for(pattern in testResults) {
            junit pattern
        }
    }
}
