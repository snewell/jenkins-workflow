test
====
Run unit tests.


Requirements
------------
 - Either :code:`make` or :code:`ninja`.
 - All tests can be run by running the :code:`test` target (e.g.,
   :code:`make test`).
 - Tests emit results in a junit-compatible XML format.  `Google Test`_
   supports this using the :code:`"--gtest_output=xml:<filename>"` argument.


Variables
---------
  - :code:`buildDir` (**required**) - The build directory to use.
  - :code:`testResults` (**required**) - An array of patterns to use for
    gathering test results.  These patterns will be passed directly to the
    :code:`junit` command.
  - :code:`buildPrefix` - Something to prefix to the build command.  This
    isn't needed in most cases, but some tools (:code:`scan-build`) adjust the
    environment during a build so they can run.
  - :code:`ninja` - Use Ninja_ instead of :code:`make`.  This makes builds
    faster in some cases, but not all tools work well.


Notes
-----
If any of the tests fail, the build will be marked as unstable.


.. _Google Test: https://github.com/google/googletest
.. _Ninja: https://ninja-build.org/
