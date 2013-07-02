(An unlaunched web service)

# last-fm-stats

Collects and displays last.fm users with the most compatible taste in music for a given username.

![screenshot](http://dl.dropbox.com/u/51836583/Screenshots/od.png)

Eventually I want to provide similar statistics not readily accessible from last.fm's interface.

It's also an excuse to mash together Compojure, Hiccup, Garden, and Redis.

## Prerequisites

* [Leiningen][1]
* Redis for caching API hits.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2013 Dan Neumann
