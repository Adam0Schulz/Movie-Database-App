
import java.util.ArrayList;
import java.io.Serializable;

public class Database implements Serializable {

    // Attributes / database content
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    // Constructor
    public Database() {
    }

    // Methods
    // Getters
    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<String> getMovieTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        for (Movie movie : movies) {
            titles.add(movie.getTitle());
        }
        return titles;
    }

    // Additions
    public void addUser(User user) {
        users.add(user);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    // not using this but we should
    public Movie selectMovie(int index) {
        return movies.get(index);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    public User searchForUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<Movie> searchForMovieByTitle(String movieTitle) {
        ArrayList<Movie> array = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(movieTitle.toLowerCase())) {
                array.add(movie);
            }
        }
        return array;
    }

    public ArrayList<Movie> searchForMovieByYear(int year, boolean from) {
        ArrayList<Movie> array = new ArrayList<Movie>();
        if (from) {
            for (Movie movie : movies) {
                if (movie.getProductionYear() > year) {
                    array.add(movie);
                }
            }
            return array;
        } else {
            for (Movie movie : movies) {
                if (movie.getProductionYear() < year) {
                    array.add(movie);
                }
            }
            return array;
        }
    }

    public ArrayList<Movie> searchForMovieByGenre(String genre) {
        ArrayList<Movie> array = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                array.add(movie);
            }
        }
        return array;
    }

    public ArrayList<Movie> searchForMovieByActor(String actor) {
        ArrayList<Movie> array = new ArrayList<Movie>();

        for (Movie movie : movies) {
            Character character = movie.searchActor(actor);
            if (character != null) {
                array.add(movie);
            }

        }
        return array;
    }

    public User registration(String username) {
        String password = Screen.enter("your new password");
        this.addUser(new User(username, password));
        return this.searchForUser(username);
    }

    public void createMovie() {

        String title = Screen.enter("the new title");

        int year = Screen.enterInt("the new production year");

        String genre = Screen.enter("genre/genres separated by a space");

        ArrayList<Character> characters = new ArrayList<Character>();
        Screen.print(
                "Please enter characters one by one in this format (actor as role) and when you're done enter done: ");
        characters = Screen.characterInsertion(Screen.scanStr(), characters);

        Movie movie = new Movie(title, year, genre, characters);
        this.addMovie(movie);

        Screen.print("Movie has been added to the database");
        Screen.pause();
    }

}
