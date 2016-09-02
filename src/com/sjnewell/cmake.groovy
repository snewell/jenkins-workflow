package com.sjnewell;

def propertyMissing(name) {
    null
}

def setCommonFlags(flags) {
    cmakeCommonFlags = flags
}

def setCMakeFlags(flags) {
    cmakeFlags = flags
}

def setCFlags(flags) {
    cmakeCFlags = flags
}

def setCxxFlags(flags) {
    cmakeCxxFlags = flags
}

def setCCompiler(cc) {
    cmakeCc = cc
}

def setCxxCompiler(cxx) {
    cmakeCxx = cxx
}

def configure(src) {
    def ex_cflags   = ""
    def ex_cxxflags = ""

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

    def args = ""
    if(cmakeFlags) {
        args = cmakeFlags
    }
    if(ex_cflags) {
        args += " -DCMAKE_C_FLAGS=\"${ex_cflags}\""
    }
    if(ex_cxxflags) {
        args += " ${args} -DCMAKE_CXX_FLAGS=\"${ex_cxxflags}\""
    }
    if(cmakeCc) {
        args += " ${args} -DCMAKE_C_COMPILER=${cmakeCc}"
    }
    if(cmakeCxx) {
        args += " ${args} -DCMAKE_CXX_COMPILER=${cmakeCxx}"
    }
    sh "cmake ${args} ${src}"
}
