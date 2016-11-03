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
    11, // Resolution ID 0
    10, // Resolution ID 1
    9   // Resolution ID 2
    //...
]
```

`WINDOW_SETTINGS`
```json
[
    "0,18,9,1,1,244,14,1,24,173,63,0,3,558,406,1,5,7,5,0,10,89,198,0,20,-10,370,1,13,85,62,0,23,604,135,1", // 0
    "0,271,38,0,1,481,4,0,24,500,61,0,3,1016,458,1,5,-3,-9,0,10,-2,155,1,20,2,390,1,13,187,50,0,23,835,201,1", // 1
    "0,263,2,1,1,488,1,1,15,724,5,1,3,1016,458,0,5,5,5,0,24,500,61,0,10,-2,155,1,20,-1,382,1,13,187,50,0,23,838,213,1", // 2
    "0,444,-1,0,1,1057,329,1,20,39,530,0,3,1021,528,1,5,-10,-6,0,24,463,15,0,10,101,307,0,36,100,400,0,13,315,122,0,23,1067,132,0"
]
```

`SLOTMENU_POSITION`
```json
[
    "284,477",
    "312,451",
    "358,592",
    "478,593"
]
```

`RESIZABLE_WINDOWS` *todo Find out defaults for resolution ID 1*
```json
[
    "5,251,142,20,303,217",
    "5,240,150,20,308,246", // Find this
    "5,240,150,20,308,246",
    "5,240,150,20,321,171"
]
```

`MAINMENU_POSITION`
```json
[
    "320,502",
    "349,480",
    "394,622",
    "514,623"
]
```

Available resolutions:
 * 0: 820x600
 * 1: 1024x576
 * 2: 1024x720
 * 3: 1280x720
 * 4: 1280x900