
def call(args) {
    stage('Test') {
        def buildTool = 'make'
        def buildPrefix = ''

        if(args.containsKey('buildPrefix')) {
            buildPrefix = args.buildPrefix
        }
        if(args.containsKey('ninja')) {
            buildTool = 'ninja'
        }

        dir(args.buildDir) {
            sh "${buildPrefix} ${buildTool} test"

            // gather the test results if anything was specified
            if(args.containsKey('testResults')) {
                for(pattern in args.testResults) {
                    junit pattern
                }
            }
        }
    }
}
