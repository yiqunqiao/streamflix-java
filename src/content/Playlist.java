/**
Student Name - Yiqun Qiao
*/
package content;

import java.util.ArrayList;
import enums.ContentType;

/** Stores a named audio playlist. */
public class Playlist {
    private final String name;
    private final ArrayList<UserContent> items;

    /**
     * Creates an empty playlist.
     * @param name the playlist name
     */
    public Playlist(String name) {
        this.name = name;
        items = new ArrayList<UserContent>();
    }

    /**
     * Returns the playlist name.
     * @return the playlist name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of items.
     * @return the number of items in this playlist
     */
    public int getNumberOfItems() {
        return items.size();
    }

    /**
     * Returns the item at the given index.
     * @param index the item index, starting at zero
     * @return the stored item at that index
     */
    public UserContent getItem(int index) {
        return items.get(index);
    }

    /**
     * Adds the details of a song or podcast if its ID is not already listed.
     * @param audio the song or podcast to add
     * @return true if the item was added, or false if its ID is already listed
     */
    public boolean addAudio(AudioContent audio) {
        return addAudio(new UserContent(audio));
    }

    /**
     * Adds a song or podcast item if its ID is not already listed.
     * @param audio the item to add
     * @return true if added, or false for another type or an existing ID
     */
    public boolean addAudio(UserContent audio) {
        if (audio.getContentType() != ContentType.SONGS
                && audio.getContentType() != ContentType.PODCAST) {
            return false;
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(audio.getId())) {
                return false;
            }
        }

        items.add(audio);
        return true;
    }
}

