
import java.io.Serializable;
import java.util.ArrayList;

public class Character implements Serializable {

    // Attributes
    private String role;
    private String actor;

    // Constructor
    public Character(String role, String actor) {
        this.role = role;
        this.actor = actor;
    }

    // Getters and Setters
    public void setRole(String role) {
        this.role = role;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getRole() {
        return role;
    }

    public String getActor() {
        return actor;
    }

    public String toString() {
        return actor + " as " + role;
    }

    // Static method
    public static ArrayList<String> toString(ArrayList<Character> array) {
        ArrayList<String> characters = new ArrayList<>();
        for (Character character : array) {
            characters.add(character.toString());
        }
        return characters;
    }

}
