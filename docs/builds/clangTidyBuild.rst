clangTidyBuild
==============
A :code:`clangTidyBuild` analyzes a project using :code:`clang-tidy`.  It does
*not* compile the project.


Steps
-----
In addition to the `common steps`_, a coverageBuild performs:

1. configure_
2. clangTidy_


Example
-------
::

    clangTidyBuild {
        git = 'https://github.com/snewell/bureaucracy'
    }


.. _common steps: ../step/common-steps.rst
.. _configure: ../step/configure.rst
.. _clangTidy: ../step/clangTidy.rst
