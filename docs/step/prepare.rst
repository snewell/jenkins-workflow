prepare
=======
Prepare the source in some way.  This is useful if the build has to modify the
source in some way, such as modifying the build system (e.g., you're doing a
build with extra flags and the upstream configuration includes
:code:`-Werror`).

The functions will be called from the node executing the build.


Variables
---------
  - :code:`prepare` - An array of things to execute.  The current build
    configuration will be passed as a map, *including internal variables*.


Notes
-----
  - This step will *not* appear in the build summary.
