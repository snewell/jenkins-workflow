clangTidyBuild
==============
A :code:`clangTidyBuild` analyzes a project using :code:`clang-tidy`.  It does
*not* compile the project.


Steps
-----
1. checkout_
2. configure_
3. clangTidy_
4. archive_


Example
-------
::

    clangTidyBuild {
        git = 'https://github.com/snewell/bureaucracy'
    }

.. _archive: ../step/archive.rst
.. _checkout: ../step/checkout.rst
.. _configure: ../step/configure.rst
.. _clangTidy: ../step/clangTidy.rst
