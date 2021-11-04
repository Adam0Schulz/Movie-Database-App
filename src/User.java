
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class User implements Serializable {

    // Attributes
    private String username;
    private String password;
    private ArrayList<Movie> favouriteList = new ArrayList<Movie>();
    private ArrayList<SeenMovie> seenMovies = new ArrayList<SeenMovie>();
    private boolean admin;

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.admin = false;
    }

    // This method is only executed once
    public void makeAdmin() {
        this.admin = true;
    }

    // Getters and setters
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<Movie> getFavouriteList() {
        return this.favouriteList;
    }

    public ArrayList<String> getFavouriteListTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        for (Movie movie : favouriteList) {
            titles.add(movie.getTitle());
        }
        return titles;
    }

    public void addToFavouriteList(Movie movie) {
        this.favouriteList.add(movie);
    }

    public void removeFavouriteList(Movie movie) {
        this.favouriteList.remove(movie);
    }

    public ArrayList<SeenMovie> getSeenMovies() {
        return this.seenMovies;
    }

    public void addSeenMovie(Movie movie, int rating) {
        SeenMovie seen = new SeenMovie(movie.getTitle(), movie.getProductionYear(), movie.getGenre(),
                movie.getCharacters(), new Date(), rating);

        this.seenMovies.add(seen);

    }

    public boolean isAdmin() {
        return this.admin;
    }

    public boolean passwordValidation(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

}
