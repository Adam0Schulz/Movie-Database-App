import java.util.ArrayList;
import java.io.*;

public class App implements Serializable {

    static Database database = load("database.ser");
    static String chooseSentence = "Please choose one of the following options:";
    static User currentUser;
    static Console console = System.console();

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
         * database.addUser(admin); save(database);
         */

        /*
         * database.addUser(new User("Babak", "kebab")); save(database);
         */
        // System.out.println(database.getMovies().get(0).getTitle());
        // System.out.println(database.getUsers().get(1).getUsername());
        start();

    }

    public static void start() {
        System.out.println(
                "Welcome to our awesome movie database thingy app. Enjoy yourself on your journey through this awesomeness!!!");
        login();
    }

    public static void login() {
        System.out.println("Please enter your username: ");
        String username = console.readLine();
        currentUser = database.searchForUser(username);
        if (currentUser == null) {
            System.out
                    .println("Your have not created an account yet. In order to create an account create a password.");
            String password = console.readLine();
            database.addUser(new User(username, password));
            currentUser = database.searchForUser(username);
            menu();
        } else {
            System.out.println("Please enter your password: ");
            String password = console.readLine();
            if (currentUser.passwordValidation(password)) {
                menu();
            } else {
                incorrectInput("password");
                login();
            }

        }
    }

    public static void menu() {
        System.out.println(chooseSentence
                + "(1) list all the movies, (2) search for a movie by title, (3) list all of your favourite movies");
        if (Integer.parseInt(console.readLine()) == 1) {
            System.out.println("Enter the number of the coresponding movie: ");
            database.listMovies();
            Movie movie = database.selectMovie(Integer.parseInt(console.readLine()));
            movieMenu(movie);
        } else if (Integer.parseInt(console.readLine()) == 2) {
            System.out.println("Enter the title of movie you want to search for: ");
            Movie selectedMovie = database.searchForMovie(console.readLine());
            if (selectedMovie == null) {
                incorrectInput("movie title");
                menu();
            } else {
                movieMenu(selectedMovie);
            }
        } else if (Integer.parseInt(console.readLine()) == 3) {
            System.out.println(
                    "This is your favourite list. You can play a movie by typing the coresponding number or you can go back by typing the number 0");
            currentUser.listFavourites();
            int input = Integer.parseInt(console.readLine());
            if (input == 0) {
                menu();
            } else {
                Movie selectedMovie = currentUser.getFavouriteList().get(input - 1);
                selectedMovie.playMovie();
                currentUser.addSeenMovie(selectedMovie);
                goBack();
            }
        } else {
            incorrectInput("input");
            menu();
        }
        // print out the prompt to choose between list movies, search for a movie and
        // list your favourite list, and calls according methods.
    }

    public static void movieMenu(Movie movie) {
        System.out.println(chooseSentence + "(1) play " + movie.getTitle() + ", (2) add " + movie.getTitle()
                + " to your favourite list");
        if (Integer.parseInt(console.readLine()) == 1) {
            movie.playMovie();
            currentUser.addSeenMovie(movie);
            goBack();

        } else if (Integer.parseInt(console.readLine()) == 2) {
            currentUser.addToFavouriteList(movie);
            menu();
        }
    }

    public static void goBack() {
        System.out.println("Enter the letter Q to go back to the main menu");
        if (console.readLine().equalsIgnoreCase("q")) {
            menu();
        } else {
            incorrectInput("input");
            goBack();
        }
    }

    public static void listFavouriteList() {
        System.out.println("here is your list of your favourite movies");
    }

    /*
     * public static String scannerString(Scanner scanner) { String input =
     * scanner.nextLine(); return input; }
     * 
     * public static int scannerInt(Scanner scanner) { int input =
     * scanner.nextInt(); return input; }
     * 
     * public static void closeScanner(Scanner scanner) { scanner.close(); }
     */

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
