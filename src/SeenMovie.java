import java.util.ArrayList;
import java.util.Date;

public class SeenMovie extends Movie {

    // Attributes
    private Date date;
    private double rating;

    // Constructor
    public SeenMovie(String title, int productionYear, String description, ArrayList<Character> characters, Date date,
            double rating) {
        super(title, productionYear, description, characters);
        this.date = date;
        this.rating = rating;
    }

    // Getters and setters
    public Date getDate() {
        return this.date;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
