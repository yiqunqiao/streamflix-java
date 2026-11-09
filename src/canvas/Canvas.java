/**
Student Name - Yiqun Qiao
*/
package  canvas;

import java.util.Scanner;
import utils.Constants;
import content.Content;
import enums.ContentType;
import java.util.ArrayList;
import content.VideoContent;
import content.AudioContent;
import content.Song;
import content.Podcast;
import content.TVSeries;
import content.Season;
import content.Episode;
import content.Game;
/** Shows content and handles canvas menus. */
public class Canvas {

    private static final int GAME_CANVAS_SIZE = 3;
    private static final String CANVAS_TITLE_FORMAT = "%34s Lists%n";

    /** Canvas movement directions. */
    enum DIRECTION { UP, DOWN, LEFT, RIGHT}
    private Content[][] canvas;
    private int numGenre;
    private final ArrayList<Content> contentCatalogue;
    private int cursorRow;
    private int cursorCol;

    /**
     * Creates a canvas with an empty catalogue.
     * @param numGenre the maximum number of genre rows
     */
    public Canvas(int numGenre) {
        this(numGenre, new ArrayList<Content>());
    }

    /**
     * Creates a canvas from the given catalogue.
     * @param numGenre the maximum number of genre rows
     * @param catalogue the available content
     */
    public Canvas(int numGenre, ArrayList<Content> catalogue) {
        this.numGenre = numGenre;
        canvas = new Content[numGenre][];
        contentCatalogue = new ArrayList<Content>();
        for (int i = 0; i < catalogue.size(); i++) {
            Content content = catalogue.get(i);

            if (content instanceof TVSeries) {
                contentCatalogue.add(new TVSeries((TVSeries) content));
            } else {
                contentCatalogue.add(content);
            }
        }
    }

