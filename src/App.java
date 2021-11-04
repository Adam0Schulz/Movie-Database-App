
import java.util.ArrayList;
import java.io.*;

public class App implements Serializable {

    private static Database database = DatabaseConn.load("database.ser");
    private static User currentUser;

    public static Database getDatabase() {
        return database;
    }

    public static void main(String[] args) throws Exception {

        start();

    }

    public static void start() {
        Screen.welcome();
        loginMenu();
    }

    public static void loginMenu() {

        String username = Screen.enter("your username / create your username");
        currentUser = database.searchForUser(username);

        if (currentUser == null) {
            Screen.noAccount();

            currentUser = database.registration(username);

        } else {
            String password = Screen.enter("your password");
            boolean passwordValid = currentUser.passwordValidation(password);

            if (!passwordValid) {
                Screen.incorrectInput("password");
                loginMenu();
            }

        }
        mainMenu();
    }

    public static void mainMenu() {
        Screen.welcome();

        // Creates options for menu
        ArrayList<String> options = new ArrayList<String>();
        options.add("List all the movies");
        options.add("Search for a movie");
        options.add("List all of your favourite movies");
        options.add("See your history");
        if (currentUser.isAdmin()) {
            options.add("create a movie");
        }
        // Displays options and stores the choice input
        int choice = Screen.choice(options);

        // Creates movie object to pass to movieMenu
        Movie movie = null;

        if (choice == 1) {

            ArrayList<Movie> selectionList = database.getMovies();
            movie = (Movie) Screen.chooseListItem(selectionList, "");

        } else if (choice == 2) {

            searchMenu();

        } else if (choice == 3) {

            ArrayList<Movie> selectionList = currentUser.getFavouriteList();
            movie = (Movie) Screen.chooseListItem(selectionList, "");

        } else if (choice == 4) {

            ArrayList<SeenMovie> selectionList = currentUser.getSeenMovies();
            movie = (Movie) Screen.chooseListItem(selectionList, "");

        } else if (choice == 5 && currentUser.isAdmin()) {

            database.createMovie();
            mainMenu();

        } else {

            Screen.incorrectInput("choice");
            mainMenu();

        }
        movieMenu(movie);
    }

    public static void searchMenu() {
        Screen.welcome();

        // Creates options for attributes to search for
        ArrayList<String> options = new ArrayList<String>();
        options.add("Title");
        options.add("Production year");
        options.add("Genre");
        options.add("Actor");

        // Displays options and stores the choice input
        int choice = Screen.choice(options);

        // Creates movie object to pass to movie menu
        Movie movie = null;

        if (choice == 1) {

            String keyword = Screen.enter("title of the movie");
            ArrayList<Movie> selectedMovies = database.searchForMovieByTitle(keyword);
            movie = (Movie) Screen.chooseListItem(selectedMovies, "");

        } else if (choice == 2) {

            // Creates options for time period related to the year
            ArrayList<String> subOptions = new ArrayList<String>();
            subOptions.add("Search for movies produced after a certain year");
            subOptions.add("Seach for movies produced before a certain year");

            // Displays options and stores the choice input
            int subChoice = Screen.choice(subOptions);

            int keyword = Screen.enterInt("the year of production");

            ArrayList<Movie> selectedMovies;

            if (subChoice == 1) {

                selectedMovies = database.searchForMovieByYear(keyword, true);
                movie = (Movie) Screen.chooseListItem(selectedMovies, "year - " + keyword);

            } else if (subChoice == 2) {

                selectedMovies = database.searchForMovieByYear(keyword, false);
                movie = (Movie) Screen.chooseListItem(selectedMovies, "year - " + keyword);

            } else {
                Screen.incorrectInput("choice");
                searchMenu();
            }

        } else if (choice == 3) {

            String keyword = Screen.enter("the movie genre");
            ArrayList<Movie> selectedMovies = database.searchForMovieByGenre(keyword);
            movie = (Movie) Screen.chooseListItem(selectedMovies, "genre - " + keyword);

        } else if (choice == 4) {

            String keyword = Screen.enter("the actor/actress");
            ArrayList<ArrayList<Object>> selectedMovies = database.searchForMovieByActor(keyword);
            movie = (Movie) Screen.chooseListItem(selectedMovies, "actor - " + keyword);

        } else {
            Screen.incorrectInput("choice");
            searchMenu();
        }
        movieMenu(movie);

    }

    public static void movieMenu(Movie movie) {
        Screen.welcome();

        // Makes sure that you can not use movie menu without selecting a movie first
        if (movie == null) {
            mainMenu();
        }

        // Checks if the selected movie is in users favourite list
        boolean favourite = currentUser.getFavouriteList().contains(movie);

        // Creates options for the menu
        ArrayList<String> options = new ArrayList<String>();
        options.add("play " + movie.getTitle());

        if (favourite) {
            options.add("remove " + movie.getTitle() + " from your favourite list");
        } else {
            options.add("add " + movie.getTitle() + "to your favourite list");
        }
        if (currentUser.isAdmin()) {
            options.add("update " + movie.getTitle());
            options.add("remove " + movie.getTitle());
        }

        // Displays options and stores the choice in input
        int choice = Screen.choice(options);

        if (choice == 1) {

            movie.playMovie();
            int rating = Screen.enterInt("rating from 1 to 5");
            currentUser.addSeenMovie(movie, rating);

        } else if (choice == 2 && favourite) {

            currentUser.removeFavouriteList(movie);

        } else if (choice == 2 && !favourite) {

            currentUser.addToFavouriteList(movie);

        } else if (choice == 3 && currentUser.isAdmin()) {

            updateMovie(movie);

        } else if (choice == 4 && currentUser.isAdmin()) {

            database.removeMovie(movie);

        } else {
            Screen.incorrectInput("choice");
            movieMenu(movie);
        }
        mainMenu();

    }

    public static void updateMovie(Movie movie) {

        // Creates options for attributes that can be updated
        ArrayList<String> options = new ArrayList<String>();
        options.add("Title");
        options.add("Production year");
        options.add("Genre");
        options.add("Characters");

        // Displays options and stores the choice input
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

            // Displays characters and stores the choice int
            int subChoice = Screen.choice(Character.toString(movie.getCharacters()));

            Screen.print("Current character: " + movie.getCharacters().get(subChoice));

            String input = Screen.enter("the new Character in the format of (actor as role)");
            String[] attributes = input.split(" as ", 2);
            movie.getCharacters().get(subChoice).setActor(attributes[0]);
            movie.getCharacters().get(subChoice).setRole(attributes[1]);

            Screen.print("Character has been updated");

        } else {
            Screen.incorrectInput("choice");
            Screen.pause();
            updateMovie(movie);
        }

        Screen.pause();
    }

}
