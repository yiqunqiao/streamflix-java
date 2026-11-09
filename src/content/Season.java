/**
Student Name - Yiqun Qiao
*/
package content;

import java.util.ArrayList;

/** Stores a season and its episodes. */
public final class Season {
    private final int seasonNumber;
    private final ArrayList<Episode> episodes;

    /**
     * Creates an empty season.
     * @param seasonNumber the season number
     */
    public Season(int seasonNumber) {
        this.seasonNumber = seasonNumber;
        episodes = new ArrayList<Episode>();
    }

    /**
     * Copies a season and its episode list.
     * @param other the season to copy
     */
    public Season(Season other) {
        this(other.seasonNumber);

        for (int i = 0; i < other.episodes.size(); i++) {
            episodes.add(other.episodes.get(i));
        }
    }

    /**
     * Returns the season number.
     * @return the season number
     */
    public int getSeasonNumber() {
        return seasonNumber;
    }

    /**
     * Returns the number of episodes.
     * @return the number of episodes in this season
     */
    public int getNumberOfEpisodes() {
        return episodes.size();
    }

    /**
     * Adds an episode to the end of the season.
     * @param episode the episode to add
     */
    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }

    /**
     * Returns an episode by its position, starting at one.
     * @param episodeNumber the episode position, starting at one
     * @return the stored episode, or null if the position is invalid
     */
    public Episode getEpisode(int episodeNumber) {
        if (episodeNumber < 1 || episodeNumber > episodes.size()) {
            return null;
        }

        return episodes.get(episodeNumber - 1);
    }

    /**
     * Returns a new list containing the stored episode objects.
     * @return a copy of the episode list
     */
    public ArrayList<Episode> getEpisodes() {
        ArrayList<Episode> copy = new ArrayList<Episode>();

        for (int i = 0; i < episodes.size(); i++) {
            copy.add(episodes.get(i));
        }

        return copy;
    }
}

