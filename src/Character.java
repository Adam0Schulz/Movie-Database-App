public class Character {

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
}
