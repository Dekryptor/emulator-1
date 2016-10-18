BlackEye Emulator
=================
The best DarkOrbit private server.

Current version: 0.0.0

Emulator Structure
------------------

The source files are under *src/* and are under the package *com.manulaiko.blackeye*.
The main class is *com.manulaiko.blackeye.launcher.Main* and sets everything for starting the servers.

Libraries Used
--------------
The libraries used are (maven coordinates):
 * org.json:json:2.0
 * mysql:mysql-connetor:5.1.38

The *.jar* files are located in *lib*.

Emulator Build
--------------
The file `bin/emulator.jar` contains the latest working build, use it if you want to test it instead of compiling
the sources.
The file `bin/emulator_unstable.jar` contains the latest compilable build.
Of course, it's full of bugs whenever I'm adding new features.