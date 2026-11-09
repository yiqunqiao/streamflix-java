/**
Student Name - Yiqun Qiao
*/
import canvas.Canvas;
import canvas.User;
import utils.Constants;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import loader.ContentLoader;
import content.Content;
import java.util.ArrayList;
import loader.TVSeriesLoader;
import loader.UserSessionLoader;


/** Runs StreamFlix. */
public class StreamingApp {


    private static String userName;
    private static int numberOfGenre;
    private static String contentFileName;
    private static String tvSeriesFileName;
    private static final String USER_SESSION_FILE = "data/userSessionData.csv";
    private static final int REQUIRED_ARGUMENT_COUNT = 4;

    /**
     * Loads the data, runs the menus, and saves the user session.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        StreamingApp engine = new StreamingApp();

        if (engine.validateCmdArgs(args)) {

            Canvas canvas;
            User user = new User(userName);

            try {
                ContentLoader contentLoader = new ContentLoader();
                ArrayList<Content> catalogue = contentLoader.loadContentFile(contentFileName);
                System.out.printf("Content File: %s loaded successfully.%n", contentFileName);

                TVSeriesLoader tvSeriesLoader = new TVSeriesLoader();
                tvSeriesLoader.loadTVSeriesFile(tvSeriesFileName, catalogue);
                System.out.printf("TV Series File: %s loaded successfully.%n", tvSeriesFileName);

                UserSessionLoader sessionLoader = new UserSessionLoader();

                // A missing session file is allowed for a new user.
                if (sessionLoader.loadSessionFile(USER_SESSION_FILE, user)) {
                    System.out.printf("User Session Data File: %s loaded successfully.%n",
                            USER_SESSION_FILE);
                }

                canvas = new Canvas(numberOfGenre, catalogue);
            } catch (IOException e) {
                System.out.println(Constants.FILE_NOT_FOUND);
                return;
            }

            System.out.printf(Constants.WELCOME_MESSAGE, user.getName());

            engine.runMainMenuLoop(canvas, user);

          
            // Save once after the user leaves the main menu.
            try {
                user.saveSessionFile(USER_SESSION_FILE);
            } catch (IOException e) {
                System.out.println(Constants.FILE_NOT_FOUND);
                return;
            }

            System.out.println(Constants.GOODBYE_MESSAGE);

        }
    }

    // Checks the arguments in order and prints the first error found.
    private boolean validateCmdArgs(String[] args) {
        if (args.length != REQUIRED_ARGUMENT_COUNT) {
            System.out.println(Constants.INVALID_COMMAND_LINE_ARGUMENTS);
            return false;
        }

        userName = args[0].trim();
        if (userName.length() == 0) {
            System.out.println(Constants.INVALID_USER_NAME);
            return false;
        }

        try {
            numberOfGenre = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println(Constants.INVALID_NUMBER_OF_GENRES);
            return false;
        }

        if (numberOfGenre <= 0
                || numberOfGenre > Constants.MAX_NUMBER_OF_GENRES) {
            System.out.println(Constants.INVALID_NUMBER_OF_GENRES);
            return false;
        }

        contentFileName = args[2].trim();
        if (contentFileName.length() == 0) {
            System.out.println(Constants.INVALID_CONTENT_FILE_NAME);
            return false;
        }

        tvSeriesFileName = args[3].trim();
        if (tvSeriesFileName.length() == 0) {
            System.out.println(Constants.INVALID_TV_SERIES_FILE_NAME);
            return false;
        }

        try {
            Scanner contentFile = new Scanner(new FileInputStream(contentFileName));
            contentFile.close();
            Scanner tvSeriesFile = new Scanner(new FileInputStream(tvSeriesFileName));
            tvSeriesFile.close();
        } catch (FileNotFoundException e) {
            System.out.println(Constants.FILE_NOT_FOUND);
            return false;
        }

        return true;
    }

    // Shows the main menu until the user chooses to exit.
    private void runMainMenuLoop(Canvas canvas, User user) {

        boolean loopRunning = true;
        while (loopRunning) {
            System.out.println(Constants.MAIN_MENU);
            String choice = Constants.SCANNER.nextLine().trim();

            switch(choice){
                case Constants.VIDEO_CANVAS:
                    canvas.runVideoMenu(Constants.SCANNER, user);
                    break;
                case Constants.AUDIO_CANVAS:
                    canvas.runAudioMenu(Constants.SCANNER, user);
                    break;
                case Constants.GAMES_CANVAS:
                    canvas.runGamesMenu(Constants.SCANNER, user);
                    break;
                case Constants.USER_SETTINGS:
                    user.runUserMenu(Constants.SCANNER);
                    break;
                case Constants.EXIT_PROGRAM:
                    loopRunning = false;
                    break;
                default:

                    System.out.println(Constants.INVALID_INPUT);
            }
        }
    }
}


