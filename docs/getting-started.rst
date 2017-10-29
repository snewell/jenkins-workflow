Getting Started
===============
This workflow library is built around my needs for my projects; in general,
this means C/C++ projects built using CMake_.  The library is designed to be
flexible enough that it's usable in a general sense.


Installing
----------
These scripts are designed to be used via the `Pipeline Global Library`_'s
internal git repository.  It should work fine as a geneirc shared library
(nothing in the scripts assume they're part of the internal git repo), but
that method hasn't been tested as thorougly.

1. Clone this repo to a local system (:code:`git clone
   https://github.com/snewell/jenkins-workflow.git`).
2. Add a remote to your Jenkins server (:code:`git remote add Jenkins
   jenkins:workflowLibs.git`).  For this to work, you'll need an appropriate
   entry in your :code:`.ssh/config` file; you can use this as a template::

    Host jenkins
      HostName 127.0.0.1
      Port 8022
      User snewell
      IdentityFile ~/.ssh/id_rsa
      KexAlgorithms +diffie-hellman-group1-sha1
3. Push to the :code:`jenkins` remote (:code:`git push jenkins master`).  If
   you get errors here, make sure your Jenkins server has been configured for
   ssh access, you've added a public key, and you have the necessary
   permissions.
4. Once pushed, the library should be ready to go.


Using
-----
Create a new Jenkins Pipeline item.  Set whatever options you want and care
about, and enter something along these lines in the Pipeline script box::

    coverageBuild {
        git = 'https://github.com/snewell/bureaucracy'
        testResults = ['**/*_results.xml']
        coverageFilters = ['*_test.cpp']
    }

More information about the build variables is available in the job's
documentation, but it should be fairly intuitive.


Steps in Builds
---------------
The provided builds are designed around a series of common steps, making it
easy to support new features as needed across build types.  Most of these
steps (e.g., Checkout, Build) will show up as stages in the job page, but
there are several that don't create a new stage (mostly when adding a stage
would be noise, such as checking static analysis results).  This is primarily
an implementation detail, but a few things won't be obvious unless you check
full build logs (reduces noise for optional things).


C/C++ Build Types
-----------------
 - clangTidyBuild_ - Analyze a project using clang-tidy
 - coverageBuild_ - Build a project and calculate code coverage
 - cppcheckBuild_ - Analyze a project using cppcheck
 - quickBuild_ - Build a project
 - sanitizerBuild_ - Build a project with a sanitizer and run tests
 - sanitizers_ - Launch a :code:`sanitizerBuild` with each supported sanitizers
 - scanBuild_ - Analyze a project using Clang's static analyzer


.. _CMake: https://cmake.org
.. _Pipeline Global Library: https://github.com/jenkinsci/workflow-cps-global-lib-plugin

.. _clangTidyBuild: builds/clangTidyBuild.rst
.. _coverageBuild: builds/coverageBuild.rst
.. _cppcheckBuild: builds/cppcheckBuild.rst
.. _quickBuild: builds/quickBuild.rst
.. _sanitizerBuild: builds/sanitizerBuild.rst
.. _sanitizers: builds/sanitizers.rst
.. _scanBuild: builds/scanBuild.rst
