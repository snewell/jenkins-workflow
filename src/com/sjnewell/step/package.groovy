package com.sjnewell.step;

def call(args) {
    def packageMethods = [
        'cmake': {
            sh "cmake --build ${args.buildDir} --target package"
        },
        'cpack': {
            dir(args.buildDir) {
                def command = 'cpack'
                if(args.containsKey('cpack_args')) {
                    command += " ${args.cpack_args}"
                }
                sh command
            }
        },
        'cmake+install': {
            withEnv(["DESTDIR=install_dir"]) {
                sh "cmake --build ${args.buildDir} --target install"
            }
            sh "tar cjf ${args.buildDir}/${args.archive_file} -C ${args.buildDir}/install_dir ."
        }
    ]

    if(args.containsKey('package_method')) {
        packageMethods[args.package_method] args
    }
}
