# rand-mp3
Loop random samples of mp3s

Based on this [tweet](https://twitter.com/martinaustwick/status/830206120094552069): 

> I want to do a thing in Java/Processing. Pick a random mp3 file from a folder, pick a random x seconds, loop it.
> Press a key to change a sample or file. Tried using the Processing Sound library and it a) had a 5s delay b) after a few iterations...
> ..stopped playing audio, Processing stopped responding, in some cases my mac stopped responding.

Don't really understand the full goal of this but thought I'd have a go at hacking it out with the javaFX mediaplayer because haven't really done any javaFX or media stuff in java and seemed like an interesting problem. 

It only has a dependency on pure java 8/maven (as javaFX is bundled with jdk8).

Given a folder full of music, it will pick a random mp3 and choose a random part of the file to play. 
It will then loop a random lengthed sample from a random position inside the track.
Pressing enter will then move onto another file and start playing a new sample.
Typing `quit` and hitting enter will then exit the program. 

The main entry point of the program is the `Runner` class

Limitations:
 * Doesn't record the resulting sound
 * Only displays how much of the track was skipped after hitting enter
 * Only tested on linux
 * Doesn't generate an executable jar
 * Hacked together / code is a bit crappy
