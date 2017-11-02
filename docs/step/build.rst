build
=====
Build a configured project.


Variables
---------
  - :code:`buildDir` (**required**) - The build directory to use.
  - :code:`buildArgs` - Extra arguments to pass to the build tool; the
    arguments will be passed without modification.
  - :code:`buildPrefix` - Something to prefix to the build command.  This
    isn't needed in most cases, but some tools (:code:`scan-build`) adjust the
    environment during a build so they can run.
  - :code:`buildTargets` - An array of targets that should be built during
    this step.  If not specified, the default target will be run.
  - :code:`countWarnings` - Include warning information in the build
    artifacts.  This will include both the raw warnings from the compiler and
    a sorted count of warnings.  The count assumes the flag to emit the
    warning was included in output (this is true for both :code:`gcc` and
    :code:`clang`).


Artifacts
---------
If :code:`countWarnings` is :code:`true`, warning information is captured.
The files will be archived even if no warnings are emitted.
