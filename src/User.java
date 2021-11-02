import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private ArrayList<Movie> favouriteList = new ArrayList<Movie>();
    private ArrayList<SeenMovie> seenMovies = new ArrayList<SeenMovie>();
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
        for (int i = 1; i <= favouriteList.size(); i++) {
            System.out.println(i + ": " + favouriteList.get(i - 1).getTitle());
        }

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

    public void listSeenMovies() {
        for (int i = 1; i <= seenMovies.size(); i++) {
            System.out.println(i + ": " + seenMovies.get(i - 1).getTitle() + " - " + seenMovies.get(i - 1).getDate()
                    + " - " + seenMovies.get(i - 1).getRating());
        }
    }

    public void addSeenMovie(Movie movie, int rating) {
        this.seenMovies.add(new SeenMovie(movie.getTitle(), movie.getProductionYear(), movie.getGenre(),
                movie.getCharacters(), new Date(), rating));

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
