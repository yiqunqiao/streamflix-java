/**
Student Name - Yiqun Qiao
*/
package content;

/** Stores the details shared by songs and podcasts. */
public abstract class AudioContent extends Content {
    private final int totalTime;
    private final String[] contributors;

    /**
     * Creates audio content and copies the contributor names.
     * @param id the content ID
     * @param name the content name
     * @param year the release year
     * @param reviewRating the review rating
     * @param description a short description
     * @param contributors the artist or creator names
     * @param totalTime the total time in minutes
     */
    protected AudioContent(String id, String name, int year, double reviewRating,
                           String description, String[] contributors,
                           int totalTime) {
        super(id, name, year, reviewRating, description);
        this.totalTime = totalTime;
        this.contributors = new String[contributors.length];

        for (int i = 0; i < contributors.length; i++) {
            this.contributors[i] = contributors[i];
        }
    }

    /**
     * Returns the total time in minutes.
     * @return the total time in minutes
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * Returns a copy of the contributor names.
     * @return a new array of the artist or creator names
     */
    protected String[] getContributors() {
        String[] copy = new String[contributors.length];

        for (int i = 0; i < contributors.length; i++) {
            copy[i] = contributors[i];
        }

        return copy;
    }
}

