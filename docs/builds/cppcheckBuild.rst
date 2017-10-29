cppcheckBuild
=============
A :code:`cppcheckBuild` analyzes a project using :code:`cppcheck`.  It does
*not* compile the project.


Steps
-----
In addition to the `common steps`_, a cppcheckBuild performs:

1. configure_
2. cppcheck_


Notes
-----
  - :code:`cppcheck` is run using the :code:`--project` argument.  This is a
    moderately recent addtion, so you may need to update the version of
    cppcheck on your build slaves.


Example
-------
::

    cppcheckBuild {
        git = 'https://github.com/snewell/bureaucracy'
    }


.. _common steps: ../step/common-steps.rst
.. _configure: ../step/configure.rst
.. _cppcheck: ../step/cppcheck.rst
