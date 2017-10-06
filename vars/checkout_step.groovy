// This step performs a project checkout.
//
// DEPENDENCIES:
//   - git
//
// USAGE:
//   You need to provide a valid git url for checkout.
//
// EXAMPLE:
//   This is designed to be part of genericBuild.
//
//   genericBuild {
//      steps = [
//          new checkout_step(),
//          // ...
//      ]
//
//      git = 'some/path/to/git'
//      // ...
//   }
//

def call(args) {
    stage('Checkout') {
        git args.git
    }
}
