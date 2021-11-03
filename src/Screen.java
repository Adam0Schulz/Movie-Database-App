
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
    private static String welcomeMessage = "\nWelcome to AWESOMEFLIX\nEnjoy yourself on your journey through this awesomeness!!!\n";
    private static String goBackInfoMessage = "Anytime you feel like you had enough of this awesomeness press Q to awesome save and exit in style\n";
    private static String chooseSentence = "Please choose one of the following options:";

    // Setting the database and user for internal use
    public static void set(Database database, User user) {
        Screen.database = database;
        Screen.user = user;

    }

    // User Input
    public static String enter(String info) {
        System.out.println("Please enter " + info + ": ");
        return scanString(scanner);
    }

    // Handeling the scanner input methods
    public static String scanString(Scanner scanner) {
        String input = scanner.nextLine();

        int intInput = 1;
        try {
            intInput = Integer.parseInt(input);
        } catch (Exception e) {
        }
        if (input.equalsIgnoreCase("q")) {
            System.out.println("Saving...");
            DatabaseConn.save(database);
            clear();
            System.exit(0);

        } else if (intInput == 0) {
            App.menu(user.isAdmin());
        }

        return input;
    }

    public static int scanInt(Scanner scanner) {
        String input = scanner.nextLine(); // the reason why I'm using the nextLine() and then parsing it to integer and
                                           // not nextInt() is that nextInt() causes problems with leftover \n (enters)
                                           // and messes up the inputs
        if (input.equalsIgnoreCase("q")) {
            System.out.println("Saving...");
            DatabaseConn.save(database);
            clear();
            System.exit(0);

        }
        int intInput = 1;
        try {
            intInput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(new Error("Error: expected input type - int"));
            App.menu(user.isAdmin());
        }
        if (intInput == 0) {
            App.menu(user.isAdmin());
        }

        return intInput;
    }

    // Scanner getter
    public static Scanner getScanner() {
        return scanner;
    }

    // Messages
    // Welcome message
    public static void welcome() {

        System.out.println(welcomeMessage);
        System.out.println(goBackInfoMessage);

    }

    // Not existing account
    public static void noAccount() {
        System.out.println(noAccountMessage);
    }

    // Choosing one of the following
    public static int choice(ArrayList<String> options) {
        System.out.println(chooseSentence);
        for (int i = 1; i < options.size(); i++) {
            System.out.println("(" + i + ") " + options.get(i - 1) + ", ");
        }
        int choice = scanInt(scanner);
        return choice;
    }

    // Clearing the console - taken from the internet
    public static void clear() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    // Outputting incorrectInput error
    public static void incorrectInput(String input) {
        System.out.println(new Error("Incorrect " + input + ". Please try again."));
    }
}