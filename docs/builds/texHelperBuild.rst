texHelperBuild
==============
A :code:`texHelperBuild` builds a project that uses the `latex-cmake`_ package
to build (La)TeX documents.  It pulls the necessary artifacts from a
latex-cmake build on the Jenkins server (set :code:`texHelperBuild` in the
build configuration) and adds :code:`${projectRoot}/ex_builders` to
:code:`PATH` during configuration to support custom targets.

If neither of those features is needed, you should use quickBuild_ since it's
mostly identical.


Steps
-----
In addition to the `common steps`_, a texHelperBuild performs:

1. configure_
2. build_


Example
-------
::

    coverageBuild {
        git = 'https://github.com/snewell/writingProject'
        texHelperBuild = 'latex-common'
    }


.. _latex-cmake: https://github.com/snewell/latex-cmake

.. _quickBuild: quickBuild.rst

.. _build: ../step/build.rst
.. _common steps: ../step/common-steps.rst
.. _configure: ../step/configure.rst
