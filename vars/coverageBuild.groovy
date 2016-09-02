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
                cmake = new com.sjnewell.cmake()
                cmake.setCxxFlags("-coverage")
                cmake.configure(src)
            }
        }

        stage('Build') {
            dir('build') {
                sh 'make'
            }
        }

        stage('Test') {
            dir('build') {
                sh 'make test'
            }
        }

        stage('Coverage') {
            dir('build') {
                coverage = new com.sjnewell.lcov()
                coverage.gather(src, 'coverage.info')
                coverage.trim('coverage.info', 'coverage.info.trimmed', '*_test.cpp')
                coverage.process('coverage.info.trimmed', 'coverage')
            }
        }
    }
}
