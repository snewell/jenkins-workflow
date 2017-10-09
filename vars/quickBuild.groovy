// Build a C/C++ project configured with CMake and count the warnings.
//
// This is intended to be used with extra compilers, not as a primary build
// tool.
//
// DEPENDENCIES:
//   - cmake
//   - make
//
// USAGE:
//   You need to provide a valid git uri Jenkins can handle (this value is
//   passed directly to Jenkins's git command).
//
//   cc - C compiler to use
//   cxx - C++ compiler to use
//
// EXAMPLE:
//  quickBuild {
//    git = '/path/to/git/uri'
//    cc = 'gcc-7'
//    cxx = 'g++-7'
//  }
//

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def build = new com.sjnewell.genericBuild()
    def data = build.buildMap(config)

    data.buildDir = 'build'
    data.countWarnings = true

    def flags = new com.sjnewell.compileFlags()
    def requiredFlags = flags.usefulFlags()  + ' ' +
                        flags.debugFlags()   + ' ' +
                        flags.warningFlags()
    if(config.containsKey('commonFlags')) {
        data.commonFlags = "${requiredFlags} ${commonFlags}"
    }
    else {
        data.commonFlags = requiredFlags
    }

    def steps = [
        new com.sjnewell.step.configure(),
        new com.sjnewell.step.build()
    ]

    build.run(data, steps)
}
