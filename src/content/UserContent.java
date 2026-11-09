/**
Student Name - Yiqun Qiao
*/
package content;

import enums.ContentType;

/** Stores content details used in user lists. */
public class UserContent {
    private final ContentType contentType;
    private final String id;
    private final String name;
    private final String genre;

    /**
     * Creates a user list item from its basic details.
     * @param contentType the content type
     * @param id the content ID
     * @param name the content name
     * @param genre the genre name
     */
    public UserContent(ContentType contentType, String id, String name, String genre) {
        this.contentType = contentType;
        this.id = id;
        this.name = name;
        this.genre = genre;
    }

    /**
     * Copies the basic details of a movie or TV series.
     * @param video the video content to use
     */
    public UserContent(VideoContent video) {
        this(video.getContentType(), video.getId(), video.getName(),
                video.getGenre().name());
    }

    /**
     * Copies the basic details of a song or podcast.
     * @param audio the song or podcast to use
     */
    public UserContent(AudioContent audio) {
        this(audio.getContentType(), audio.getId(), audio.getName(),
                getAudioGenreName(audio));
    }

    /**
     * Returns the content type.
     * @return the content type
     */
    public ContentType getContentType() {
        return contentType;
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
     * Returns the genre name.
     * @return the genre name
     */
    public String getGenre() {
        return genre;
    }

    private static String getAudioGenreName(AudioContent audio) {
        if (audio instanceof Song) {
            return ((Song) audio).getGenre().name();
        }

        return ((Podcast) audio).getGenre().name();
    }
}

