package com.sjnewell;

// Run the CMake command.
//   src: path to the source directory (where CMakeLists.txt lives)
def configure(src, buildArgs) {
    def ex_cflags   = ''
    def ex_cxxflags = ''

    // build the C/CXX flags
    if(buildArgs.containsKey('commonFlags')) {
        ex_cflags = buildArgs.commonFlags
        ex_cxxflags = buildArgs.commonFlags
    }
    if(buildArgs.containsKey('cFlags')) {
        ex_cflags += " ${buildArgs.cFlags}"
    }
    if(buildArgs.containsKey('cxxFlags')) {
        ex_cxxflags += " ${buildArgs.cxxFlags}"
    }

    // build up the cmake command line
    def args = ''

    // start with any prefix and cmake flags
    if(buildArgs.containsKey('configPrefix')) {
        args = "${buildArgs.configPrefix}"
    }
    args += ' cmake'
    if(buildArgs.containsKey('cmakeFlags')) {
        args += " ${buildArgs.cmakeFlags}"
    }
    if(buildArgs.containsKey('export')) {
        args += ' -DCMAKE_EXPORT_COMPILE_COMMANDS=1'
    }

    // if we have C/CXX flags add the appropriate arguments
    if(ex_cflags) {
        args += " -DCMAKE_C_FLAGS=\"${ex_cflags}\""
    }
    if(ex_cxxflags) {
        args += " -DCMAKE_CXX_FLAGS=\"${ex_cxxflags}\""
    }

    // if we have custom compilers, set them
    if(buildArgs.containsKey('cc')) {
        args += " -DCMAKE_C_COMPILER=${buildArgs.cc}"
    }
    if(buildArgs.containsKey('cxx')) {
        args += " -DCMAKE_CXX_COMPILER=${buildArgs.cxx}"
    }

    // turn on Ninja if we need to
    if(buildArgs.containsKey('buildGenerator')) {
        args += " -G ${buildArgs.buildGenerator}"
    }

    // configure it
    return "${args} ${src}"
}
