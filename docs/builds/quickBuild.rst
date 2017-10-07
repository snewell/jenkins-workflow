quickBuild
==========
A :code:`quickBuild` builds a target.  This build is designed to make it easy
to support alternate toolchains to root out errors with migrations and
upgrades.  Tests are not run, but warnings are captured to help verify clean
builds.


Steps
-----
1. checkout_
2. configure_
3. build_
4. archive_


Notes
-----
  - Warnings will be captured.


Example
-------
::

    quickBuild {
        git = 'https://github.com/snewell/bureaucracy'
        cxx = 'g++-6.4.0'
    }


.. _archive: ../step/archive.rst
.. _build: ../step/build.rst
.. _checkout: ../step/checkout.rst
.. _configure: ../step/configure.rst
