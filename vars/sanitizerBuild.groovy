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

    node {
        def src = pwd()
        stage('Checkout') {
            git "${config.git}"
        }

        stage('Configure') {
            dir('build') {
                deleteDir()
                def commonFlags = compileFlags.usefulFlags() + ' ' + compileFlags.debugFlags() + ' ' + compileFlags.warningFlags() + ' ' + compileFlags.sanitizerFlags(config.sanitizer)

                cmake = new com.sjnewell.cmake()
                cmake.setCommonFlags(commonFlags)
                cmake.setCCompiler("clang")
                cmake.setCxxCompiler("clang++")
                cmake.useNinja()
                cmake.configure(src)
            }
        }

        stage('Build') {
            dir('build') {
                sh 'ninja'
            }
        }

        stage('Test') {
            dir('build') {
                sh 'ninja test'
            }
        }
    }
}
