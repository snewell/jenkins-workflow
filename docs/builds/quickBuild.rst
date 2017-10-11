quickBuild
==========
A :code:`quickBuild` builds a target.  This build is designed to make it easy
to support alternate toolchains to root out errors with migrations and
upgrades.  Tests are not run, but warnings are captured to help verify clean
builds.


Steps
-----
In addition to the `common steps`_, a quickBuild performs:

1. configure_
2. build_


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


.. _build: ../step/build.rst
.. _common steps: ../step/common-steps.rst
.. _configure: ../step/configure.rst
