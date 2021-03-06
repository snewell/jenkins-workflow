package com.sjnewell.step;

// This step runs clang-tidy on a CMake project
//
// DEPENDENCIES:
//   - clang-tidy
//
// USAGE:
//   You need to provide a valid build directory.
//
// EXAMPLE:
//   This is designed to be part of genericBuild.
//
//   genericBuild {
//      steps = [
//          // ...
//          new clangTidy_step(),
//          // ...
//      ]
//
//      buildDir = 'build'
//   }
//

def call(args) {
    stage('Clang Tidy') {
        def outputFile = 'clang-tidy-output'
        // Chainsaw the clang-tidy command:
        //  1. look for any files listed in the compilation database
        //  2. use awk to just take the filename
        //  3. remove any files in build directory
        //  4. remove the quotes wrapping the filename
        //  5. remove duplicate entries (shared and static libs)
        //  6. feed the list of files to clang-tidy via xargs
        def fullBuildDir = "${pwd()}/${args.buildDir}"
        sh "grep '[[:space:]]*\"file\"[[:space:]]*:[[:space:]]' '${args.buildDir}/compile_commands.json' |" +
           "awk '{ print \$2 }' |" +
           "grep -v '${fullBuildDir}' |" +
           "sed 's/\\\"//g' |" +
           'sort | uniq |' +
           "xargs clang-tidy -p ${args.buildDir} " +
           '-checks=' +
                   'modernize-*,performance-*,readability-*,' +
                   'bugprone-*,cppcoreguidelines-*,' +
                   'cppcoreguidelines-pro-*,portability-*,misc-*,' +
                   'cert-*,hicpp-* ' +
           "-header-filter=. >${outputFile}"

        def countFile = "${outputFile}.counts"
        sh "grep -o '\\[[a-z\\-]*\\]\$' ${outputFile} |" +
           "sort | uniq -c > ${countFile}"
        archiveArtifacts outputFile
        archiveArtifacts countFile
    }
}
