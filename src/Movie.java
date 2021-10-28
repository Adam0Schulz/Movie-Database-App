import java.util.ArrayList;

public class Movie {

    // Attributes
    private String title;
    private int productionYear;
    private String description;
    private ArrayList<Character> characters = new ArrayList<Character>();

    // Constructors
    public Movie(String title, int productionYear, String description, ArrayList<Character> characters) {
        this.title = title;
        this.productionYear = productionYear;
        this.description = description;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Character> getCharacters() {
        return this.characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public void playMovie() {
        for (Character character : characters) {
            System.out.println(character.getActor() + " - " + character.getRole());
        }

    }

}
