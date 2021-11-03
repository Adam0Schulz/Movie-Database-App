
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class App implements Serializable {

    private static Database database = DatabaseConn.load("database.ser");
    private static User currentUser;

    // opening the scanner but not closing it (intentionally) because
    // it causes problems when there are multiple uses of a scanner

    public static void main(String[] args) throws Exception {

        // System.out.println(database.getMovies().get(0).getTitle());
        // System.out.println(database.getUsers().get(1).getUsername());
        start();

    }

    public static void start() {
        Screen.welcome();
        login();
    }

    public static void login() {

        String username = Screen.enter("your username");
        currentUser = database.searchForUser(username);

        if (currentUser == null) {
            Screen.noAccount();
            registration(username);
            menu(currentUser.isAdmin());
        } else {
            String password = Screen.enter("your password");
            if (currentUser.passwordValidation(password)) {
                menu(currentUser.isAdmin());
            } else {
                Screen.incorrectInput("password");
                login();
            }

        }

    }

    public static void registration(String username) {
        String password = Screen.enter("your password");
        database.addUser(new User(username, password));
        currentUser = database.searchForUser(username);
    }

    public static void menu(boolean admin) {

        ArrayList<String> options = new ArrayList<String>();
        options.add("List all the movie");
        options.add("Search for a movie");
        options.add("List all of your favourite movies");
        options.add("See your history");
        if (admin) {
            options.add("create a movie");
        }
        int choice = Screen.choice(options);

        if (choice == 1) {

            SelectMovie(database.getMovies());

        } else if (choice == 2) {

            searchMenu();

        } else if (choice == 3) {

            SelectMovie(currentUser.getFavouriteList());

        } else if (choice == 4 && admin) {

            SelectMovie(currentUser.getSeenMovies());

        } else {

            Screen.incorrectInput("choice");
            menu(admin);

        }
    }

    public static ArrayList<String> titles(ArrayList<Movie> array) {
        ArrayList<String> titles = new ArrayList<>();
        for (Movie movie : array) {
            titles.add(movie.getTitle());
        }
        return titles;
    }

    public static void SelectMovie(ArrayList array) {
        try {
            ArrayList<Movie> movieArray = (ArrayList<Movie>) array;
            int subChoice = Screen.choice(titles(movieArray));
            Movie movie = movieArray.get(subChoice);
            movieMenu(movie);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("there is a type casting problem in selectMovie");
        }

    }

    public static void searchMenu() {

        ArrayList<String> options = new ArrayList<String>();
        options.add("Title");
        options.add("Production year");
        options.add("Genre");
        options.add("Actor");

        int choice = Screen.choice(options);
        if (choice == 1) {
            String keyword = Screen.enter("title of the movie");
            ArrayList<Movie> selectedMovies = database.searchForMovieByTitle(keyword);
            int subChoice = Screen.choice(titles(selectedMovies));
            Movie movie = selectedMovies.get(subChoice);
            movieMenu(movie);
        } else if (choice == 2) {
            ArrayList<String> subOptions = new ArrayList<String>();
            subOptions.add("Search for movies produced after a certain year");
            subOptions.add("Seach for movies produced before a certain year");

            int subChoice = Screen.choice(subOptions);

            /********************* I got here ***********************/

            if (subChoice == 1) {
                System.out.println("Enter the production year after which you want to display the movies: ");
                ArrayList<Movie> selectedMovies = database.searchForMovieByYear(scannerInt(scanner), true);
                for (int i = 1; i <= selectedMovies.size(); i++) {
                    System.out.println(i + ": " + selectedMovies.get(i - 1).getTitle() + ", "
                            + selectedMovies.get(i - 1).getProductionYear());
                }
                Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
                movieMenu(movie);
            } else if (subChoice == 2) {
                System.out.println("Enter the production year before which you want to display the movies: ");
                ArrayList<Movie> selectedMovies = database.searchForMovieByYear(scannerInt(scanner), false);
                for (int i = 1; i <= selectedMovies.size(); i++) {
                    System.out.println(i + ": " + selectedMovies.get(i - 1).getTitle() + ", "
                            + selectedMovies.get(i - 1).getProductionYear());
                }
                Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
                movieMenu(movie);
            } else {
                Screen.incorrectInput("choice");
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
            Screen.incorrectInput("choice");
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

}
