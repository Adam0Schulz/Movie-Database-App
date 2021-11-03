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

    public ArrayList<String> getMovieTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        for (Movie movie : movies) {
            titles.add(movie.getTitle());
        }
        return titles;
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

    public ArrayList<ArrayList<Object>> searchForMovieByActor(String actor) {
        ArrayList<ArrayList<Object>> array = new ArrayList<ArrayList<Object>>();
        for (Movie movie : movies) {
            for (Character character : movie.getCharacters()) {
                if (character.getActor().toLowerCase().contains(actor.toLowerCase())) {
                    ArrayList<Object> subArray = new ArrayList<Object>();
                    subArray.add(movie);
                    subArray.add(character);
                    array.add(subArray);
                }
            }
        }
        return array;
    }

    @Override
    public String toString() {
        return "{" + " users='" + getUsers() + "'" + ", movies='" + getMovies() + "'" + "}";
    }

}
