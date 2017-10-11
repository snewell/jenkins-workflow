copyArtifacts
=============
Copy artifacts from another build to this build.


Variables
---------
  - :code:`copyArtifacts` - An array of artifacts to copy.  Each entry should
    be a map containing arguments that will be passed *directly* to the
    :code:`CopyArtifacts` command.  At minimum, this should include
    :code:`projectName` and :code:`filter` or no artifacts will be copied.


Notes
-----
  - This step will *not* appear in the build summary.
