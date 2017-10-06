// This step calculates code coverage.
//
// DEPENDENCIES:
//   - lcov
//   - gcov
//
// USAGE:
//   You need to provide a valid build directory.
//
//   You can optionally specify the following variables:
//     - coverageFilters - an array of filters to strip files from coverage
//                         results
//
// EXAMPLE:
//   This is designed to be part of genericBuild.
//
//   genericBuild {
//      steps = [
//          // ...
//          new coverage_step(),
//          // ...
//      ]
//
//      buildDir = 'build'
//      coverageFilters = ['*_test.cpp', '*internal*']
//      // ...
//   }
//
// ASSUMPTIONS:
//   The build needs to have been built with the necessary flags.  This step
//   won't be able to gather results unless tests were actually run.
//

def call(args) {
    stage('Coverage') {
        def src = pwd()

        dir(args.buildDir) {
            coverage = new com.sjnewell.lcov()
            def count = 0
            coverage.gather(src, "coverage.info.${count}")
            if(args.containsKey('coverageFilters')) {
                for(filter in args.coverageFilters) {
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
