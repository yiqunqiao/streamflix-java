/**
Student Name - Yiqun Qiao
*/
package content;

import enums.ContentType;

/** Stores game details. */
public class Game extends Content {
    private final String developer;
    private final int minPlayers;
    private final int maxPlayers;

    /**
     * Creates a game with its player limits.
     * @param id the content ID
     * @param name the content name
     * @param year the release year
     * @param reviewRating the review rating
     * @param description a short description
     * @param developer the game developer
     * @param minPlayers the minimum number of players
     * @param maxPlayers the maximum number of players
     */
    public Game(String id, String name, int year, double reviewRating,
                String description, String developer, int minPlayers,
                int maxPlayers) {
        super(id, name, year, reviewRating, description);
        this.developer = developer;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Returns the game developer.
     * @return the game developer
     */
    public String getDeveloper() {
        return developer;
    }

    /**
     * Returns the minimum number of players.
     * @return the minimum number of players
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Returns the maximum number of players.
     * @return the maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    public ContentType getContentType() {
        return ContentType.GAME;
    }

    public void displayDetails() {
        System.out.println("Game Details:");
        displayCommonDetails();
        System.out.println("Developer: " + developer);
        System.out.println("Players: " + minPlayers + " - " + maxPlayers);
    }
}

