
import java.util.ArrayList;
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
        loginMenu();
    }

    public static void loginMenu() {

        String username = Screen.enter("your username");
        currentUser = database.searchForUser(username);

        if (currentUser == null) {
            Screen.noAccount();
            registration(username);
            mainMenu();
        } else {
            String password = Screen.enter("your password");
            if (currentUser.passwordValidation(password)) {
                mainMenu();
            } else {
                Screen.incorrectInput("password");
                loginMenu();
            }

        }

    }

    public static void registration(String username) {
        String password = Screen.enter("your password");
        database.addUser(new User(username, password));
        currentUser = database.searchForUser(username);
    }

    public static void mainMenu() {

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

            Movie movie = (Movie) Screen.chooseListItem(database.getMovies(), "");
            movieMenu(movie);

        } else if (choice == 2) {

            searchMenu();

        } else if (choice == 3) {

            Movie movie = (Movie) Screen.chooseListItem(currentUser.getFavouriteList(), "");
            movieMenu(movie);

        } else if (choice == 4 && currentUser.isAdmin()) {

            Movie movie = (Movie) Screen.chooseListItem(currentUser.getSeenMovies(), "");
            movieMenu(movie);

        } else {

            Screen.incorrectInput("choice");
            mainMenu();

        }
    }

    public static void searchMenu() {
        Movie movie = null;

        ArrayList<String> options = new ArrayList<String>();
        options.add("Title");
        options.add("Production year");
        options.add("Genre");
        options.add("Actor");

        int choice = Screen.choice(options);
        if (choice == 1) {
            String keyword = Screen.enter("title of the movie");
            ArrayList<Movie> selectedMovies = database.searchForMovieByTitle(keyword);
            movie = (Movie) Screen.chooseListItem(selectedMovies, "");
        } else if (choice == 2) {
            ArrayList<String> subOptions = new ArrayList<String>();
            subOptions.add("Search for movies produced after a certain year");
            subOptions.add("Seach for movies produced before a certain year");

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
        mainMenu();
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
            mainMenu();
        } else if (choice == 2 && favourite) {
            currentUser.removeFavouriteList(movie);
            mainMenu();
        } else if (choice == 2 && !favourite) {
            currentUser.addToFavouriteList(movie);
            mainMenu();
        } else if (choice == 3 && currentUser.isAdmin()) {
            updateMovie(movie);
            mainMenu();
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
