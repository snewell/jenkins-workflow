
def call(args) {
    stage('Build') {
        def buildTool = 'make'
        def buildPrefix = ''

        if(args.containsKey('buildPrefix')) {
            buildPrefix = args.buildPrefix
        }
        if(args.containsKey('ninja')) {
            buildTool = 'ninja'
        }

        dir(args.buildDir) {
            sh "${buildPrefix} ${buildTool}"
        }
    }
}
