/**
Student Name - Yiqun Qiao
*/
package content;

import enums.ContentType;
import interfaces.Viewable;

/** Stores the details shared by all content. */
public abstract class Content implements Viewable {
    private final String id;
    private final String name;
    private final int year;
    private final double reviewRating;
    private final String description;

    /**
     * Creates content with its shared details.
     * @param id the content ID
     * @param name the content name
     * @param year the release year
     * @param reviewRating the review rating
     * @param description a short description
     */
    protected Content(String id, String name, int year, double reviewRating,
                      String description) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.reviewRating = reviewRating;
        this.description = description;
    }

    /**
     * Returns the content ID.
     * @return the content ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the content name.
     * @return the content name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the release year.
     * @return the release year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the review rating.
     * @return the review rating
     */
    public double getReviewRating() {
        return reviewRating;
    }

    /**
     * Returns the description.
     * @return the content description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the type of this content.
     * @return the content type
     */
    public abstract ContentType getContentType();

    public abstract void displayDetails();

    /** Prints the details shared by all content. */
    protected void displayCommonDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Year: " + year);
        System.out.println("Description: " + description);
        System.out.println("Review Rating: " + reviewRating);
    }

    /**
     * Formats the names.
     * @param names the names to join
     * @return the formatted names
     */
    protected String formatNames(String[] names) {
        String result = "";
        for (int i = 0; i < names.length; i++) {
            if (i > 0) {
                result += ", ";
            }
            result += names[i];
        }
        return result;
    }
}

