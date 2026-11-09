/**
Student Name - Yiqun Qiao
*/
package content;

import enums.VideoGenre;

/** Stores the details shared by movies and TV series. */
public abstract class VideoContent extends Content {
    private final VideoGenre genre;
    private final String[] cast;

    /**
     * Creates video content and copies the cast names.
     * @param id the content ID
     * @param name the content name
     * @param year the release year
     * @param reviewRating the review rating
     * @param description a short description
     * @param genre the video genre
     * @param cast the cast names
     */
    protected VideoContent(String id, String name, int year, double reviewRating,
                           String description, VideoGenre genre, String[] cast) {
        super(id, name, year, reviewRating, description);
        this.genre = genre;
        this.cast = new String[cast.length];

        for (int i = 0; i < cast.length; i++) {
            this.cast[i] = cast[i];
        }
    }

    /**
     * Returns the video genre.
     * @return the video genre
     */
    public VideoGenre getGenre() {
        return genre;
    }

    /**
     * Returns a copy of the cast names.
     * @return a new array of cast names
     */
    public String[] getCast() {
        String[] copy = new String[cast.length];

        for (int i = 0; i < cast.length; i++) {
            copy[i] = cast[i];
        }

        return copy;
    }

    /** Prints the shared content details, genre and cast. */
    protected void displayVideoDetails() {
        displayCommonDetails();
        System.out.println("Genre: " + genre);
        System.out.println("Cast: " + formatNames(cast));
    }
}

