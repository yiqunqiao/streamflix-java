/**
Student Name - Yiqun Qiao
*/
package content;

import enums.ContentType;
import enums.SongGenre;

/** Stores song details. */
public class Song extends AudioContent {
    private final SongGenre genre;

    /**
     * Creates a song with its genre and artist names.
     * @param id the content ID
     * @param name the content name
     * @param year the release year
     * @param reviewRating the review rating
     * @param description a short description
     * @param genre the song genre
     * @param artists the artist names
     * @param totalTime the total time in minutes
     */
    public Song(String id, String name, int year, double reviewRating,
                String description, SongGenre genre, String[] artists,
                int totalTime) {
        super(id, name, year, reviewRating, description, artists, totalTime);
        this.genre = genre;
    }

    /**
     * Returns the song genre.
     * @return the song genre
     */
    public SongGenre getGenre() {
        return genre;
    }

    /**
     * Returns a copy of the artist names.
     * @return a new array of artist names
     */
    public String[] getArtists() {
        return getContributors();
    }

    public ContentType getContentType() {
        return ContentType.SONGS;
    }

    public void displayDetails() {
        System.out.println("Song Details:");
        displayCommonDetails();
        System.out.println("Genre: " + genre);
        System.out.println("Artists: " + formatNames(getArtists()));
        System.out.println("Total Time (mins): " + getTotalTime());
    }
}

