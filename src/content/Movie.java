/**
Student Name - Yiqun Qiao
*/
package content;

import enums.ContentType;
import enums.VideoGenre;

/** Stores movie details. */
public class Movie extends VideoContent {
    private final int totalTime;

    /**
     * Creates a movie with its video details and total time.
     * @param id the content ID
     * @param name the content name
     * @param year the release year
     * @param reviewRating the review rating
     * @param description a short description
     * @param genre the video genre
     * @param cast the cast names
     * @param totalTime the total time in minutes
     */
    public Movie(String id, String name, int year, double reviewRating,
                 String description, VideoGenre genre, String[] cast,
                 int totalTime) {
        super(id, name, year, reviewRating, description, genre, cast);
        this.totalTime = totalTime;
    }

    /**
     * Returns the total time in minutes.
     * @return the total time in minutes
     */
    public int getTotalTime() {
        return totalTime;
    }

    public ContentType getContentType() {
        return ContentType.MOVIE;
    }

    public void displayDetails() {
        System.out.println("Movie Details:");
        displayVideoDetails();
        System.out.println("Total Time (mins): " + totalTime);
    }
}

