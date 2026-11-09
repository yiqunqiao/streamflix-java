/**
Student Name - Yiqun Qiao
*/
package loader;

import content.Content;
import content.TVSeries;
import exceptions.InvalidFormatException;
import exceptions.InvalidLineException;
import exceptions.NotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/** Loads TV series episodes. */
public class TVSeriesLoader {
    private static final int TV_SERIES_FIELD_COUNT = 5;

    /**
     * Loads episodes after the header into the given content list.
     * @param fileName the path to the TV series episode file
     * @param catalogue the content list containing the TV series
     * @throws IOException if the file cannot be opened, read, or closed
     */
    public void loadTVSeriesFile(String fileName, ArrayList<Content> catalogue)
            throws IOException {
        // Build the ID lookup before reading episode rows.
        HashMap<String, TVSeries> seriesById = indexTVSeries(catalogue);
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
                    parseTVSeriesLine(line, seriesById);
                } catch (InvalidLineException e) {
                    System.out.println(e.getMessage());
                } catch (InvalidFormatException e) {
                    System.out.println(e.getMessage());
                } catch (NotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        } finally {
            input.close();
        }
    }

    // Builds a lookup of the TV series in the content list.
    private HashMap<String, TVSeries> indexTVSeries(ArrayList<Content> catalogue) {
        HashMap<String, TVSeries> seriesById = new HashMap<String, TVSeries>();

        for (int i = 0; i < catalogue.size(); i++) {
            Content content = catalogue.get(i);

            if (content instanceof TVSeries) {
                seriesById.put(content.getId(), (TVSeries) content);
            }
        }

        return seriesById;
    }

    // Checks one episode row, then adds it to the matching TV series.
    private void parseTVSeriesLine(String line, HashMap<String, TVSeries> seriesById)
            throws InvalidLineException, InvalidFormatException, NotFoundException {
        // Keep empty fields at the end so they can be checked.
        String[] fields = line.split(",", -1);

        if (fields.length < TV_SERIES_FIELD_COUNT) {
            throw new InvalidLineException(
                    "Invalid TV Series Episode Line. Skipping this line.");
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }

        if (fields[0].length() == 0 || fields[2].length() == 0) {
            throw new InvalidLineException(
                    "Either TV Series ID or Episode name is empty. Skipping this line.");
        }

        int seasonNumber = readInteger(fields[1],
                "Invalid season number format. Skipping this line.");

        if (seasonNumber < 1) {
            throw new InvalidLineException(
                    "Season number cannot be less than 1. Skipping this line.");
        }

        if (fields[3].length() == 0) {
            throw new InvalidLineException("Episode description is empty. Skipping this line.");
        }

        int totalTime = readInteger(fields[4], "Invalid time format. Skipping this line.");

        if (totalTime < 0) {
            throw new InvalidLineException(
                    "TotalTime cannot be less than 0 mins. Skipping this line.");
        }

        TVSeries series = seriesById.get(fields[0]);

        if (series == null) {
            throw new NotFoundException(
                    "TV Series with id " + fields[0] + " not found. Skipping this line.");
        }

        series.addEpisode(seasonNumber, fields[2], fields[3], totalTime);
    }

    // Reads an integer and uses the given message for a format error.
    private int readInteger(String value, String message) throws InvalidFormatException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException(message);
        }
    }
}

