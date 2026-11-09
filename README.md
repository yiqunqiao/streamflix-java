# StreamFlix++

A Java console application for managing movies, television series, songs, podcasts and games. Built on the COMP90041 course framework by Yiqun Qiao.

## Features

- Content models built with inheritance, interfaces and enums, including television seasons and episodes.
- Watch history, watchlists and audio playlists, with duplicate handling and list updates.
- Content and user-session file loading, CSV persistence, command-line validation and exception handling.

## Source snapshot

The 27 Java files in `src/` were copied from the saved Assignment 2 Ed workspace. Student ID and email header lines were removed from this upload copy; application logic and original attribution were retained.

## Dependencies and validation

A JDK and the matching course-format content files are required. The original course datasets, personal session records, reports and assessment documents are not included. `StreamingApp` accepts four arguments: user name, number of genres, content file and television-series file. It reads or writes `data/userSessionData.csv` relative to the working directory.

The source snapshot was checked for completeness and personal information. Java compilation and end-to-end execution have not been performed in this environment.
