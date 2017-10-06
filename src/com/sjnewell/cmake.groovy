package com.sjnewell;

// Workaround to deal with setters not being called
def propertyMissing(name) {
    null
}

// A prefix to put before calling CMake.  This will be passed directly to the
// shell command.
//   prefix: the text to put before invoking CMake
def setPrefix(prefix) {
    cmakePrefix = prefix
}

// Set common flags for both C and C++.
//   flags: flags for both C and C++
def setCommonFlags(flags) {
    cmakeCommonFlags = flags
}

// Set extra flags to pass to CMake.
//   flags: CMake specific flags
def setCMakeFlags(flags) {
    cmakeFlags = flags
}

// Set flags that should be passed to the C compiler
//   flags: flags that will be passed to the C compiler
def setCFlags(flags) {
    cmakeCFlags = flags
}

// Set flags that should be passed to the C++ compiler
//   flags: flags that will be passed to the C++ compiler
def setCxxFlags(flags) {
    cmakeCxxFlags = flags
}

// Tell CMake to generate ninja files instead of its default
def useNinja() {
    generator = 'Ninja'
}

// Generate compile commands
def exportCompileCommands() {
    exportCommands = true
}

// Use a specific C compiler
//   cc: the C compiler to use
def setCCompiler(cc) {
    cmakeCc = cc
}

// Use a specific C++ compiler
//   cxx: the C++ compiler to use
def setCxxCompiler(cxx) {
    cmakeCxx = cxx
}

// Run the CMake command.
//   src: path to the source directory (where CMakeLists.txt lives)
def configure(src) {
    def ex_cflags   = ''
    def ex_cxxflags = ''

    // build the C/CXX flags
    if(cmakeCommonFlags) {
        ex_cflags = cmakeCommonFlags
        ex_cxxflags = cmakeCommonFlags
    }
    if(cmakeCFlags) {
        ex_cflags += " ${cmakeCFlags}"
    }
    if(cmakeCxxFlags) {
        ex_cxxflags += " ${cmakeCxxFlags}"
    }

    // build up the cmake command line
    def args = ''

    // start with any prefix and cmake flags
    if(cmakePrefix) {
        args = "${cmakePrefix}"
    }
    args += ' cmake'
    if(cmakeFlags) {
        args += " ${cmakeFlags}"
    }
    if(exportCommands) {
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
    if(cmakeCc) {
        args += " -DCMAKE_C_COMPILER=${cmakeCc}"
    }
    if(cmakeCxx) {
        args += " -DCMAKE_CXX_COMPILER=${cmakeCxx}"
    }

    // turn on Ninja if we need to
    if(generator) {
        args += " -G ${generator}"
    }

    // configure it
    sh "${args} ${src}"
}
