import java.util.ArrayList;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private ArrayList<Movie> favouriteList = new ArrayList<Movie>();
    private ArrayList<Movie> seenMovies = new ArrayList<Movie>();
    private ArrayList<Movie> ratedMovies = new ArrayList<Movie>();
    private boolean admin;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.admin = false;
    }

    public void makeAdmin() {
        this.admin = true;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<Movie> getFavouriteList() {
        return this.favouriteList;
    }

    public void listFavourites() {
        for (int i = 1; i < favouriteList.size(); i++) {
            System.out.println(i + favouriteList.get(i - 1).getTitle());
        }

    }

    public void addToFavouriteList(Movie movie) {
        this.favouriteList.add(movie);
    }

    public void removeFavouriteList(Movie movie) {
        this.favouriteList.remove(movie);
    }

    public ArrayList<Movie> getSeenMovies() {
        return this.seenMovies;
    }

    public void addSeenMovie(Movie seenMovie) {
        this.seenMovies.add(seenMovie);
    }

    public ArrayList<Movie> getRatedMovies() {
        return this.ratedMovies;
    }

    public void addRatedMovie(Movie ratedMovie) {
        this.ratedMovies.add(ratedMovie);
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
