package com.sjnewell.step;

def call(args) {
    def packageMethods = [
        'cmake': {
            sh "cmake --build ${args.buildDir} --target package"
        },
        'cpack': {
            dir(args.buildDir) {
                echo 'trace'
                def command = 'cpack'
                if(args.containsKey('cpack_args')) {
                    command += " ${args.cpack_args}"
                }
                sh command
            }
        }
    ]

    if(args.containsKey('package_method')) {
        packageMethods[args.package_method] args
    }
}
