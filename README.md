ScalaSubFetcher
===============

Search and download subtitles for your movies and tv shows.


## Compiling and running
This is a SBT project but for the people not familiar with SBT, do this:
- cd to the root directory of the source code and type the following commands:
- sbt
- compile
- run

And you're done. SBT should download all the dependencies for you.

### Installation
I have created a release build which can be found in the build directory of this repository.
Copy the 2 files to a directory which is in your path so you can execute the bash script from anywhere. After you've done that it is as easy as executing the next command from any directory:
```
fetchsubtitle.sh somemoviefile.mk4
```

### Command line options
The application now has a commande line option. It takes 2 parameters:
- The movie file
- language name (optional)

This will show all English subtitles:
```
fetchsubtitle.sh amovie.mk4 English
```

this will show all available subtitles for amovie.mk4
```
fetchsubtitle amovie.mk4
```
