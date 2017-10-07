package com.sjnewell.step;

// This step configures CMake.
//
// DEPENDENCIES:
//   - cmake
//
// USAGE:
//   You need to provide a valid build directory (its contents will be erased
//   before being configured).
//
//   You can optionally specify the following variables:
//     - cmakeFlags - exra flags to pass to CMake
//     - cFlags - flags used by C builds
//     - cxxFlags - flags used by C++ builds
//     - commonFlags - flags used by both C and C++ builds
//     - cc - the C compiler to use
//     - cxx - the C++ compiler to use
//     - configPrefix - a configuration prefix for CMake
//     - ninja - use Ninja instead of make
//     - exportCommands - generate a compile commands file (commonly used by
//                        other tools)
//
// EXAMPLE:
//   This is designed to be part of genericBuild.
//
//   genericBuild {
//      steps = [
//          // ...
//          new configure_step(),
//          // ...
//      ]
//
//      buildDir = 'build'
//      cc = 'clang'
//      ninja = true
//      // ...
//   }
//

def execute(args) {
    stage('Configure') {
        def src = pwd()

        dir(args.buildDir) {
            deleteDir()

            cmake = new com.sjnewell.cmake()
            if(args.containsKey('cmakeFlags')) {
                cmake.setCMakeFlags(args.cmakeFlags)
            }
            if(args.containsKey('commonFlags')) {
                cmake.setCommonFlags(args.commonFlags)
            }
            if(args.containsKey('cFlags')) {
                cmake.setCFlags(args.cFlags)
            }
            if(args.containsKey('cxxFlags')) {
                cmake.setCxxFlags(args.cxxFlags)
            }
            if(args.containsKey('cc')) {
                cmake.setCCompiler(args.cc)
            }
            if(args.containsKey('cxx')) {
                cmake.setCxxCompiler(args.cxx)
            }
            if(args.containsKey('configPrefix')) {
                cmake.setPrefix(args.configPrefix)
            }
            if(args.containsKey('ninja') && args.ninja) {
                cmake.useNinja()
            }
            if(args.containsKey('export') && args.export) {
                cmake.exportCompileCommands()
            }
            cmake.configure(src)
        }
    }
}
