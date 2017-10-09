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
//     - buildGenerator - the buildGenerator to use
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
        if(args.containsKey('buildGenerator')) {
            cmake.setGenerator(args.buildGenerator)
        }
        if(args.containsKey('export') && args.export) {
            cmake.exportCompileCommands()
        }

        def buildEnvArray = { input, suffix ->
            ret = []
            input.each { pattern ->
                pattern.each { key, value ->
                    ret += ["${key}${suffix}=${value}"]
                }
            }
            return ret
        }

        // build lists of array overrides and appends
        def envOverride = []
        if(args.containsKey('envOverride')) {
            envOverride = buildEnvArray(args.envOverride, '')
        }

        def envAppend = []
        if(args.containsKey('envAppend')) {
            envAppend = buildEnvArray(args.envAppend, '+WHATEVER')
        }

        dir(args.buildDir) {
            deleteDir()
            // This is ugly.
            // TODO: check if it's safe to call withEnv with an empty list
            if(envOverride) {
                withEnv(envOverride) {
                    if(envAppend) {
                        withEnv(envAppend) {
                            cmake.configure(src)
                        }
                    }
                    else {
                        cmake.configure(src)
                    }
                }
            }
            else if(envAppend) {
                withEnv(envAppend) {
                    cmake.configure(src)
                }
            }
            else {
                cmake.configure(src)
            }
        }
    }
}
