/*
Notes for later
1.7. deleting a movie from a favourites
3. make the commands consistent
4. divide methods into smaller ones if it makes sense
5. divide methods across classes if it makes sense
6. have as little methods in App class as possible
*/

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class App implements Serializable {

    // static Database database = new Database();
    static Database database = load("database.ser");
    static String chooseSentence = "Please choose one of the following options:";
    static User currentUser;
    static Scanner scanner = new Scanner(System.in); // opening the scanner but not closing it (intentionally) because
                                                     // it causes problems when there are multiple uses of a scanner

    public static void main(String[] args) throws Exception {

        // addition of the first two movies
        /*
         * ArrayList<Character> characters = new ArrayList<Character>();
         * characters.add(new Character("Teddy Adams", "Allen C. Gardner"));
         * characters.add(new Character("Lloyd Gibbard", "Drew Smith"));
         * characters.add(new Character("Hailey Emerson", "Hayden Blane"));
         * characters.add(new Character("Lisa Lane", "Alexis Boozer Sterling"));
         * database.addMovie(new Movie("Being Awesome", 2014, "drama", characters));
         * characters = new ArrayList<Character>(); characters.add(new
         * Character("Stephen", "Stephen Dypiangco")); characters.add(new
         * Character("Patrick", "Patrick Epino")); characters.add(new
         * Character("Tamlyn", "Tamlyn Tomito")); characters.add(new Character("Al",
         * "Al Leong")); database.addMovie(new Movie("Awesome Asian Bad Guys", 2014,
         * "comedy action", characters));
         */
        // addition of admin user
        /*
         * User admin = new User("admin", "Admin123"); admin.makeAdmin();
         * database.addUser(admin);
         * 
         * database.addUser(new User("Babak", "kebab")); save(database);
         */
        // System.out.println(database.getMovies().get(0).getTitle());
        // System.out.println(database.getUsers().get(1).getUsername());
        start();

    }

    public static void start() {
        System.out.println(
                "\nWelcome to our awesome movie database thingy app. Enjoy yourself on your journey through this awesomeness!!!");
        System.out.println(
                "Anytime you feel like you had enough of this awesomeness press Q to awesome save and exit in style\n");
        login();
    }

    public static void login() {
        System.out.println("Please enter your username: ");
        String username = scannerString(scanner);
        currentUser = database.searchForUser(username);
        if (currentUser == null) {
            System.out
                    .println("Your have not created an account yet. In order to create an account create a password.");
            String password = scannerString(scanner);
            database.addUser(new User(username, password));
            currentUser = database.searchForUser(username);
            menu(currentUser.isAdmin());
        } else {
            System.out.println("Please enter your password: ");
            String password = scannerString(scanner);
            if (currentUser.passwordValidation(password)) {
                menu(currentUser.isAdmin());
            } else {
                incorrectInput("password");
                login();
            }

        }

    }

    public static void menu(boolean admin) {
        String extension = "";
        if (admin) {
            extension = ", (5) create a movie";
        }
        System.out.println(chooseSentence
                + "(1) list all the movies, (2) search for a movie by title, (3) list all of your favourite movies, (4) list all the seen movies"
                + extension);
        int choice = scannerInt(scanner);
        if (choice == 1) {
            System.out.println("If you want to select the movie enter the corresponding number: ");
            database.listMovies();
            Movie movie = database.selectMovie(scannerInt(scanner) - 1);
            movieMenu(movie);
        } else if (choice == 2) {
            System.out.println("Enter the title of movie you want to search for: ");
            Movie selectedMovie = database.searchForMovie(scannerString(scanner));
            if (selectedMovie == null) {
                incorrectInput("movie title");
                menu(currentUser.isAdmin());
            } else {
                movieMenu(selectedMovie);
            }
        } else if (choice == 3) {
            System.out.println(
                    "This is your favourite list. You can enter the corresponding number to select the movie: ");
            currentUser.listFavourites();
            int input = scannerInt(scanner);

            Movie selectedMovie = currentUser.getFavouriteList().get(input - 1);
            movieMenu(selectedMovie);

        } else if (choice == 4) {
            System.out.println("If you want to select the movie enter the corresponding number: ");
            currentUser.listSeenMovies();
            Movie movie = database.selectMovie(scannerInt(scanner) - 1);
            movieMenu(movie);
        } else {

            if (currentUser.isAdmin()) {
                if (choice == 5) {
                    createMovie();
                    menu(currentUser.isAdmin());
                } else {
                    incorrectInput("choice");
                    menu(currentUser.isAdmin());
                }
            } else {
                incorrectInput("choice");
                menu(currentUser.isAdmin());
            }

        }
        // print out the prompt to choose between list movies, search for a movie and
        // list your favourite list, and calls according methods.
    }

    public static void createMovie() {
        System.out.println("To create a movie please enter the following information: ");
        System.out.println("Title: ");
        String title = scannerString(scanner);
        System.out.println("Year of production: ");
        int year = scannerInt(scanner);
        System.out.println("Enter all of one/multiple genres separated by a space: ");
        String genre = scannerString(scanner);
        System.out
                .println("Enter characters one by one in this format (role - actor) and when you're done enter done: ");
        ArrayList<Character> characters = new ArrayList<Character>();
        thingyThatWeWillCallLater(scannerString(scanner), characters);
        System.out.println("Movie has been added to the database");
    }

    public static ArrayList<Character> thingyThatWeWillCallLater(String input, ArrayList<Character> characters) {
        if (input.equals("done")) {
            return characters;
        } else {
            String[] attributes = input.split(" - ", 2);
            Character character = new Character(attributes[0], attributes[1]);
            characters.add(character);

            thingyThatWeWillCallLater(scannerString(App.scanner), characters);
        }
        return characters;
    }

    public static void clear() {

        System.out.print("Everything on the console will cleared");
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    public static void movieMenu(Movie movie) {

        if (currentUser.getFavouriteList().contains(movie)) {
            System.out.println(chooseSentence + "(1) play " + movie.getTitle() + ", (2) remove " + movie.getTitle()
                    + " from your favourite list");
            int choice = scannerInt(scanner);
            if (choice == 1) {
                movie.playMovie();
                System.out.println("Rate this movie 1 to 5: ");
                currentUser.addSeenMovie(movie, scannerInt(scanner));
                menu(currentUser.isAdmin());
            } else if (choice == 2) {
                currentUser.removeFavouriteList(movie);
                menu(currentUser.isAdmin());
            }
        } else {

            System.out.println(chooseSentence + "(1) play " + movie.getTitle() + ", (2) add " + movie.getTitle()
                    + " to your favourite list");
            int choice = scannerInt(scanner);
            if (choice == 1) {
                movie.playMovie();
                System.out.println("Rate this movie 1 to 5: ");
                currentUser.addSeenMovie(movie, scannerInt(scanner));
                menu(currentUser.isAdmin());
            } else if (choice == 2) {
                currentUser.addToFavouriteList(movie);
                menu(currentUser.isAdmin());
            }

        }

    }

    public static void listFavouriteList() {
        System.out.println("here is your list of your favourite movies");
    }

    public static String scannerString(Scanner scanner2) {
        String input = scanner2.nextLine();

        int intInput = 1;
        try {
            intInput = Integer.parseInt(input);
        } catch (Exception e) {

        }
        if (input.equalsIgnoreCase("q")) {
            System.out.println("Saving...");
            save(database);
            clear();
            System.exit(0);

        } else if (intInput == 0) {
            menu(currentUser.isAdmin());
        }

        return input;
    }

    public static int scannerInt(Scanner scanner) {
        String input = scanner.nextLine(); // the reason why I'm using the nextLine() and then parsing it to integer and
                                           // not nextInt() is that nextInt() causes problems with leftover \n (enters)
                                           // and messes up the inputs
        if (input.equalsIgnoreCase("q")) {
            System.out.println("Saving...");
            save(database);
            clear();
            System.exit(0);

        }
        int intInput = 1;
        try {
            intInput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(
                    "Error: listen here you dumb ass, the input should have been a number and not the gibberish that you entered");
            menu(currentUser.isAdmin());
        }
        if (intInput == 0) {
            menu(currentUser.isAdmin());
        }

        return intInput;
    }

    public static void incorrectInput(String input) {
        System.out.println("Incorrect " + input + ". Please try again.");
    }

    public static void save(Database database) {
        try {
            FileOutputStream fileOut = new FileOutputStream("database.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(database);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in database.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Database load(String file) {
        Database database = null;
        try {
            FileInputStream filein = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(filein);
            database = (Database) in.readObject();
            in.close();
            filein.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return database;

    }
}
