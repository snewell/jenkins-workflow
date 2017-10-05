
def call(args) {
    stage('Coverage') {
        def src = pwd()

        dir(args.buildDir) {
            coverage = new com.sjnewell.lcov()
            def count = 0
            coverage.gather(src, "coverage.info.${count}")
            if(args.containsKey('coverageFilters')) {
                for(filter in args.coverageFilters) {
                    def next = count + 1
                    coverage.trim("coverage.info.${count}",
                                "coverage.info.${next}",
                                filter)
                    count = next
                }
            }
            coverage.process("coverage.info.${count}", 'coverage')
            archiveArtifacts 'coverage/**'
        }
    }
}
