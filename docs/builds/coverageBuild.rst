coverageBuild
=============
A :code:`coverageBuild` builds a target, then calculates the amount of code
coverage.


Steps
-----
In addition to the `common steps`_, a coverageBuild performs:

1. configure_
2. build_
3. test_
4. coverage_


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


.. _build: ../step/build.rst
.. _common steps: ../step/common-steps.rst
.. _configure: ../step/configure.rst
.. _coverage: ../step/coverage.rst
.. _test: ../step/test.rst
