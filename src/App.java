import java.util.Scanner;

public class App {

    static Database database = new Database();
    static String chooseSentence = "Please choose one of the following options:";
    static User currentUser;

    public static void main(String[] args) throws Exception {
        start();

    }

    public static void start() {
        System.out.println(
                "Welcome to our awesome movie database thingy app. Enjoy yourself on your journey through this awesomeness!!!");
        login();
    }

    public static void login() {
        System.out.println("Please enter your username: ");
        String username = scannerString();
        currentUser = database.searchForUser(username);
        if (currentUser == null) {
            System.out
                    .println("Your have not created an account yet. In order to create an account create a password.");
            String password = scannerString();
            database.addUser(new User(username, password));
            currentUser = database.searchForUser(username);
            menu();
        } else {
            System.out.println("Please enter your password: ");
            String password = scannerString();
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
        if (scannerInt() == 1) {
            System.out.println("Enter the number of the coresponding movie: ");
            database.listMovies();
            Movie movie = database.selectMovie(scannerInt());
            movieMenu(movie);
        } else if (scannerInt() == 2) {
            System.out.println("Enter the title of movie you want to search for: ");
            Movie selectedMovie = database.searchForMovie(scannerString());
            if (selectedMovie == null) {
                incorrectInput("movie title");
                menu();
            } else {
                movieMenu(selectedMovie);
            }
        } else if (scannerInt() == 3) {
            System.out.println(
                    "This is your favourite list. You can play a movie by typing the coresponding number or you can go back by typing the number 0");
            currentUser.listFavourites();
            int input = scannerInt();
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
        if (scannerInt() == 1) {
            movie.playMovie();
            currentUser.addSeenMovie(movie);
            goBack();

        } else if (scannerInt() == 2) {
            currentUser.addToFavouriteList(movie);
            menu();
        }
    }

    public static void goBack() {
        System.out.println("Enter the letter Q to go back to the main menu");
        if (scannerString().equalsIgnoreCase("q")) {
            menu();
        } else {
            incorrectInput("input");
            goBack();
        }
    }

    public static void listFavouriteList() {
        System.out.println("here is your list of your favourite movies");
    }

    public static String scannerString() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }

    public static int scannerInt() {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.close();
        return input;
    }

    public static void incorrectInput(String input) {
        System.out.println("Incorrect " + input + ". Please try again.");
    }
}
