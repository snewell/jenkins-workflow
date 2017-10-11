scanBuild
=========
A :code:`scanBuild` runs :code:`scan-build` over a project.


Requirements
------------
  - :code:`scan-build` (part of Clang/LLVM)


Steps
-----
In addition to the `common steps`_, a coverageBuild performs:

2. configure_
3. build_
4. scanResults_


Example
-------
::

    scanBuild {
        git = 'https://github.com/snewell/bureaucracy'
    }


.. _build: ../step/build.rst
.. _common steps: ../step/common-steps.rst
.. _configure: ../step/configure.rst
.. _scanResults: ../step/scanResults.rst
