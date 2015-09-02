The features of the WhiteDog System are:

  * Framework for the object sharing applications.
    * i.e. shared paint board or chat application.
  * Annotation based design.
    * Put **@Share** annotation to the method you want to share its execution.
    * Put **@Share** annotation to the field you want to synchronize its state at session start.
  * Pluggable protocol.
    * Currently, UDP multicasting(JGroups) and in-VM(ClassLoader) are supported.

Concept video(based on the former implementation of the WhiteDog System):
  * <a href='http://www.youtube.com/watch?v=ESp8OgCETD0'>WhiteDog System</a>
  * <a href='http://www.youtube.com/watch?v=oq9Lrae7YOQ'>Additional demo</a>
  * <a href='http://www.youtube.com/watch?v=lTwxlGIFxAs'>A part of demonstration at NJT2004</a>

Code Example:
```
  class Canvas{
    @Share
    public void paint(int x, int y, Color color){
       ...
    }
  }
```

WhiteDog System runs on Java5 and depends on AspectJ1.6.

Now Ivy repository is available: http://whitedog.googlecode.com/svn/trunk/ivyrepository/
You can use Ivy repository from your ivy by adding following settings to your ivysettings.xml
```
<ivyrep name="whitedogrep"
	ivyroot="http://whitedog.googlecode.com/svn/trunk/ivyrepository/"
	ivypattern="[organization]/[module]/ivy-[revision].xml"
	artroot="http://whitedog.googlecode.com/svn/trunk/ivyrepository/"
	artpattern="[organization]/[module]/[artifact]-[revision].[ext]"
	/>
```

## News ##
  * _Jan.20 Ivy repository available._
  * _Aug.4  WhiteDog now supports state synchronization at starting session._
  * _Jun.26  Custom peer factory (PeerFactory) and current message sender (peer) identification (Session.getCurrentPeer()) are supported (in trunk). See chat sample for details_
  * _Jun.24  Basic peer management (iterate/add event/remove event) is now supported in JGroupsSession implementation (in trunk)_

## Now working for ##
  * Additional work relating ivy (publishing each module. source or javadoc artifacts)

## Projects ##
| **whitedog** |WhiteDog core classes which doesn't include any session implementation.|
|:-------------|:----------------------------------------------------------------------|
| **whitedog-jgroups** |JGroups session implementation.                                        |
| **whitedog-local** |Local session implementation.                                          |
| **whitedog-sample-paint** |Sample shared paint application.                                       |
| **whitedog-sample-autosessionpaint** |Sample shared paint application that uses automatic session management aspect(jp.whitedog.AutoSessionAspect).|
| **whitedog-sample-localdoublepaint** |Sample locally shared paint application that has two shared paint area. That uses IN-VM(ClassLoader) session.|
| **whitedog-sample-chat** |Sample chat application.  This application explains how to use peer management (and state synchronization)|