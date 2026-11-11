# Validation record

Reference date: 11 November 2026. Python 3.12.14; OpenJDK 21.0.13.

`python3 test_app.py` compiles all 27 source files, then starts the actual Java
application with scripted input and a separate temporary session directory for
each test. All six tests passed.

| Test | Behaviour checked |
| --- | --- |
| Startup and restoration | Both input files load; exit saves a session; a second run restores it |
| Watchlist and history | Repeated additions are rejected; playing records a movie; restart retains history |
| Audio playlist | Playlist creation, case-insensitive name lookup, duplicate prevention and restoration |
| Media canvases | TV series, podcasts and games can be opened and exited; movies and songs covered above |
| Command-line validation | Missing arguments, invalid genre counts and missing files are handled |
| Malformed catalogue | A bad numeric field is reported and skipped; the app continues and exits normally |

`demo.txt` contains a real catalogue browsing session; `demo.png` renders an
excerpt of that output. These are local regression checks for the portfolio
version, not a claim about unobserved course hidden tests.
