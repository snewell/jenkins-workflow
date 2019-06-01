package com.sjnewell.step;

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
        // build the checkout structure

        // if something besides git needs support, hopefully it can just be
        // changed here
        def checkoutArgs = [$class: 'GitSCM']

        // looks like it can do mulitple checkouts in example...
        if(args.containsKey('credentials')) {
            checkoutArgs.userRemoteConfigs = [
                [credentialsId: args.credentials, url: args.git]
            ]
        }
        else {
            checkoutArgs.userRemoteConfigs = [
                [url: args.git]
            ]
        }
        checkoutArgs.extensions = [
            [$class: 'CleanCheckout']
        ]

        if(args.containsKey('branch')) {
            checkoutArgs.branches = [
                // multiple branches too...
                [name: args.branch]
            ]
        }
        else {
            // the default branch probably varies based on the SCM being used
            checkoutArgs.branches = [
                [name: '**']
            ]
        }

        // fingers crossed...
        checkout(checkoutArgs)
    }
}
