sanitizerBuild
==============
A :code:`sanitizerBuild` builds a target and runs tests using a sanitizer.


Requirements
------------
  - A variable (:code:`sanitizier`) that controls which sanitizer to build
    with.  It's suggested this is configured as a build argument to make it
    easy to support new sanitizers in the future (see example).
  - Clang is used as the compiler.
  - Builds of this type are expected to be built indirectly.  See sanitizers_
    for more information.


Steps
-----
In addition to the `common steps`_, a coverageBuild performs:

1. configure_
2. build_
3. test_


Notes
-----
  - Flags required to build with the sanitizer are included automatically.


Example
-------
::

    sanitizerBuild {
        git = 'https://github.com/snewell/bureaucracy'
        // Use the 'sanitizer' string argument
        sanitizer = "${this.sanitizer}"
    }

.. _sanitizers: sanitizers.rst

.. _build: ../step/build.rst
.. _common steps: ../step/common-steps.rst
.. _configure: ../step/configure.rst
.. _test: ../step/test.rst
