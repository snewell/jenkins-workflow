def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def build = new com.sjnewell.genericBuild()
    def data = build.buildMap(config)

    data.buildDir = 'build'

    def requiredArtifacts = [
        [
            flatten: true,
            projectName: config.texHelperBuild,
            filter: '**/TexHelper.cmake'
        ],
        [
            flatten: true,
            projectName: config.texHelperBuild,
            filter: '**/tex-wrapper.sh'
        ]
    ]
    if(data.containsKey('copyArtifacts')) {
        data.copyArtifacts += requiredArtifacts
    }
    else {
        data.copyArtifacts = requiredArtifacts
    }

    def setExBuildersPath = { args ->
        args.envAppend = [
            [PATH: "${pwd()}/ex_builders"]
        ]
    }

    def setCmakeFlags = { args ->
        args.cmakeFlags = "-DCMAKE_MODULE_PATH=${pwd()}"
    }

    def requiredPrep = [
        setExBuildersPath,
        setCmakeFlags
    ]
    if(data.containsKey('prepare')) {
        data.prepare += requiredPrep
    }
    else {
        data.prepare = requiredPrep
    }

    def steps = [
        new com.sjnewell.step.configure(),
        new com.sjnewell.step.build()
    ]

    build.run(data, steps)
}
