/**
Student Name - Yiqun Qiao
*/
package loader;

import canvas.User;
import content.UserContent;
import enums.ContentType;
import enums.PodcastGenre;
import enums.SongGenre;
import enums.VideoGenre;
import exceptions.InvalidFormatException;
import exceptions.InvalidLineException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/** Loads saved user lists. */
public class UserSessionLoader {
    private static final int SESSION_FIELD_COUNT = 6;

    /**
     * Loads saved rows after the header into the user's lists.
     * @param fileName the path to the saved session file
     * @param user the user whose lists are restored
     * @return true if the file exists and is read; false if it does not exist
     * @throws IOException if an existing file cannot be opened, read, or closed
     */
    public boolean loadSessionFile(String fileName, User user) throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            return false;
        }

        BufferedReader input = new BufferedReader(new FileReader(file));

        try {
            // Skip the CSV header.
            input.readLine();
            String line;

            while ((line = input.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }

                try {
                    parseSessionLine(line, user);
                } catch (InvalidLineException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        } finally {
            input.close();
        }

        return true;
    }

    // Checks one saved row and restores its content to a user list.
    private void parseSessionLine(String line, User user)
            throws InvalidLineException, InvalidFormatException {
        // Keep empty fields at the end so they can be checked.
        String[] fields = line.split(",", -1);

        if (fields.length < SESSION_FIELD_COUNT) {
            throw new InvalidLineException("Invalid user session line. Skipping this line.");
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }

        ContentType type = readContentType(fields[0]);

        if (fields[3].length() == 0 || fields[4].length() == 0) {
            throw new InvalidLineException("Content id or name is empty. Skipping this line.");
        }

        validateGenre(type, fields[5]);

        UserContent content = new UserContent(
                type, fields[3], fields[4], fields[5]);

        restoreContent(fields[1], fields[2], content, user);
    }

    // Reads a saved content type and rejects games.
    private ContentType readContentType(String value) throws InvalidFormatException {
        ContentType type;

        try {
            type = ContentType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException("Incorrect content type. Skipping this line.");
        }

        if (type == ContentType.GAME) {
            throw new InvalidFormatException("Incorrect content type. Skipping this line.");
        }

        return type;
    }

    // Checks the genre against the enum for the content type.
    private void validateGenre(ContentType type, String genre)
            throws InvalidFormatException {
        if (type == ContentType.MOVIE || type == ContentType.TV_SERIES) {
            try {
                VideoGenre.valueOf(genre);
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatException("Incorrect video genre. Skipping this line.");
            }
        } else if (type == ContentType.SONGS) {
            try {
                SongGenre.valueOf(genre);
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatException("Incorrect song genre. Skipping this line.");
            }
        } else if (type == ContentType.PODCAST) {
            try {
                PodcastGenre.valueOf(genre);
            } catch (IllegalArgumentException e) {
                throw new InvalidFormatException("Incorrect podcast genre. Skipping this line.");
            }
        }
    }

    // Sends saved content to the named user list.
    private void restoreContent(String listType, String playlistName,
            UserContent content, User user) throws InvalidLineException {
        switch (listType) {
            case "PREVIOUSLY_WATCHED":
                user.restorePreviouslyWatchedVideo(content);
                break;
            case "WATCHLIST":
                user.restoreWatchListVideo(content);
                break;
            case "PLAYLIST":
                user.restorePlaylistAudio(playlistName, content);
                break;
            default:
                throw new InvalidLineException("Invalid list type. Skipping this line.");
        }
    }
}

