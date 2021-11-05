
/**
 * Screen
 * 
 * Class to group methods which interact with the console
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Screen {

    // Attributes
    private static Scanner scanner = new Scanner(System.in);

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

    // User Input
    public static String enter(String info) {
        print("Please enter " + info + ": ");
        return scanStr();
    }

    public static int enterInt(String info) {
        return Integer.parseInt(enter(info));
    }

    // Basic sout
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
            DatabaseConn.save(App.getDatabase());
            pause();
            clear();
            System.exit(0);

        } else if (intInput == 0) {
            App.mainMenu();
        }

        return input;
    }

    public static int scanInt() {
        /*
         * The reason why I'm using the nextLine() and then parsing it to integer and
         * not nextInt() is that nextInt() causes problems with leftover \n (enters)
         */
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("q")) {
            print("Saving...");
            DatabaseConn.save(App.getDatabase());
            pause();
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

    public static void welcome() {
        clear();
        print(welcomeMessage);
        print(goBackInfoMessage);

    }

    // Display not existing account message
    public static void noAccount() {
        print(noAccountMessage);
    }

    // Displays options
    public static void listOptions(ArrayList<String> options) {
        if (options.size() == 0) {
            print("\nThis list is empty\n");
        } else {
            for (int i = 1; i <= options.size(); i++) {
                print(i + ": " + options.get(i - 1));
            }
        }

    }

    // Displays options and returns the choice int
    public static int choice(ArrayList<String> options) {
        print(chooseSentence);
        listOptions(options);
        int choice = scanInt();
        return choice;
    }

    // Displays options, selects the choice from the options and returns it
    public static Object chooseListItem(ArrayList array, String search) {

        String[] searchArr = search.split(" - ", 2);
        Object object;

        try {
            object = array.get(0);
        } catch (IndexOutOfBoundsException e) {
            print("\nThis list is empty\n");
            pause();
            return null;
        }

        if (object instanceof Movie && !(object instanceof SeenMovie) && search == "") {

            Movie movie;
            int choice = choice(Movie.toString(array, "")) - 1;
            movie = (Movie) array.get(choice);
            return movie;

        } else if (object instanceof Movie && searchArr[0].equals("char")) {

            Movie movie;
            int choice = choice(Movie.toString(array, search)) - 1;
            movie = (Movie) array.get(choice);
            return movie;

        } else if (object instanceof Movie && searchArr[0].equals("year")) {

            Movie movie;
            int choice = choice(Movie.toString(array, search)) - 1;
            movie = (Movie) array.get(choice);
            return movie;

        } else if (object instanceof Movie && searchArr[0].equals("genre")) {

            Movie movie;
            int choice = choice(Movie.toString(array, search)) - 1;
            movie = (Movie) array.get(choice);
            return movie;

        } else if (object instanceof SeenMovie) {

            SeenMovie movie;
            int choice = choice(SeenMovie.toString(array)) - 1;
            movie = (SeenMovie) array.get(choice);
            return movie;

        } else if (object instanceof Character) {

            Character character;
            int choice = choice(Character.toString(array)) - 1;
            character = (Character) array.get(choice);
            return character;

        } else {
            return null;
        }
    }

    // Pauses the application for 0.8 seconds
    public static void pause() {
        try {
            Thread.sleep(800);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clears the console
    public static void clear() {

        /*
         * print("\033[H\033[2J"); System.out.flush();
         */
        print("clearing the console");

    }

    // Displays incorrectInput error
    public static void incorrectInput(String input) {
        print(new Error("Incorrect " + input + ". Please try again."));
    }

    public static ArrayList<Character> characterInsertion(String input, ArrayList<Character> characters) {
        if (input.equals("done")) {
            return characters;
        } else {
            String[] attributes = input.split(" as ", 2);
            Character character = new Character(attributes[1], attributes[0]);
            characters.add(character);

            characterInsertion(Screen.scanStr(), characters);
        }
        return characters;
    }

}