build
=====
Build a configured project.


Variables
---------
  - :code:`buildDir` (**required**) - The build directory to use.
  - :code:`buildPrefix` - Something to prefix to the build command.  This
    isn't needed in most cases, but some tools (:code:`scan-build`) adjust the
    environment during a build so they can run.
  - :code:`countWarnings` - Include warning information in the build
    artifacts.  This will include both the raw warnings from the compiler and
    a sorted count of warnings.  The count assumes the flag to emit the
    warning was included in output (this is true for both :code:`gcc` and
    :code:`clang`).
  - :code:`ninja` - Use Ninja_ instead of :code:`make`.  This makes builds
    faster in some cases, but not all tools work well.


Artifacts
---------
If :code:`countWarnings` is :code:`true`, warning information is captured.
The files will be archived even if no warnings are emitted.


.. _Ninja: https://ninja-build.org/
