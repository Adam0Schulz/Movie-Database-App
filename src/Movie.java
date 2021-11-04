import java.util.ArrayList;
import java.io.Serializable;

public class Movie implements Serializable {

    // Attributes
    private String title;
    private int productionYear;
    private String genre;
    private ArrayList<Character> characters = new ArrayList<Character>();

    // Constructors
    public Movie(String title, int productionYear, String genre, ArrayList<Character> characters) {
        this.title = title;
        this.productionYear = productionYear;
        this.genre = genre;
        this.characters = characters;
    }

    // Getters and Setters
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProductionYear() {
        return this.productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public ArrayList<Character> getCharacters() {
        return this.characters;
    }

    public void listCharacters() {
        for (int i = 1; i < characters.size(); i++) {
            Screen.print(i + ": " + characters.get(i - 1));
        }
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public void playMovie() {
        for (Character character : characters) {
            Screen.print(character.getActor() + " - " + character.getRole());
        }

    }

    public Character searchActor(String actor) {
        for (Character character : characters) {
            if (character.getActor().toLowerCase().contains(actor.toLowerCase())) {

                return character;
            }
        }
        return null;

    }

    public static ArrayList<String> toString(ArrayList<Movie> array, String additAtt, String actor) {
        ArrayList<String> result = new ArrayList<>();
        for (Movie movie : array) {
            String item = movie.getTitle() + ", ";

            if (additAtt.equals("year")) {
                item += ", " + movie.getProductionYear();
            } else if (additAtt.equals("genre")) {
                item += ", " + movie.getGenre();
            } else if (additAtt.equals("char")) {
                item += ", " + movie.searchActor(actor);
            }
            result.add(item);
        }
        return result;

    }

}
