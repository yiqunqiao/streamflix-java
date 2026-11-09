/**
Student Name - Yiqun Qiao
*/
package content;

import enums.ContentType;
import enums.PodcastGenre;

/** Stores podcast details. */
public class Podcast extends AudioContent {
    private final PodcastGenre genre;

    /**
     * Creates a podcast with its genre and creator names.
     * @param id the content ID
     * @param name the content name
     * @param year the release year
     * @param reviewRating the review rating
     * @param description a short description
     * @param genre the podcast genre
     * @param creators the creator names
     * @param totalTime the total time in minutes
     */
    public Podcast(String id, String name, int year, double reviewRating,
                   String description, PodcastGenre genre, String[] creators,
                   int totalTime) {
        super(id, name, year, reviewRating, description, creators, totalTime);
        this.genre = genre;
    }

    /**
     * Returns the podcast genre.
     * @return the podcast genre
     */
    public PodcastGenre getGenre() {
        return genre;
    }

    /**
     * Returns a copy of the creator names.
     * @return a new array of creator names
     */
    public String[] getCreators() {
        return getContributors();
    }

    public ContentType getContentType() {
        return ContentType.PODCAST;
    }

    public void displayDetails() {
        System.out.println("Podcast Details:");
        displayCommonDetails();
        System.out.println("Genre: " + genre);
        System.out.println("Creators: " + formatNames(getCreators()));
        System.out.println("Total Time (mins): " + getTotalTime());
    }
}

