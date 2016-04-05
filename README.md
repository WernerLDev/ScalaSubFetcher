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

### Command line options
The application now has a commande line option. It takes 2 parameters:
1. The movie file
2. language name (optional)

This will show all English subtitles:
```
sbt run amovie.mk4 English
```

this will show all available subtitles for amovie.mk4
```
sbt run amovie.mk4
```