    /**
     * Shows the video menu until the user chooses to return.
     * @param keyboard the scanner used to read menu choices
     * @param user the current user
     */
    public void runVideoMenu(Scanner keyboard, User user){
        boolean loopRunning = true;
        while (loopRunning) {
            
            System.out.println(Constants.VIDEOS_MENU);
            String choice = keyboard.nextLine().trim();

            switch(choice){
                case Constants.WATCH_MOVIE:
                    runNavigationMenu(keyboard, user, ContentType.MOVIE);
                    break;
                case Constants.WATCH_TV_SHOW:
                    runNavigationMenu(keyboard, user, ContentType.TV_SERIES);
                    break;
                case Constants.VIDEO_MENU_EXIT:
                    loopRunning = false;
                    break;
                default:
                    System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    /**
     * Shows the audio menu until the user chooses to return.
     * @param keyboard the scanner used to read menu choices
     * @param user the current user
     */
    public void runAudioMenu(Scanner keyboard, User user){
        boolean loopRunning = true;
        while (loopRunning) {
            
            System.out.println(Constants.AUDIOS_MENU);
            String choice = keyboard.nextLine().trim();

            switch(choice){
                case Constants.LISTEN_SONGS:
                    runNavigationMenu(keyboard, user, ContentType.SONGS);
                    break;
                case Constants.LISTEN_PODCAST:
                    runNavigationMenu(keyboard, user, ContentType.PODCAST);
                    break;
                case Constants.AUDIO_MENU_EXIT:
                    loopRunning = false;
                    break;
                default:
                    System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    /**
     * Opens the game grid and its navigation menu.
     * @param keyboard the scanner used to read menu choices
     * @param user the current user
     */
    public void runGamesMenu(Scanner keyboard, User user) {
        runNavigationMenu(keyboard, user, ContentType.GAME);
    }

    // Builds a grid and handles movement and content selection.
    private void runNavigationMenu(Scanner keyboard, User user, ContentType contentType) {
        buildCanvas(contentType);

        if (canvas.length == 0) {
            return;
        }

        boolean loopRunning = true;
        String contentName = contentType.name().toLowerCase().replace('_', ' ');

        while (loopRunning) {
            printCanvas(contentType);
            System.out.printf(Constants.NAVIGATION_MENU, contentName, contentName);
            String choice = keyboard.nextLine().trim().toUpperCase();

            switch (choice) {
                case Constants.MOVE_UP:
                    navigate(DIRECTION.UP);
                    break;
                case Constants.MOVE_DOWN:
                    navigate(DIRECTION.DOWN);
                    break;
                case Constants.MOVE_LEFT:
                    navigate(DIRECTION.LEFT);
                    break;
                case Constants.MOVE_RIGHT:
                    navigate(DIRECTION.RIGHT);
                    break;
                case Constants.SELECT_CONTENT:
                    Content selectedContent = canvas[cursorRow][cursorCol];

                    if (selectedContent == null) {
                        System.out.println("There is no content in this cell.");
                        break;
                    }

                    runContentActionsMenu(keyboard, user, selectedContent);
                    break;
                case Constants.EXIT_NAVIGATION:
                    loopRunning = false;
                    break;
                default:
                    System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    
    // Shows the actions available for the selected content.
    private void runContentActionsMenu(Scanner keyboard, User user, Content content) {
        boolean loopRunning = true;
        ContentType contentType = content.getContentType();
        String typeName = contentType.name().replace('_', ' ');
        String contentName = typeName.toLowerCase();

        while (loopRunning) {
            if (contentType == ContentType.GAME) {
                System.out.printf(Constants.GAME_ACTIONS_MENU);
            } else {
                System.out.printf(Constants.CONTENT_ACTIONS_MENU,
                        typeName, contentName, contentName);
            }

            String choice = keyboard.nextLine().trim().toUpperCase();

            switch (choice) {
                case Constants.VIEW_CONTENT_DETAILS:
                    content.displayDetails();
                    break;
                case Constants.PLAY_CONTENT:
                    playContent(keyboard, user, content);
                    break;
                case Constants.ADD_TO_WATCHLIST:
                    if (contentType == ContentType.GAME) {
                        System.out.println(Constants.INVALID_INPUT);
                    } else if (content instanceof VideoContent) {
                        user.addToWatchlist((VideoContent) content);
                    } else if (content instanceof AudioContent) {
                        user.addToPlaylist(keyboard, (AudioContent) content);
                    }
                    break;
                case Constants.EXIT_CONTENT_ACTIONS:
                    loopRunning = false;
                    break;
                default:
                    System.out.println(Constants.INVALID_INPUT);
            }
        }
    }
    // Plays the selected content based on its type.
    private void playContent(Scanner keyboard, User user, Content content) {
        switch (content.getContentType()) {
            case MOVIE:
                System.out.println("Now playing Movie: " + content.getName());
                user.recordWatchedVideo((VideoContent) content);
                break;
            case TV_SERIES:
                playTVSeries(keyboard, user, (TVSeries) content);
                break;
            case SONGS:
                System.out.println("Now playing Song: " + content.getName());
                break;
            case PODCAST:
                System.out.println("Now playing Podcast: " + content.getName());
                break;
            case GAME:
                playGame(keyboard, (Game) content);
                break;
            default:
                break;
        }
    }
    // Reads a season and episode choice, then plays the episode.
    private void playTVSeries(Scanner keyboard, User user, TVSeries series) {
        ArrayList<Season> seasons = series.getSeasons();

        if (seasons.isEmpty()) {
            System.out.println("This Tv Series is not available in your region.");
            return;
        }

        System.out.println("Select a season:");
        for (int i = 0; i < seasons.size(); i++) {
            System.out.println("Season " + seasons.get(i).getSeasonNumber());
        }

        int seasonNumber;
        try {
            seasonNumber = Integer.parseInt(keyboard.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid season selected.");
            return;
        }

        Season season = series.getSeason(seasonNumber);
        if (season == null) {
            System.out.println("Invalid season selected.");
            return;
        }

        System.out.println("Select an episode:");
        for (int i = 1; i <= season.getNumberOfEpisodes(); i++) {
            System.out.println(i + ". " + season.getEpisode(i).getName());
        }

        int episodeNumber;
        try {
            episodeNumber = Integer.parseInt(keyboard.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(Constants.INVALID_INPUT);
            return;
        }

        Episode episode = season.getEpisode(episodeNumber);
        if (episode == null) {
            System.out.println(Constants.INVALID_INPUT);
            return;
        }

        System.out.println("Now playing " + series.getName() + " - Season "
                + seasonNumber + " Episode " + episodeNumber + ": " + episode.getName());
        user.recordWatchedVideo(series);
    }
    // Checks the player count and starts the game in the correct mode.
    private void playGame(Scanner keyboard, Game game) {
        System.out.println("Enter number of players:");

        int numberOfPlayers;
        try {
            numberOfPlayers = Integer.parseInt(keyboard.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(Constants.INVALID_INPUT);
            return;
        }

        if (numberOfPlayers < game.getMinPlayers()
                || numberOfPlayers > game.getMaxPlayers()) {
            System.out.println("Number of players must be between "
                    + game.getMinPlayers() + " and " + game.getMaxPlayers() + ".");
            return;
        }

        String mode = "challenge";
        if (numberOfPlayers == 1 && game.getMaxPlayers() > 1) {
            mode = "practice";
        }

        System.out.println("Now playing game: " + game.getName()
                + " in " + mode + " mode");
    }
    // Builds the grid for the given content type and resets the cursor.
    private void buildCanvas(ContentType contentType) {
        cursorRow = 0;
        cursorCol = 0;

        if (contentType == ContentType.GAME) {
            buildGameCanvas();
            return;
        }

        // Keep genres in the order in which they first appear in the catalogue.
        ArrayList<String> genres = new ArrayList<String>();

        for (int i = 0; i < contentCatalogue.size()
                && genres.size() < numGenre; i++) {
            Content content = contentCatalogue.get(i);

            if (content.getContentType() != contentType) {
                continue;
            }

            String genre = getGenreName(content);

            if (!genres.contains(genre)) {
                genres.add(genre);
            }
        }

        canvas = new Content[genres.size()][];

        for (int row = 0; row < canvas.length; row++) {
            ArrayList<Content> rowContents = new ArrayList<Content>();

            String rowGenre = genres.get(row);

            for (int i = 0; i < contentCatalogue.size(); i++) {
                Content content = contentCatalogue.get(i);

                if (content.getContentType() != contentType) {
                    continue;
                }

                if (getGenreName(content).equals(rowGenre)) {
                    rowContents.add(content);
                }
            }

            int rowSize = rowContents.size();
            canvas[row] = new Content[rowSize];

            for (int col = 0; col < rowSize; col++) {
                canvas[row][col] = rowContents.get(col);
            }
        }
    }

    // Fills the game grid in row order, up to nine games.
    private void buildGameCanvas() {
        canvas = new Content[GAME_CANVAS_SIZE][GAME_CANVAS_SIZE];
        int columnCount = canvas[0].length;
        int gameCount = 0;
        int maximumGames = canvas.length * columnCount;

        for (int i = 0; i < contentCatalogue.size() && gameCount < maximumGames; i++) {
            Content content = contentCatalogue.get(i);

            if (content.getContentType() != ContentType.GAME) {
                continue;
            }

            int row = gameCount / columnCount;
            int col = gameCount % columnCount;

            canvas[row][col] = content;
            gameCount++;
        }
    }

    // Gets the genre name of video or audio content.
    private String getGenreName(Content content) {
        if (content instanceof VideoContent) {
            return ((VideoContent) content).getGenre().name();
        }

        if (content instanceof Song) {
            return ((Song) content).getGenre().name();
        }

        if (content instanceof Podcast) {
            return ((Podcast) content).getGenre().name();
        }

        return "";
    }


    // Prints the current grid with its title and selected cell.
    private void printCanvas(ContentType contentType) {
        String canvasTitle = contentType.name().replace('_', ' ');

        System.out.println(Constants.CONTENT_CANVAS_HEADER);
        System.out.printf(CANVAS_TITLE_FORMAT, canvasTitle);
        System.out.println(Constants.CONTENT_CANVAS_HEADER);

        for (int row = 0; row < canvas.length; row++) {
            if (contentType != ContentType.GAME) {
                String genreLabel = getGenreName(canvas[row][0]);
                System.out.printf(Constants.GENRE_LABEL_WIDTH, genreLabel);
            }

            System.out.print(Constants.CELL_SEPARATOR);

            Content[] currentRow = canvas[row];

            for (int col = 0; col < currentRow.length; col++) {
                Content content = currentRow[col];
                String name = "----------";

                if (content != null) {
                    name = content.getName();
                }

                boolean isSelected = row == cursorRow && col == cursorCol;
                printCell(name, isSelected);
                System.out.print(Constants.CELL_SEPARATOR);
            }

            System.out.println();
        }

        System.out.println(Constants.CONTENT_CANVAS_HEADER);
    }


    // Prints one cell and marks it if it is selected.
        private void printCell(String name, boolean isSelected) {
        String displayName = formatContentName(name);
        String leftPadding = "  ";
        String rightPadding = "  ";

        if (isSelected) {
            leftPadding = " [";
            rightPadding = "] ";
        }

        System.out.print(leftPadding + displayName + rightPadding);
    }
  
    // Shortens or pads a content name to the cell width.
        private String formatContentName(String contentName) {
        if (contentName.length() > Constants.CONTENT_NAME_DISPLAY_LENGTH) {
            int visibleChars = Constants.CONTENT_NAME_DISPLAY_LENGTH - Constants.ELLIPSIS.length();
            return contentName.substring(0, visibleChars) + Constants.ELLIPSIS;
        }

        if (contentName.length() == Constants.CONTENT_NAME_DISPLAY_LENGTH) {
            return contentName;
        }

        StringBuilder paddedName = new StringBuilder(contentName);
        int spacesToAdd = Constants.CONTENT_NAME_DISPLAY_LENGTH - contentName.length();

        for (int space = 0; space < spacesToAdd; space++) {
            paddedName.append(" ");
        }
        return paddedName.toString();
    }

    
    // Moves the cursor one cell when the move is allowed.
    private void navigate(DIRECTION direction) {
        switch (direction) {
            case UP:
                if (cursorRow > 0) {
                    cursorRow--;
                } else {
                    System.out.println(Constants.CANNOT_MOVE_THERE);
                }
                break;
            case DOWN:
                if (cursorRow >= canvas.length - 1 || canvas[cursorRow + 1] == null) {
                    System.out.println(Constants.CANNOT_MOVE_THERE);

