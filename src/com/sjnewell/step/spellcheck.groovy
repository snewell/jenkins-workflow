
def call(args) {
    stage('Spellcheck') {
        def modeArg = ''
        if(args.containsKey('mode')) {
            modeArg = "--mode=${args.mode}"
        }
        def currentDir = pwd()
        def cmd = "while read f; do " +
                      "cat \${f} |" +
                      "aspell list ${modeArg} -p ${pwd()}/${args.dictionary} |" +
                      "sed \"s,^\\(.*\\),\\1     \${f},g\";" +
                   "done | " +
                   "sort | " +
                   "uniq -c |" +
                   "sort -r"

        def count = 1
        args.spellcheckPatterns.each { pattern ->
            // there's probably a less-hacky way to do this
            def outfile = "spellcheck.out.${count}"
            sh "echo ${pattern} >${outfile}"
            sh "echo >>${outfile}"
            sh "find ${pattern} | ${cmd} >>${outfile}"
            ++count
            archiveArtifacts outfile
        }
    }
}
