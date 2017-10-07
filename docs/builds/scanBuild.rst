scanBuild
=========
A :code:`scanBuild` runs :code:`scan-build` over a project.


Requirements
------------
  - :code:`scan-build` (part of Clang/LLVM)


Steps
-----
1. checkout_
2. configure_
3. build_
4. scanResults_
5. archive_


Example
-------
::

    scanBuild {
        git = 'https://github.com/snewell/bureaucracy'
    }


.. _archive: ../step/archive.rst
.. _build: ../step/build.rst
.. _checkout: ../step/checkout.rst
.. _configure: ../step/configure.rst
.. _scanResults: ../step/scanResults.rst
