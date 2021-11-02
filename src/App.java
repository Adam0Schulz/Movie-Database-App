/*
Notes for later
3. make the commands consistent
4. divide methods into smaller ones if it makes sense
5. divide methods across classes if it makes sense
6. have as little methods in App class as possible
9. be able to search for a movie by all of it's attributes
- complete the createMovie()
-
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class App implements Serializable {

    static Database database = DatabaseConn.load("database.ser");
    static String chooseSentence = "Please choose one of the following options:";
    static User currentUser;
    static Scanner scanner = new Scanner(System.in); // opening the scanner but not closing it (intentionally) because
                                                     // it causes problems when there are multiple uses of a scanner

    public static void main(String[] args) throws Exception {

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
                + "(1) list all the movies, (2) search for a movie, (3) list all of your favourite movies, (4) see your history"
                + extension);
        int choice = scannerInt(scanner);
        if (choice == 1) {
            System.out.println("If you want to select the movie enter the corresponding number: ");
            database.listMovies();
            Movie movie = database.selectMovie(scannerInt(scanner) - 1);
            movieMenu(movie);
        } else if (choice == 2) {
            searchMenu();
        } else if (choice == 3) {
            System.out.println(
                    "This is your favourite list. You can enter the corresponding number to select the movie: ");
            currentUser.listFavourites();
            Movie selectedMovie = currentUser.getFavouriteList().get(scannerInt(scanner) - 1);
            movieMenu(selectedMovie);

        } else if (choice == 4) {
            System.out.println("If you want to select the movie enter the corresponding number: ");
            currentUser.listSeenMovies();
            Movie movie = currentUser.getSeenMovies().get(scannerInt(scanner) - 1);
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

    public static void searchMenu() {
        System.out.println("Select a search criteria\n1: Title\n2: Production year\n3: Genre\n4: Actor");
        int choice = scannerInt(scanner);
        if (choice == 1) {
            System.out.println("Please enter title of the movie you want to search for: ");
            ArrayList<Movie> selectedMovies = database.searchForMovieByTitle(scannerString(scanner));
            for (int i = 1; i <= selectedMovies.size(); i++) {
                System.out.println(i + ": " + selectedMovies.get(i - 1).getTitle());

            }
            Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
            movieMenu(movie);
        } else if (choice == 2) {
            System.out.println(
                    "Select one of the following: (1) search for movies produced after a certain year, (2) search for movies produced before a certain year");
            int input = scannerInt(scanner);
            if (input == 1) {
                System.out.println("Enter the production year after which you want to display the movies: ");
                ArrayList<Movie> selectedMovies = database.searchForMovieByYear(scannerInt(scanner), true);
                for (int i = 1; i <= selectedMovies.size(); i++) {
                    System.out.println(i + ": " + selectedMovies.get(i - 1).getTitle() + ", "
                            + selectedMovies.get(i - 1).getProductionYear());
                }
                Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
                movieMenu(movie);
            } else if (input == 2) {
                System.out.println("Enter the production year before which you want to display the movies: ");
                ArrayList<Movie> selectedMovies = database.searchForMovieByYear(scannerInt(scanner), false);
                for (int i = 1; i <= selectedMovies.size(); i++) {
                    System.out.println(i + ": " + selectedMovies.get(i - 1).getTitle() + ", "
                            + selectedMovies.get(i - 1).getProductionYear());
                }
                Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
                movieMenu(movie);
            } else {
                incorrectInput("choice");
                searchMenu();
            }

        } else if (choice == 3) {
            System.out.println("Please enter movie genre you want to search for: ");
            ArrayList<Movie> selectedMovies = database.searchForMovieByGenre(scannerString(scanner));
            for (int i = 1; i < selectedMovies.size(); i++) {
                System.out.println(
                        i + ": " + selectedMovies.get(i - 1).getTitle() + ", " + selectedMovies.get(i - 1).getGenre());
            }
            Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
            movieMenu(movie);
        } else if (choice == 4) {
            System.out.println("Please enter the actor/actress you want to search for: ");
            String input = scannerString(scanner);
            ArrayList<ArrayList<Object>> selectedMovies = database.searchForMovieByActor(input);
            System.out.println(input + " stars in these movies: ");
            for (int i = 1; i <= selectedMovies.size(); i++) {
                Movie singleMovie = (Movie) selectedMovies.get(i - 1).get(0);
                Character singleCharacter = (Character) selectedMovies.get(i - 1).get(1);
                System.out.println(i + ": " + singleMovie.getTitle() + ", " + singleCharacter);
            }
            Movie movie = (Movie) selectedMovies.get(scannerInt(scanner) - 1).get(0);
            movieMenu(movie);
        } else {
            incorrectInput("choice");
            searchMenu();
        }

    }

    public static void createMovie() {
        System.out.println("To create a movie please enter the following information: ");
        System.out.println("Title: ");
        String title = scannerString(scanner);
        System.out.println("Year of production: ");
        int year = scannerInt(scanner);
        System.out.println("Enter all of one/multiple genres separated by a space: ");
        String genre = scannerString(scanner);
        System.out.println(
                "Enter characters one by one in this format (actor as role) and when you're done enter done: ");
        ArrayList<Character> characters = new ArrayList<Character>();
        characters = characterInsertion(scannerString(scanner), characters);
        Movie movie = new Movie(title, year, genre, characters);
        database.addMovie(movie);
        System.out.println("Movie has been added to the database");
    }

    public static ArrayList<Character> characterInsertion(String input, ArrayList<Character> characters) {
        if (input.equals("done")) {
            return characters;
        } else {
            String[] attributes = input.split(" as ", 2);
            Character character = new Character(attributes[1], attributes[0]);
            characters.add(character);

            characterInsertion(scannerString(scanner), characters);
        }
        return characters;
    }

    public static void clear() {

        System.out.print("Everything on the console will cleared");
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    public static void movieMenu(Movie movie) {
        String adminOptions = "";
        String favouriteOption = ", (2) add " + movie.getTitle() + " from your favourite list";
        boolean admin = currentUser.isAdmin();
        boolean favourite = currentUser.getFavouriteList().contains(movie);
        if (admin) {
            adminOptions = ", (3) update " + movie.getTitle() + ", (4) remove " + movie.getTitle();
        }
        if (favourite) {
            favouriteOption = ", (2) remove " + movie.getTitle() + " from your favourite list";
        }

        System.out.println(chooseSentence + "(1) play " + movie.getTitle() + favouriteOption + adminOptions);
        int choice = scannerInt(scanner);
        if (choice == 1) {
            movie.playMovie();
            System.out.println("Rate this movie 1 to 5: ");
            currentUser.addSeenMovie(movie, scannerInt(scanner));
            menu(currentUser.isAdmin());
        } else if (choice == 2 && favourite) {
            currentUser.removeFavouriteList(movie);
            menu(currentUser.isAdmin());
        } else if (choice == 2 && !favourite) {

        } else if (choice == 3 && admin) {
            updateMovie(movie);
        } else if (choice == 4 && admin) {
            removeMovie(movie);
        } else {
            incorrectInput("choice");
            movieMenu(movie);
        }

    }

    public static void updateMovie(Movie movie) {
        System.out.println(
                "Please select the attribute that you want to update:\n1: Title\n2: Production year\n3: Genre\n4: Characters");
        int choice = scannerInt(scanner);
        if (choice == 1) {
            System.out.println("Current title: " + movie.getTitle());
            System.out.println("Enter the new title: ");
            String newTitle = scannerString(scanner);
            movie.setTitle(newTitle);
            System.out.println("Title has been updated");
            menu(currentUser.isAdmin());
        } else if (choice == 2) {
            System.out.println("Current year of production: " + movie.getProductionYear());
            System.out.println("Enter the new year of production: ");
            int newYear = scannerInt(scanner);
            movie.setProductionYear(newYear);
            System.out.println("Year of production has been updated");
            menu(currentUser.isAdmin());
        } else if (choice == 3) {
            System.out.println("Current genre/genres: " + movie.getGenre());
            System.out.println("Enter the new genre/genres (separated by a space): ");
            String newGenre = scannerString(scanner);
            movie.setGenre(newGenre);
            System.out.println("Genre has been updated");
            menu(currentUser.isAdmin());
        } else if (choice == 4) {
            System.out.println("Please select a character to update: ");
            for (int i = 1; i <= movie.getCharacters().size(); i++) {
                System.out.println(i + ": " + movie.getCharacters().get(i - 1));
            }
            int subChoice = scannerInt(scanner);
            System.out.println("Current character: " + movie.getCharacters().get(subChoice - 1));
            String input = scannerString(scanner);
            String[] attributes = input.split(" as ", 2);
            movie.getCharacters().get(subChoice - 1).setActor(attributes[0]);
            movie.getCharacters().get(subChoice - 1).setRole(attributes[1]);
            System.out.println("Character has been updated");
            menu(currentUser.isAdmin());
        } else {
            incorrectInput("choice");
            updateMovie(movie);
        }
    }

    public static void removeMovie(Movie movie) {
        database.removeMovie(movie);
        menu(currentUser.isAdmin());
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
            DatabaseConn.save(database);
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
            DatabaseConn.save(database);
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

}
