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
com.manulaiko.blackeye.simulator.collectable.Builder:67
```java
            ((Collectable)this._object).addReward(reward);
```

I still don't find it correct to have casting `_object` each time I need something from the `Collectable`
class, maybe having a base class that defines the object is a bad idea.

However, I like the feeling of using inheritance more than casting `_object`, makes me feel like a pro coder.