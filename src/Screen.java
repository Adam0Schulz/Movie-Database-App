
/**
 * Screen
 * 
 * Class to group methods to communicate with the user
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Screen {

    // Attributes
    private static Scanner scanner = new Scanner(System.in);
    private static Database database;
    private static User user;

    // Messages
    private static String noAccountMessage = "You have not created an account yet or your login credentials are incorrect";
    private static String welcomeMessage = "     ___   ____    __    ____  _______      _______.  ______   .___  ___.  _______  _______  __       __  ___   ___ \n"
            + "    /   \\  \\   \\  /  \\  /   / |   ____|    /       | /  __  \\  |   \\/   | |   ____||   ____||  |     |  | \\  \\ /  / \n"
            + "   /  ^  \\  \\   \\/    \\/   /  |  |__      |   (----`|  |  |  | |  \\  /  | |  |__   |  |__   |  |     |  |  \\  V  /  \n"
            + "  /  /_\\  \\  \\            /   |   __|      \\   \\    |  |  |  | |  |\\/|  | |   __|  |   __|  |  |     |  |   >   <   \n"
            + " /  _____  \\  \\    /\\    /    |  |____ .----)   |   |  `--'  | |  |  |  | |  |____ |  |     |  `----.|  |  /  .  \\  \n"
            + "/__/     \\__\\  \\__/  \\__/     |_______||_______/     \\______/  |__|  |__| |_______||__|     |_______||__| /__/ \\__\\ ";
    private static String goBackInfoMessage = "Anytime you feel like you had enough of this awesomeness press Q to awesome save and exit in style\n";
    private static String chooseSentence = "Please choose one of the following options:";

    // Setting the database and user for internal use
    public static void set(Database database, User user) {
        Screen.database = database;
        Screen.user = user;

    }

    // User Input
    public static String enter(String info) {
        print("Please enter " + info + ": ");
        return scanStr();
    }

    public static int enterInt(String info) {
        return Integer.parseInt(enter(info));
    }

    public static void print(Object value) {
        System.out.println(value);
    }

    // Handeling the scanner input methods
    public static String scanStr() {
        String input = scanner.nextLine();

        int intInput = 1;
        try {
            intInput = Integer.parseInt(input);
        } catch (Exception e) {
        }
        if (input.equalsIgnoreCase("q")) {
            print("Saving...");
            DatabaseConn.save(database);
            clear();
            System.exit(0);

        } else if (intInput == 0) {
            App.mainMenu();
        }

        return input;
    }

    public static int scanInt() {
        String input = scanner.nextLine(); // the reason why I'm using the nextLine() and then parsing it to integer and
                                           // not nextInt() is that nextInt() causes problems with leftover \n (enters)
                                           // and messes up the inputs
        if (input.equalsIgnoreCase("q")) {
            print("Saving...");
            DatabaseConn.save(database);
            clear();
            System.exit(0);

        }
        int intInput = 1;
        try {
            intInput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            print(new Error("Error: expected input type - int"));
            App.mainMenu();
        }
        if (intInput == 0) {
            App.mainMenu();
        }

        return intInput;
    }

    // Messages
    // Welcome message
    public static void welcome() {

        print(welcomeMessage);
        print(goBackInfoMessage);

    }

    // Not existing account
    public static void noAccount() {
        print(noAccountMessage);
    }

    // Choosing one of the following
    public static void listOptions(ArrayList<String> options) {
        if (options.size() == 0) {
            print("0 items found");
        } else {
            for (int i = 1; i <= options.size(); i++) {
                print(i + ": " + options.get(i - 1));
            }
        }

    }

    public static int choice(ArrayList<String> options) {
        print(chooseSentence);
        listOptions(options);
        int choice = scanInt();
        return choice;
    }

    // this seem to be a useful function but I'm not sure about its placement
    public static Object chooseListItem(ArrayList array, String search) {
        String[] searchArr = search.split(" - ", 2);
        if (array.get(0) instanceof Movie) {
            Movie movie;
            int choice = choice(Movie.toString(array, "", "")) - 1;
            movie = (Movie) array.get(choice);
            return movie;
        } else if (array.get(0) instanceof Movie && searchArr[0].equals("char")) {
            Movie movie;
            int choice = choice(Movie.toString(array, searchArr[0], searchArr[1])) - 1;
            movie = (SeenMovie) array.get(choice);
            return movie;
        } else if (array.get(0) instanceof SeenMovie) {
            SeenMovie movie;
            int choice = choice(SeenMovie.toString(array)) - 1;
            movie = (SeenMovie) array.get(choice);
            return movie;
        } else if (array.get(0) instanceof Character) {
            Character character;
            int choice = choice(Character.toString(array)) - 1;
            character = (Character) array.get(choice);
            return character;
        } else {
            return null;
        }
    }

    // Clearing the console - taken from the internet
    public static void clear() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    // Outputting incorrectInput error
    public static void incorrectInput(String input) {
        print(new Error("Incorrect " + input + ". Please try again."));
    }
}