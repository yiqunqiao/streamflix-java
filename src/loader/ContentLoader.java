/**
Student Name - Yiqun Qiao
*/
package loader;

import content.Content;
import content.Game;
import content.Movie;
import content.Podcast;
import content.Song;
import content.TVSeries;
import enums.ContentType;
import enums.PodcastGenre;
import enums.SongGenre;
import enums.VideoGenre;
import exceptions.InvalidFormatException;
import exceptions.InvalidLineException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** Loads content from a CSV file. */
public class ContentLoader {
    private static final int MIN_CONTENT_FIELD_COUNT = 9;
    private static final int MIN_RELEASE_YEAR = 1970;

    /** Creates a content file loader. */
    public ContentLoader() {
    }
    /**
     * Loads valid content rows after the header.
     * @param fileName the path to the content file
     * @return the content loaded from valid rows
     * @throws IOException if the file cannot be opened, read, or closed
     */
    public ArrayList<Content> loadContentFile(String fileName) throws IOException {
        ArrayList<Content> catalogue = new ArrayList<Content>();
        BufferedReader input = new BufferedReader(new FileReader(fileName));
        try {
            // Skip the CSV header.
            input.readLine();
            String line;
            while ((line = input.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                try {
                    Content content = parseContentLine(line);
                    catalogue.add(content);
                } catch (InvalidLineException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        } finally {
            input.close();
        }
        return catalogue;
    }

    // Checks one row in field order and creates its content.
    private Content parseContentLine(String line)
            throws InvalidLineException, InvalidFormatException {
        // Keep empty fields at the end so they can be checked.
        String[] fields = line.split(",", -1);
        if (fields.length < MIN_CONTENT_FIELD_COUNT) {
            throw new InvalidLineException("Invalid Content Line. Skipping this line.");
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }
        ContentType type = readContentType(fields[0]);
        if (fields[1].length() == 0 || fields[2].length() == 0) {
            throw new InvalidLineException("Either id or name is empty. Skipping this line.");
        }
        int year = readInteger(fields[3], "Invalid year format. Skipping this line.");
        if (year < MIN_RELEASE_YEAR) {
            throw new InvalidLineException("Year cannot be less than 1970. Skipping this line.");
        }
        double rating = readRating(fields[4]);
        if (rating < 0) {
            throw new InvalidLineException("Review Rating cannot be less than 0. Skipping this line.");
        }
        int totalTime = readInteger(fields[5], "Invalid time format. Skipping this line.");
        if (totalTime < 0) {
            throw new InvalidLineException("TotalTime cannot be less than 0 mins. Skipping this line.");
        }
        if (fields[6].length() == 0 || fields[7].length() == 0) {
            throw new InvalidLineException("Either creators or desc are empty Skipping this line.");
        }

        return createContent(type, fields, year, rating, totalTime);
    }

    // Reads a content type from its enum name.
    private ContentType readContentType(String value) throws InvalidFormatException {
        try {
            return ContentType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException("Incorrect content type. Skipping this line.");
        }
    }

    // Reads an integer and uses the given message for a format error.
    private int readInteger(String value, String errorMessage) throws InvalidFormatException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException(errorMessage);
        }
    }
    
    // Reads a review rating from text.
    private double readRating(String value) throws InvalidFormatException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Invalid review Rating format. Skipping this line.");
        }
    }
    // Creates content of the given type from the checked common fields.
    private Content createContent(ContentType type, String[] fields, int year,
                                  double rating, int totalTime)
            throws InvalidLineException, InvalidFormatException {
        switch (type) {
            case MOVIE:
                return new Movie(fields[1], fields[2], year, rating, fields[7],
                        readVideoGenre(fields[8]), splitCreators(fields[6]), totalTime);
            case TV_SERIES:
                return new TVSeries(fields[1], fields[2], year, rating, fields[7],
                        readVideoGenre(fields[8]), splitCreators(fields[6]));
            case SONGS:
                return new Song(fields[1], fields[2], year, rating, fields[7],
                        readSongGenre(fields[8]), splitCreators(fields[6]), totalTime);
            case PODCAST:
                return new Podcast(fields[1], fields[2], year, rating, fields[7],
                        readPodcastGenre(fields[8]), splitCreators(fields[6]), totalTime);
            case GAME:
                return createGame(fields, year, rating);
            default:
                throw new InvalidFormatException("Incorrect content type. Skipping this line.");
        }
    }

    // Reads the player limits and creates a game.
    private Game createGame(String[] fields, int year, double rating)
            throws InvalidLineException, InvalidFormatException {
        String formatMessage = "Invalid min players or max players format. Skipping this line.";
        int minPlayers = readInteger(fields[9], formatMessage);
        int maxPlayers = readInteger(fields[10], formatMessage);
        if (minPlayers < 1 || maxPlayers < 1) {
            throw new InvalidLineException(
                    "Min or Max Players cannot be less than 1. Skipping this line.");
        }
        if (maxPlayers < minPlayers) {
            throw new InvalidLineException(
                    "Max players cannot be less than min players. Skipping this line.");
        }
        return new Game(fields[1], fields[2], year, rating, fields[7], fields[6],
                minPlayers, maxPlayers);
    }

    // Reads a video genre from its enum name.
    private VideoGenre readVideoGenre(String value) throws InvalidFormatException {
        try {
            return VideoGenre.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException("Incorrect video genre. Skipping this line.");
        }
    }
    // Reads a song genre from its enum name.
    private SongGenre readSongGenre(String value) throws InvalidFormatException {
        try {
            return SongGenre.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException("Incorrect song genre. Skipping this line.");
        }
    }

    // Reads a podcast genre from its enum name.
    private PodcastGenre readPodcastGenre(String value) throws InvalidFormatException {
        try {
            return PodcastGenre.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException("Incorrect podcast genre. Skipping this line.");
        }
    }
    // Splits creator names at each hash sign and trims each name.
    private String[] splitCreators(String value) {
        String[] creators = value.split("#", -1);
        for (int i = 0; i < creators.length; i++) {
            creators[i] = creators[i].trim();
        }
        return creators;
    }

}

