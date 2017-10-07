scanResults
===========
Analyze results of static analysis to determine if there are issues.


Variables
---------
  - :code:`buildDir` (**required**) - The build directory to use.


Artifacts
---------
  - A directory (scan-results) containing issues.  If no issues were detected,
    this directory won't be captured.


Notes
-----
  - This step will *not* show up in the build summary.
  - If any issues are detected, the build will be marked as unstable.


.. _Google Test: https://github.com/google/googletest
.. _Ninja: https://ninja-build.org/
