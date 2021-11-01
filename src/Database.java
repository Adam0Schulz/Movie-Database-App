import java.util.ArrayList;
import java.io.Serializable;

public class Database implements Serializable {
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void listMovies() {
        for (int i = 1; i <= movies.size(); i++) {
            System.out.println(i + ": " + movies.get(i - 1).getTitle());
        }

    }

    public Movie selectMovie(int index) {
        return movies.get(index);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
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

    public Movie searchForMovie(String movieTitle) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(movieTitle)) {
                return movie;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "{" + " users='" + getUsers() + "'" + ", movies='" + getMovies() + "'" + "}";
    }

}
