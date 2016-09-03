package com.sjnewell;

def propertyMissing(name) {
    null
}

def setPrefix(prefix) {
    cmakePrefix = prefix
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

def useNinja() {
    generator = "Ninja"
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
    if(cmakePrefix) {
        args = "${cmakePrefix}"
    }
    args += " cmake"
    if(cmakeFlags) {
        args += " ${cmakeFlags}"
    }
    if(ex_cflags) {
        args += " -DCMAKE_C_FLAGS=\"${ex_cflags}\""
    }
    if(ex_cxxflags) {
        args += " -DCMAKE_CXX_FLAGS=\"${ex_cxxflags}\""
    }
    if(cmakeCc) {
        args += " -DCMAKE_C_COMPILER=${cmakeCc}"
    }
    if(cmakeCxx) {
        args += " -DCMAKE_CXX_COMPILER=${cmakeCxx}"
    }
    if(generator) {
        args += " -G ${generator}"
    }
    sh "${args} ${src}"
}
