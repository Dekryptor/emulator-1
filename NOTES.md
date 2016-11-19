Dev Notes
=========

Just ignore this file, is where I will write things that I have to do, thoughts or problems whenever I need to.

Tuesday, October 18 2016
------------------------
Probably it will be a nice idea to modify Main class so it tracks the current state of the app and the `exit` method
can perform the necessary options.
For example, if the servers are running, close the servers and then exit.

Thursday, October 20 2016
-------------------------
com.manulaiko.blackeye.simulator.map.collectable.Builder:67
```java
            ((Collectable)this._object).addReward(reward);
```

I still don't find it correct to have casting `_object` each time I need something from the `Collectable`
class, maybe having a base class that defines the object is a bad idea.

However, I like the feeling of using inheritance more than casting `_object`, makes me feel like a pro coder.

Friday, November 04 2016
------------------------
It seems that settings packet just don't work.

However, each resolution has their own settings, for example the `WINDOW_SETTINGS` and `MINIMAP_SCALE` sub-commands relies on the resolution ID, so instead of storing the raw settings it will be better to save them in a JSON  *(copied from other ps :/)*:

`MINIMAP_SCALE`
```json
[
    8,
    8,
    8,
    8,
    8
]
```

`WINDOW_SETTINGS`
```json
[
    "0,265,3,0,1,424,3,1,3,562,400,1,5,5,2,1,10,5,176,1,13,85,62,0,22,245,200,0,23,604,116,1,24,182,37,0",
    "0,275,5,0,1,500,5,1,3,766,377,1,5,5,5,1,10,5,288,1,13,187,50,0,22,347,188,0,23,500,9,1,24,284,25,0",
    "0,273,5,0,1,497,5,1,3,762,521,1,5,8,5,1,10,8,280,1,13,187,122,0,22,347,260,0,23,804,138,1,24,284,97,0",
    "0,376,6,0,1,601,6,1,3,1020,520,1,5,10,10,1,10,4,330,1,13,315,122,0,22,475,260,0,23,1060,131,1,24,412,97,0",
    "0,444,5,0,1,674,5,1,3,1020,700,1,5,10,10,1,10,2,420,1,13,315,212,0,22,475,350,0,23,1059,200,1,24,412,187,0"
]
```

`SLOTMENU_POSITION`
```json
[
    "284,477",
    "312,451",
    "358,592",
    "478,593",
    "478,593" // Find this
]
```

`RESIZABLE_WINDOWS` *todo Find out defaults for resolution ID 1*
```json
[
    "5,251,142,20,303,217",
    "5,240,150,20,308,246", // Find this
    "5,240,150,20,308,246",
    "5,240,150,20,321,171",
    "5,240,150,20,321,171"  // And this
]
```

`MAINMENU_POSITION`
```json
[
    "320,502",
    "349,480",
    "394,622",
    "514,623",
    "514,623"  // Find this
]
```

Available resolutions:
 * 0: 820x600
 * 1: 1024x576
 * 2: 1024x720
 * 3: 1280x720
 * 4: 1280x900

Sunday, November 06 2016
------------------------

As for now, settings works like a charm, they're updated in the database and multiple resolutions are working, I've never been so proud of making something this easy.

The only thing that isn't working is the quickbar slots, the values are updated in the database and the packet (*0|7|QUICKBAR_SLOT|...*) is properly sent and received but for some reason the client interprets it as an empty packet (*0|7|QUICKBAR_SLOT|-1,-1...*),

Thursday, November 10 2016
--------------------------

Laser attack system is working, but only for Account against NPC.
The way it's implemented lets NPC attack other NPCs, idk if it's a good idea making an event where NPCs attack between them.

Anyway, I still need to finish the reward (spawn cargo box) and NPC against Account logic.

Maybe I'll move `Attack._destroy` to `Account` and `Attack._respawn` to `NPC`.
This way it would be possible to automatically respawn an account once it's destroyed.

A thing about `UpdaterManager` is that sometimes it throws `ConcurrentModificationException` when an event is unsubscribed and the thread stills looping through it, it's actually harmless and everything works as expected, idk if is worth fixing it.

And idk what else I've done :/

Saturday, November 19 2016
--------------------------

The portals are working as expected, you can use them and that shit,
the only problem is that when you use the portal, you can't see the portals
of the new map, although the packets are sent, they don't appear in the client.
However they still there and can be used.
