archive
=======
Archive miscellaneous files.  This is in addition to files picked up
automatically by other steps.


Variables
---------
  - :code:`archivePatterns` - An array of patterns for archival.  These
    patterns are applied at the root of the source tree.
  - :code:`buildArchivePatterns` - Identical to :code:`archivePatterns`, but
    run from the job's build directory.


Artifacts
---------
  - Anything picked up by :code:`archivePatterns` or
    :code:`buildArchivePatterns`.


Notes
-----
  - This step will *not* appear in the build summary.  If you need to debug
    patterns, check the full build logs.
