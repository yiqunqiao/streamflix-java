/**
Student Name - Yiqun Qiao
*/
package utils;

import java.util.Scanner;
import enums.VideoGenre;
import enums.SongGenre;
import enums.PodcastGenre;
/** Stores shared messages and settings. */
public class Constants{

    /** The shared scanner for keyboard input. */
    public static final Scanner SCANNER = new Scanner(System.in);

    /** The welcome message with a space for the user name. */
    public final static String WELCOME_MESSAGE = "Welcome %s to StreamFlix%n";
    /** The message shown when the app closes. */
    public final static String GOODBYE_MESSAGE = "Thank you for watching StreamFlix. Come back again.";

    /** The message for an invalid menu choice. */
    public final static String INVALID_INPUT="Invalid input.";
    /** The message for an incorrect number of command-line arguments. */
    public static final String INVALID_COMMAND_LINE_ARGUMENTS = "Invalid number of command-line arguments. Please run the app using: java StreamingApp <userName> <numberOfGenres> <contentFileName> <tvSeriesFileName>";
    /** The message for an empty user name. */
    public static final String INVALID_USER_NAME = "Invalid user name. User name cannot be empty.";
    /** The message for an invalid genre count. */
    public static final String INVALID_NUMBER_OF_GENRES = "Invalid number of genres.";
    /** The message for an empty content file path. */
    public static final String INVALID_CONTENT_FILE_NAME = "Invalid content file name. Content File Name cannot be empty.";
    /** The message for an empty TV series file path. */
    public static final String INVALID_TV_SERIES_FILE_NAME = "Invalid TV Series file name. TV Series File Name cannot be empty.";
    /** The message for a file that cannot be opened, read, or written. */
    public static final String FILE_NOT_FOUND = "File Not Found. Terminating Program.";
    /** The highest genre count among the three genre types. */
    public static final int MAX_NUMBER_OF_GENRES = getMaximumNumberOfGenres();
    /** The message shown when the cursor reaches a canvas edge. */
    public static final String CANNOT_MOVE_THERE = "You can't move there.";
    /** The default starting size for arrays. */
    public final static int INITIAL_ARR_CAPACITY = 2;

    /** The main menu text. */
    public final static String MAIN_MENU= "Select an option from below:\n" +
            "1. View videos\n" +
            "2. View audios\n" +
            "3. View games\n" +
            "4. View user settings\n" +
            "5. Exit StreamFlix";

    /** The menu for movies and TV series. */
    public final static String VIDEOS_MENU= "Select an option from below:\n" +
            "1. Watch movies\n" +
            "2. Watch tv series\n" +
            "3. Return to main menu";

    /** The menu for songs and podcasts. */
    public final static String AUDIOS_MENU= "Select an option from below:\n" +
            "1. Listen to songs\n" +
            "2. Listen to podcasts\n" +
            "3. Return to main menu";
    
    /** The menu for viewing the user lists. */
    public final static String USER_MENU = "=========User Menu==========\nSelect an option from below:\n" +
                                            "1. View previously watched content\n" +
                                            "2. View videos watchlist\n" +
                                            "3. View audio playlists\n" +
                                            "4. Exit user menu";

    /** The navigation menu with spaces for the content type name. */
    public final static String NAVIGATION_MENU = "=========Navigation Menu==========%nUse the options below to navigate:%n" +
                                            "U to move up%n" +
                                            "D to move down%n" +
                                            "L to move left%n" +
                                            "R to move right%n" +
                                            "X to select %s%n" +
                                            "Q to exit %s canvas%n";

    /** The actions menu for video and audio content. */
    public final static String CONTENT_ACTIONS_MENU = "=========%s Actions==========%nSelect an option below:%n" +
                                            "V to View %s details%n" +
                                            "P to Play %s%n" +
                                            "W to Add to watchlist/playlist%n" +
                                            "Q to Exit%n";

