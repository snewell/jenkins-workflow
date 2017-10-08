// Build a C/C++ project configured with CMake using a Clang's sanitizers.
//
// DEPENDENCIES:
//   - cmake
//   - ninja
//   - clang
//
// USAGE:
//   You need to provide a valid git uri Jenkins can handle (this value is
//   passed directly to Jenkins's git command).  You'll also need to provide
//   a sanitizer for clang to use.
//
// EXAMPLE:
//  sanitizerBuild {
//    git = '/path/to/git/uri'
//    sanitizer = 'address'
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
    data.buildGenerator = 'Ninja'
    data.cc = 'clang'
    data.cxx = 'clang++'

    def flags = new com.sjnewell.compileFlags()
    def requiredFlags = flags.usefulFlags()  + ' ' +
                        flags.debugFlags()   + ' ' +
                        flags.warningFlags() + ' ' +
                        flags.sanitizerFlags(body.sanitizer)
    if(config.containsKey('commonFlags')) {
        data.commonFlags = "${requiredFlags} ${commonFlags}"
    }
    else {
        data.commonFlags = requiredFlags
    }

    def steps = [
        new com.sjnewell.step.checkout(),
        new com.sjnewell.step.configure(),
        new com.sjnewell.step.build(),
        new com.sjnewell.step.test(),
        new com.sjnewell.step.archive()
    ]

    build.run(data, steps)
}
