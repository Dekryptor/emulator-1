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
 * mysql:mysql-connector:5.1.38

The *.jar* files are located in *lib*.

Emulator Build
--------------
The file `bin/emulator.jar` contains the latest working build, use it if you want to test it instead of compiling
the sources.
The file `bin/emulator_unstable.jar` contains the latest compilable build.
Of course, it's full of bugs whenever I'm adding new features.

Contributing
------------
Everyone is free to add the things they want to, however there must be some rules so this doesn't
become a copy of a well-known repo.

So, every pull request must be simple an concise. I don't want endless commit logs of PR.
If you have fixed an unfixable bug which didn't allow the emulator to safely send a packet, its PR
would look something like this:

```
Fixed unfixable bug when sending packets
========================================

It happened that sometimes a packet failed to be sent, this was due to a poor coded `send` method.

Now the socket connection is verified before sending a packet.

Commit log:
 * Improved `send` method.
 * Forgot to document it and properly format it.
```

And the PR will be happily accepted and merged.

If the unfixable bug requires a lot more of work, open an issue explaining your ideas so we can discuss it.

And finally, about the code of PR, it must be documented (I don't want to read a 100 paragraphs docblock,
keep it simple, stupid) and its format must be the same as the rest of the whole project
(4 spaces indentation, LF line-ending chars, class and method braces on new line...).
