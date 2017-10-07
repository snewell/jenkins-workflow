configure
=========
Configure a project using CMake_.


Requirements
------------
  - CMake_


Variables
---------
  - :code:`buildDir` (**required**) - The build directory to use.  This
    directory will be wiped prior to configuring.
  - :code:`cmakeFlags` - Extra arguments to pass to :code:`cmake`.  It's
    usually better to use another option unless none of them fit your needs.
  - :code:`commonFlags` - Flags that should applied to both C and C++ builds.
  - :code:`cFlags` - Flags that should applied to C builds.
  - :code:`cxxFlags` - Flags that should applied to C++ builds.
  - :code:`cc` - The C compiler to use (if not specified, :code:`cmake` will
    use whatever it feels like).
  - :code:`cxx` - The C++ compiler to use (if not specified, :code:`cmake` will
    use whatever it feels like).
  - :code:`configPrefix` - A string to prefix to the :code:`cmake` invocation.
    This isn't needed in most cases, but can be useful if a tool manipulates
    the environment in some way (e.g., :code:`scan-build`).
  - :code:`ninja` - Use Ninja_ as a build system instead of the default
    (:code:`make`).  This can make builds faster, but some tools don't work
    well if the project is configured/built with :code:`ninja`.
  - :code:`export` - Have :code:`cmake` generate a compile commands file.
    This isn't required in most cases, but some tools require this file.


.. _CMake: https://cmake.org
.. _Ninja: https://ninja-build.org/
