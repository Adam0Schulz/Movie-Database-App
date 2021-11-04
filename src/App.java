
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class App implements Serializable {

    private static Database database = DatabaseConn.load("database.ser");
    private static User currentUser;

    public static void main(String[] args) throws Exception {
        Screen.set(database, currentUser);

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
            menu();
        } else {
            String password = Screen.enter("your password");
            if (currentUser.passwordValidation(password)) {
                menu();
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

    public static void menu() {

        ArrayList<String> options = new ArrayList<String>();
        options.add("List all the movies");
        options.add("Search for a movie");
        options.add("List all of your favourite movies");
        options.add("See your history");
        if (currentUser.isAdmin()) {
            options.add("create a movie");
        }
        int choice = Screen.choice(options);

        if (choice == 1) {

            Movie movie = (Movie) Screen.chooseListItem(database.getMovies());
            movieMenu(movie);

        } else if (choice == 2) {

            searchMenu();

        } else if (choice == 3) {

            Movie movie = (Movie) Screen.chooseListItem(currentUser.getFavouriteList());
            movieMenu(movie);

        } else if (choice == 4 && currentUser.isAdmin()) {

            Movie movie = (Movie) Screen.chooseListItem(currentUser.getSeenMovies());
            movieMenu(movie);

        } else {

            Screen.incorrectInput("choice");
            menu();

        }
    }

    public static void searchMenu() {
        // Screen.print("hello");

        ArrayList<String> options = new ArrayList<String>();
        options.add("Title");
        options.add("Production year");
        options.add("Genre");
        options.add("Actor");

        int choice = Screen.choice(options);
        if (choice == 1) {
            String keyword = Screen.enter("title of the movie");
            ArrayList<Movie> selectedMovies = database.searchForMovieByTitle(keyword);
            Movie movie = (Movie) Screen.chooseListItem(selectedMovies);
            movieMenu(movie);
        } else if (choice == 2) {
            ArrayList<String> subOptions = new ArrayList<String>();
            subOptions.add("Search for movies produced after a certain year");
            subOptions.add("Seach for movies produced before a certain year");

            int subChoice = Screen.choice(subOptions);

            if (subChoice == 1) {
                int keyword = Screen.enterInt("the year of production");
                ArrayList<Movie> selectedMovies = database.searchForMovieByYear(keyword, true);
                for (int i = 1; i <= selectedMovies.size(); i++) {
                    Screen.print(i + ": " + selectedMovies.get(i - 1).getTitle() + ", "
                            + selectedMovies.get(i - 1).getProductionYear());
                }
                Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
                movieMenu(movie);
            } else if (subChoice == 2) {
                System.out.println("Enter the production year before which you want to display the movies: ");
                ArrayList<Movie> selectedMovies = database.searchForMovieByYear(scannerInt(scanner), false);
                for (int i = 1; i <= selectedMovies.size(); i++) {
                    Screen.print(i + ": " + selectedMovies.get(i - 1).getTitle() + ", "
                            + selectedMovies.get(i - 1).getProductionYear());
                }
                Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
                movieMenu(movie);
            } else {
                Screen.incorrectInput("choice");
                searchMenu();
            }

        } else if (choice == 3) {
            Screen.print("Please enter movie genre you want to search for: ");
            ArrayList<Movie> selectedMovies = database.searchForMovieByGenre(scannerString(scanner));
            for (int i = 1; i < selectedMovies.size(); i++) {
                Screen.print(
                        i + ": " + selectedMovies.get(i - 1).getTitle() + ", " + selectedMovies.get(i - 1).getGenre());
            }
            Movie movie = selectedMovies.get(scannerInt(scanner) - 1);
            movieMenu(movie);
        } else if (choice == 4) {
            Screen.print("Please enter the actor/actress you want to search for: ");
            String input = scannerString(scanner);
            ArrayList<ArrayList<Object>> selectedMovies = database.searchForMovieByActor(input);
            Screen.print(input + " stars in these movies: ");
            for (int i = 1; i <= selectedMovies.size(); i++) {
                Movie singleMovie = (Movie) selectedMovies.get(i - 1).get(0);
                Character singleCharacter = (Character) selectedMovies.get(i - 1).get(1);
                Screen.print(i + ": " + singleMovie.getTitle() + ", " + singleCharacter);
            }
            Movie movie = (Movie) selectedMovies.get(scannerInt(scanner) - 1).get(0);
            movieMenu(movie);
        } else {
            Screen.incorrectInput("choice");
            searchMenu();
        }

    }

    public static void createMovie() {

        String title = Screen.enter("the new title");
        int year = Screen.enterInt("the new production year");
        String genre = Screen.enter("genre/genres separated by a space");
        ArrayList<Character> characters = new ArrayList<Character>();
        Screen.print(
                "Please enter characters one by one in this format (actor as role) and when you're done enter done: ");
        characters = characterInsertion(Screen.scanStr(), characters);
        Movie movie = new Movie(title, year, genre, characters);
        database.addMovie(movie);
        Screen.print("Movie has been added to the database");
        menu();
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

    public static void movieMenu(Movie movie) {
        if (movie == null) {
            Screen.print("");
        }

        ArrayList<String> options = new ArrayList<String>();
        options.add("play " + movie.getTitle());
        boolean favourite = currentUser.getFavouriteList().contains(movie);
        if (favourite) {
            options.add("remove " + movie.getTitle() + " from your favourite list");
        } else {
            options.add("add " + movie.getTitle() + "to your favourite list");
        }
        if (currentUser.isAdmin()) {
            options.add("update " + movie.getTitle());
            options.add("remove " + movie.getTitle());
        }

        int choice = Screen.choice(options);

        if (choice == 1) {
            movie.playMovie();
            int rating = Screen.enterInt("rating from 1 to 5");
            currentUser.addSeenMovie(movie, rating);
            menu();
        } else if (choice == 2 && favourite) {
            currentUser.removeFavouriteList(movie);
            menu();
        } else if (choice == 2 && !favourite) {
            currentUser.addToFavouriteList(movie);
            menu();
        } else if (choice == 3 && currentUser.isAdmin()) {
            updateMovie(movie);
            menu();
        } else if (choice == 4 && currentUser.isAdmin()) {
            database.removeMovie(movie);
        } else {
            Screen.incorrectInput("choice");
            movieMenu(movie);
        }

    }

    public static void updateMovie(Movie movie) {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Title");
        options.add("Production year");
        options.add("Genre");
        options.add("Characters");

        int choice = Screen.choice(options);

        if (choice == 1) {
            Screen.print("Current title: " + movie.getTitle());
            String newTitle = Screen.enter("the new title");
            movie.setTitle(newTitle);
            Screen.print("Title has been updated");
        } else if (choice == 2) {
            Screen.print("Current year of production: " + movie.getProductionYear());
            int newYear = Screen.enterInt("the new production year");
            movie.setProductionYear(newYear);
            Screen.print("Year of production has been updated");
        } else if (choice == 3) {
            Screen.print("Current genre/genres: " + movie.getGenre());
            String newGenre = Screen.enter("the new genre/genres (separated by a space)");
            movie.setGenre(newGenre);
            Screen.print("Genre has been updated");
        } else if (choice == 4) {
            Screen.print("Please select a character to update: ");
            int index = Screen.choice(Character.toString(movie.getCharacters()));
            Screen.print("Current character: " + movie.getCharacters().get(index));
            String input = Screen.enter("the new Character in the format of (actor as role)");
            String[] attributes = input.split(" as ", 2);
            movie.getCharacters().get(index).setActor(attributes[0]);
            movie.getCharacters().get(index).setRole(attributes[1]);
            Screen.print("Character has been updated");
        } else {
            Screen.incorrectInput("choice");
            updateMovie(movie);
        }
    }

}
