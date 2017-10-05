
def call(args) {
    stage('Configure') {
        def src = pwd()

        dir(args.buildDir) {
            deleteDir()

            cmake = new com.sjnewell.cmake()
            if(args.containsKey('commonFlags')) {
                cmake.setCommonFlags(args.commonFlags)
            }
            if(args.containsKey('cFlags')) {
                cmake.setCFlags(args.cFlags)
            }
            if(args.containsKey('cxxFlags')) {
                cmake.setCxxFlags(args.cxxFlags)
            }
            if(args.containsKey('cc')) {
                cmake.setCCompiler(args.cc)
            }
            if(args.containsKey('cxx')) {
                cmake.setCxxCompiler(args.cxx)
            }
            if(args.containsKey('configPrefix')) {
                cmake.setPrefix(args.configPrefix)
            }
            if(args.containsKey('ninja') && args.ninja) {
                cmake.useNinja()
            }
            cmake.configure(src)
        }
    }
}
