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
                cmake.setPrefix('scan-build')
                cmake.useNinja()
                cmake.configure(src)
            }
        }

        stage('Build') {
            dir('build') {
                sh 'scan-build -o scan-results ninja'
            }
        }
    }
}