     /** The actions menu for games. */
     public final static String GAME_ACTIONS_MENU = "=========GAME Actions==========%nSelect an option below:%n" +
                                            "V to View Game details%n" +
                                            "P to Play Game%n" +
                                            "Q to Exit%n";
                                            
 
    /** The heading for previously watched content. */
    public final static String PREVIOUSLY_WATCHED_CONTENT_HEADER = "Previously Watched Content:";
    /** The heading for the video watchlist. */
    public final static String WATCHLIST_HEADER = "Watchlist:";
    /** The message for an empty viewing history. */
    public final static String NO_PREVIOUSLY_WATCHED_CONTENT = "No previously watched content.";
    /** The message for an empty watchlist. */
    public final static String NO_CONTENT_IN_WATCHLIST = "No content in watchlist.";


    /** The main menu option for video content. */
    public final static String VIDEO_CANVAS= "1";
    /** The main menu option for audio content. */
    public final static String AUDIO_CANVAS= "2";
    /** The main menu option for games. */
    public final static String GAMES_CANVAS= "3";
    /** The main menu option for user settings. */
    public final static String USER_SETTINGS = "4";
    /** The main menu option to close the app. */
    public final static String EXIT_PROGRAM = "5";

    /** The user menu option to view watched movies and TV series. */
    public final static String VIEW_PREVIOUSLY_WATCHED_MOVIES = "1";
    /** The user menu option to view the watchlist. */
    public final static String VIEW_WATCHLIST = "2";
    /** The user menu option to view audio playlists. */
    public final static String VIEW_PLAYLISTS = "3";
    /** The option to leave the user menu. */
    public final static String EXIT_USER_MENU = "4";

    /** The video menu option to browse movies. */
    public final static String WATCH_MOVIE = "1";
    /** The video menu option to browse TV series. */
    public final static String WATCH_TV_SHOW = "2";
    /** The option to leave the video menu. */
    public final static String VIDEO_MENU_EXIT = "3";

    /** The audio menu option to browse songs. */
    public final static String LISTEN_SONGS = "1";
    /** The audio menu option to browse podcasts. */
    public final static String LISTEN_PODCAST = "2";
    /** The option to leave the audio menu. */
    public final static String AUDIO_MENU_EXIT = "3";

    /** The command to move the cursor up. */
    public final static String MOVE_UP = "U";
    /** The command to move the cursor down. */
    public final static String MOVE_DOWN = "D";
    /** The command to move the cursor left. */
    public final static String MOVE_LEFT = "L";
    /** The command to move the cursor right. */
    public final static String MOVE_RIGHT = "R";
    /** The command to select the current cell. */
    public final static String SELECT_CONTENT = "X";
    /** The command to leave the current canvas. */
    public final static String EXIT_NAVIGATION = "Q";


    /** The command to show content details. */
    public final static String VIEW_CONTENT_DETAILS = "V";
    /** The command to play the selected content. */
    public final static String PLAY_CONTENT = "P";
    /** The command to add video or audio content to a user list. */
    public final static String ADD_TO_WATCHLIST = "W";
    /** The command to leave the actions menu. */
    public final static String EXIT_CONTENT_ACTIONS = "Q";

    /** The line shown above and below a canvas. */
    public final static String CONTENT_CANVAS_HEADER = "================================================================================";
    
    /** The width used to display each content name. */
    public final static int CONTENT_NAME_DISPLAY_LENGTH = 10;
    /** The text added when a content name is shortened. */
    public final static String ELLIPSIS = "...";
    /** The format used for a left-aligned genre label. */
    public final static String GENRE_LABEL_WIDTH = "%-10s";
    /** The character placed between canvas cells. */
    public final static String CELL_SEPARATOR = "|";


    /** The header row written to the user session file. */
    public static final String SESSION_DATA_HEADER = "content_type,list_type,playlist_name,content_id,name,genre";
    // Finds the highest genre count among the three genre types.
    private static int getMaximumNumberOfGenres() {
        int maximum = VideoGenre.values().length;

        if (SongGenre.values().length > maximum) {
            maximum = SongGenre.values().length;
        }

        if (PodcastGenre.values().length > maximum) {
            maximum = PodcastGenre.values().length;
        }

        return maximum;
    }   
}


