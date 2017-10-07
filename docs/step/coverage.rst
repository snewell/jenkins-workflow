coverage
========
Calculate code coverage on executed unit tests.


Requirements
------------
  - :code:`gcov`
  - :code:`lcov`
  - :code:`genhtml`


Variables
---------
  - :code:`buildDir` (**required**) - The build directory to use.
  - :code:`coverageFilters` - An array of patterns passed to :code:`gcov` to
    strip filenames from coverage analysis.  This is useful to avoid including
    unit test sources in the results.


Artifacts
---------
  - Code coverage analysis.  This is a directory (coverage); index.html is the
    entry point to the full report.


.. _Ninja: https://ninja-build.org/
