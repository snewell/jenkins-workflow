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
1. checkout_
2. configure_
3. build_
4. test_
5. archive_


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

.. _archive: ../step/archive.rst
.. _build: ../step/build.rst
.. _checkout: ../step/checkout.rst
.. _configure: ../step/configure.rst
.. _test: ../step/test.rst
