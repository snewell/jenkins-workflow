
def call(args) {
    stage('Checkout') {
        git args.git
    }
}
