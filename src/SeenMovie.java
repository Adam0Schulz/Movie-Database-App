
import java.util.ArrayList;
import java.util.Date;

public class SeenMovie extends Movie {

    // Attributes
    private Date date;
    private int rating;

    // Constructor
    public SeenMovie(String title, int productionYear, String genre, ArrayList<Character> characters, Date date,
            int rating) {
        super(title, productionYear, genre, characters);
        this.date = date;
        this.rating = rating;
    }

    // Getters and setters
    public Date getDate() {
        return this.date;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Static method
    public static ArrayList<String> toString(ArrayList<SeenMovie> array) {
        ArrayList<String> seenMoviesArray = new ArrayList<String>();
        for (SeenMovie seenMovie : array) {
            seenMoviesArray
                    .add(seenMovie.getTitle() + ", " + seenMovie.getDate() + ", " + seenMovie.getRating() + "/5 stars");
        }
        return seenMoviesArray;
    }

}
