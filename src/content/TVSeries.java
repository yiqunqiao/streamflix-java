/**
Student Name - Yiqun Qiao
*/
package content;

import enums.ContentType;
import enums.VideoGenre;
import java.util.ArrayList;

/** Stores a TV series and its seasons. */
public class TVSeries extends VideoContent {
    private final ArrayList<Season> seasons;

    /**
     * Creates a TV series with no seasons.
     * @param id the content ID
     * @param name the content name
     * @param year the release year
     * @param reviewRating the review rating
     * @param description a short description
     * @param genre the video genre
     * @param cast the cast names
     */
    public TVSeries(String id, String name, int year, double reviewRating,
                    String description, VideoGenre genre, String[] cast) {
        super(id, name, year, reviewRating, description, genre, cast);
        seasons = new ArrayList<Season>();
    }

    /**
     * Copies a TV series and its seasons.
     * @param other the TV series to copy
     */
    public TVSeries(TVSeries other) {
        this(other.getId(), other.getName(), other.getYear(),
                other.getReviewRating(), other.getDescription(),
                other.getGenre(), other.getCast());

        for (int i = 0; i < other.seasons.size(); i++) {
            seasons.add(new Season(other.seasons.get(i)));
        }
    }

    /**
     * Returns the number of seasons.
     * @return the number of seasons in this series
     */
    public int getNumberOfSeasons() {
        return seasons.size();
    }
    /**
     * Adds an episode, creating the season if it does not exist.
     * @param seasonNumber the season number
     * @param episode the episode to add
     */
    public void addEpisode(int seasonNumber, Episode episode) {
        Season season = findSeason(seasonNumber);

        if (season == null) {
            season = new Season(seasonNumber);
            seasons.add(season);
        }

        season.addEpisode(episode);
    }

    /**
     * Creates and adds an episode to the given season.
     * @param seasonNumber the season number
     * @param name the episode name
     * @param description a short description
     * @param totalTime the total time in minutes
     */
    public void addEpisode(int seasonNumber, String name, String description,
                           int totalTime) {
        addEpisode(seasonNumber, new Episode(name, description, totalTime));
    }
    /**
     * Returns a copy of a season.
     * @param seasonNumber the season number to find
     * @return a season copy, or null if not found
     */
    public Season getSeason(int seasonNumber) {
        Season season = findSeason(seasonNumber);

        if (season == null) {
            return null;
        }

        return new Season(season);
    }

    /**
     * Returns a new list of season copies.
     * @return a list of season copies
     */
    public ArrayList<Season> getSeasons() {
        ArrayList<Season> copy = new ArrayList<Season>();

        for (int i = 0; i < seasons.size(); i++) {
            copy.add(new Season(seasons.get(i)));
        }

        return copy;
    }

    private Season findSeason(int seasonNumber) {
        for (int i = 0; i < seasons.size(); i++) {
            Season season = seasons.get(i);

            if (season.getSeasonNumber() == seasonNumber) {
                return season;
            }
        }

        return null;
    }

    public ContentType getContentType() {
        return ContentType.TV_SERIES;
    }

    public void displayDetails() {
        System.out.println("TV Series Details:");
        displayVideoDetails();
        System.out.println("Number of Seasons: " + getNumberOfSeasons());
    }
}

