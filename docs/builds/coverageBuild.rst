coverageBuild
=============
A :code:`coverageBuild` builds a target, then calculates the amount of code
coverage.


Steps
-----
1. checkout_
2. configure_
3. build_
4. test_
5. coverage_
6. archive_


Notes
-----
  - Flags required to calculate code coverage will be added to any user-defined
    flags.
  - Warnings will be captured.


Example
-------
::

    coverageBuild {
        git = 'https://github.com/snewell/bureaucracy'
        testResults = ['**/*_results.xml']
        coverageFilters = ['*_test.cpp']
    }


.. _archive: ../step/archive.rst
.. _build: ../step/build.rst
.. _checkout: ../step/checkout.rst
.. _configure: ../step/configure.rst
.. _coverage: ../step/coverage.rst
.. _test: ../step/test.rst
