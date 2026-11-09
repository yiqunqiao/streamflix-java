/**
Student Name - Yiqun Qiao
*/
package canvas;

import java.util.Scanner;
import java.util.ArrayList;
import utils.Constants;
import content.VideoContent;
import content.AudioContent;
import content.Playlist;
import content.UserContent;
import enums.ContentType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** Stores user lists and handles the user menu. */
public class User {
    private String name;
    private final ArrayList<UserContent> previouslyWatchedVideos =
            new ArrayList<UserContent>();
    private final ArrayList<UserContent> watchList =
            new ArrayList<UserContent>();
    private final ArrayList<Playlist> playlists = new ArrayList<Playlist>();

    /** Creates a user without a name. */
    public User() {
    }

    /**
     * Creates a user with the given name.
     * @param userName the user name
     */
    public User(String userName) {
        this.name = userName;
    }

    /**
     * Gets the user name.
     * @return the user name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a video loaded from the session file to the watched list.
     * @param video the saved video
     */
    public void restorePreviouslyWatchedVideo(UserContent video) {
        addVideoToList(previouslyWatchedVideos, video);
    }

    /**
     * Adds a video loaded from the session file to the watchlist.
     * @param video the saved video
     */
    public void restoreWatchListVideo(UserContent video) {
        addVideoToList(watchList, video);
    }

    /**
     * Restores saved audio to a playlist, creating the playlist if needed.
     * @param playlistName the name of the saved playlist
     * @param audio the saved audio content
     */
    public void restorePlaylistAudio(String playlistName, UserContent audio) {
        Playlist playlist = findPlaylist(playlistName);

        if (playlist == null) {
            playlist = new Playlist(playlistName);
            playlists.add(playlist);
        }

        playlist.addAudio(audio);
    }

    /**
     * Adds a video to the watched list and removes it from the watchlist.
     * @param video the video that was played
     */
    public void recordWatchedVideo(VideoContent video) {
        if (addVideoToList(previouslyWatchedVideos, new UserContent(video))) {
            System.out.println("Content added to previously watched list.");
        }

        for (int i = 0; i < watchList.size(); i++) {
            if (watchList.get(i).getId().equals(video.getId())) {
                watchList.remove(i);
                break;
            }
        }
    }

    /**
     * Adds a video to the watchlist if its ID is not already present.
     * @param video the video to add
     */
    public void addToWatchlist(VideoContent video) {
        if (addVideoToList(watchList, new UserContent(video))) {
            System.out.println("Content added to watchlist.");
        } else {
            System.out.println("Content is already in watchlist.");
        }
    }

    /**
     * Reads a playlist name and adds the audio to that playlist.
     * @param keyboard the scanner used to read the playlist name
     * @param audio the audio content to add
     */
    public void addToPlaylist(Scanner keyboard, AudioContent audio) {
        String playlistName = readPlaylistName(keyboard);
        Playlist playlist = findPlaylist(playlistName);

        if (playlist == null) {
            playlist = new Playlist(playlistName);
            playlists.add(playlist);
            System.out.println("Playlist created.");
        }

        if (playlist.addAudio(audio)) {
            System.out.println("Audio content added to playlist.");
        } else {
            System.out.println("Audio content is already in playlist.");
        }
    }

    // Reads a playlist name and asks again if the name is empty.
    private String readPlaylistName(Scanner keyboard) {
        String playlistName;

        do {
            System.out.println("Enter playlist name:");
            playlistName = keyboard.nextLine().trim();

            if (playlistName.length() == 0) {
                System.out.println("Playlist name cannot be empty.");
            }
        } while (playlistName.length() == 0);

        return playlistName;
    }

