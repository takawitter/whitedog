# Setup eclipse #

WhiteDog project uses following version of Eclipse and plugins

  * Eclipse 3.4.1 http://eclipse.org (Ganymede SR1)
  * AJDT 1.6.2(with AspectJ1.6.3) http://download.eclipse.org/tools/ajdt/34/update
  * Subclipse 1.4.7 http://subclipse.tigris.org/update_1.4.x
  * IvyDE 2.0.0.beta1(with Ivy 2.0.0.cr2) http://www.apache.org/dist/ant/ivyde/updatesite

Note: jp.whitedog.ShareAspect needs **hasmember()** type pattern. So you must turned on Has Member option (AspectJ Compiler -> Other -> Has Member).

# Building WhiteDog #

Launch Eclipse and checkout projects you need.

  * build - common build scripts (required)
  * ivyrepository - ivy repository (not required. required only for module publishers)
  * whitedog - WhiteDog core (required)
  * whitedog-jgroups - session implementation using JGroups (optional)
  * whitedog-local - session implementation of in-vm (optional)
  * whitedog-sample-autosessionpaint - sample paint application using @ConnectAtHere annotation (depends on whitedog-jgroups)
  * whitedog-sample-chat - sample chat application (depends on whitedog-jroups)
  * whitedog-sample-localdoublepaint - sample paint application (depends on whitedog-local)
  * whitedog-sample-paint - sample paint application (depends on whitedog-jgroups)

When you checkout projects, Eclipse and IvyDE plugin automatically download required libraries and build. After build succeeded you can run sample programs in Eclipse.

You can also create jar file (into dist directory) and gather required jars (into dist/lib directory) by executing build.xml as ant script. If you are behind the firewall (you must access internet via proxy), you need to set proxy parameters to your Java VM(which runs ant) because IvyDE don't use Eclipse's network settings.

```
-Dhttp.proxyHost=your.proxy.host -Dhttp.proxyPort=port
```