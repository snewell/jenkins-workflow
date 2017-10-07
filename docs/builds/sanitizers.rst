sanitizers
==========
Launch a sanitizerBuild_ with every supported sanitizer.  This build doesn't
do anything directly, but instead triggers a handful of other builds.


Requirements
------------
  - A build job in Jenkins that runs a sanitizerBuild_.  See that build for
    requirements.  This build should be specified using the
    :code:`sanitizerBuild` variable.
  - The name of the sanitizer argument in the sanitizerBuild_.  This is stored
    in the :code:`sanitizer` variable.


Example
-------
::

    sanitizers {
        sanitizer = 'sanitizer'
        sanitizerBuild = 'bureaucracy-sanitizer-build'
    }

.. _sanitizerBuild: sanitizerBuild.rst