    /**
     * Writes the current user lists to a CSV session file.
     * @param fileName the path of the session file
     * @throws IOException if the file cannot be opened, written or closed
     */
    public void saveSessionFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        try {
            writeSessionLine(writer, Constants.SESSION_DATA_HEADER);

            for (int i = 0; i < previouslyWatchedVideos.size(); i++) {
                writeSessionLine(writer, formatSessionLine(previouslyWatchedVideos.get(i),
                        "PREVIOUSLY_WATCHED", ""));
            }

            for (int i = 0; i < watchList.size(); i++) {
                writeSessionLine(writer,
                        formatSessionLine(watchList.get(i), "WATCHLIST", ""));
            }

            for (int i = 0; i < playlists.size(); i++) {
                Playlist playlist = playlists.get(i);

                for (int j = 0; j < playlist.getNumberOfItems(); j++) {
                    writeSessionLine(writer,
                            formatSessionLine(playlist.getItem(j),
                                    "PLAYLIST", playlist.getName()));
                }
            }

            writer.flush();
        } finally {
            writer.close();
        }
    }

    // Writes one line followed by a line break.
    private void writeSessionLine(BufferedWriter writer, String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

    // Formats one content item as a session file row.
    private String formatSessionLine(UserContent content, String listType,
                                     String playlistName) {
        return content.getContentType().name() + "," + listType + "," + playlistName + ","
                + content.getId() + "," + content.getName() + "," + content.getGenre();
    }

    // Finds a playlist by name without checking letter case.
    private Playlist findPlaylist(String playlistName) {
        for (int i = 0; i < playlists.size(); i++) {
            Playlist playlist = playlists.get(i);

            if (playlist.getName().equalsIgnoreCase(playlistName)) {
                return playlist;
            }
        }

        return null;
    }

    // Adds a movie or TV series if the list does not contain its ID.
    private boolean addVideoToList(ArrayList<UserContent> videos,
                                   UserContent video) {
        if (video.getContentType() != ContentType.MOVIE
                && video.getContentType() != ContentType.TV_SERIES) {
            return false;
        }

        if (containsVideo(videos, video.getId())) {
            return false;
        }

        videos.add(video);
        return true;
    }

    // Checks whether a video ID is already in a list.
    private boolean containsVideo(ArrayList<UserContent> videos, String id) {
        for (int i = 0; i < videos.size(); i++) {
            if (videos.get(i).getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Shows the user lists menu until the user chooses to return.
     * @param keyboard the scanner used to read menu choices
     */
    public void runUserMenu(Scanner keyboard) {
        boolean loopRunning = true;

        while (loopRunning) {
            System.out.println(Constants.USER_MENU);
            String choice = keyboard.nextLine().trim();

            switch (choice) {
                case Constants.VIEW_PREVIOUSLY_WATCHED_MOVIES:
                    viewPreviouslyWatchedMovies();
                    break;
                case Constants.VIEW_WATCHLIST:
                    viewWatchList();
                    break;
                case Constants.VIEW_PLAYLISTS:
                    viewPlayLists(keyboard);
                    break;
                case Constants.EXIT_USER_MENU:
                    loopRunning = false;
                    break;
                default:
                    System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    // Prints the watched videos, or a message if the list is empty.
    private void viewPreviouslyWatchedMovies() {
        if (previouslyWatchedVideos.isEmpty()) {
            System.out.println(Constants.NO_PREVIOUSLY_WATCHED_CONTENT);
            return;
        }

        System.out.println(Constants.PREVIOUSLY_WATCHED_CONTENT_HEADER);

        for (int i = 0; i < previouslyWatchedVideos.size(); i++) {
            System.out.println((i + 1) + ". "
                    + formatContentLine(previouslyWatchedVideos.get(i)));
        }
    }

    // Prints the watchlist, or a message if the list is empty.
    private void viewWatchList() {
        if (watchList.isEmpty()) {
            System.out.println(Constants.NO_CONTENT_IN_WATCHLIST);
            return;
        }

        System.out.println(Constants.WATCHLIST_HEADER);

        for (int i = 0; i < watchList.size(); i++) {
            System.out.println((i + 1) + ". " + formatContentLine(watchList.get(i)));
        }
    }

    // Shows playlist names and prints the contents of the chosen playlist.
    private void viewPlayLists(Scanner keyboard) {
        if (playlists.isEmpty()) {
            System.out.println("No playlists created.");
            return;
        }

        System.out.println("Playlists:");

        for (int i = 0; i < playlists.size(); i++) {
            System.out.println(playlists.get(i).getName());
        }

        Playlist playlist = findPlaylist(readPlaylistName(keyboard));

        if (playlist == null) {
            System.out.println("Playlist not found.");
            return;
        }

        for (int i = 0; i < playlist.getNumberOfItems(); i++) {
            System.out.println((i + 1) + ". " + formatContentLine(playlist.getItem(i)));
        }
    }

    // Formats a content item for display in a user list.
    private String formatContentLine(UserContent content) {
        return content.getContentType().name().replace('_', ' ') + ": "
                + content.getName() + " [" + content.getGenre() + "]";
    }
}

