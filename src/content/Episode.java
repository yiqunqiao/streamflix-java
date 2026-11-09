/**
Student Name - Yiqun Qiao
*/
package content;

/** Stores episode details. */
public final class Episode {
    private final String name;
    private final String description;
    private final int totalTime;

    /**
     * Creates a TV episode.
     * @param name the episode name
     * @param description a short description
     * @param totalTime the total time in minutes
     */
    public Episode(String name, String description, int totalTime) {
        this.name = name;
        this.description = description;
        this.totalTime = totalTime;
    }

    /**
     * Returns the episode name.
     * @return the episode name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the episode description.
     * @return the episode description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the total time in minutes.
     * @return the total time in minutes
     */
    public int getTotalTime() {
        return totalTime;
    }
}

