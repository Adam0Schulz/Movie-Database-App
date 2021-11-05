
import java.io.*;
import java.util.ArrayList;

public class DatabaseConn {

    public static void main(String[] args) {
        delete();
        initialCreation();
    }

    public static void save(Database database) {
        try {
            FileOutputStream fileOut = new FileOutputStream("database.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(database);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in database.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Database load(String file) {
        Database database = null;
        try {
            FileInputStream filein = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(filein);
            database = (Database) in.readObject();
            in.close();
            filein.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        return database;

    }

    public static void delete() {
        File myObj = new File("database.ser");
        if (myObj.exists() && !myObj.isDirectory()) {
            if (myObj.delete()) {
                Screen.print("Deleted the file: " + myObj.getName());
            } else {
                Screen.print("Failed to delete the file.");
            }
        }
    }

    public static void initialCreation() {
        // Creates a database object
        Database database = new Database();

        // Manual addition of the initial movies
        ArrayList<Character> characters = new ArrayList<Character>();
        characters.add(new Character("Teddy Adams", "Allen C. Gardner"));
        characters.add(new Character("Lloyd Gibbard", "Drew Smith"));
        characters.add(new Character("Hailey Emerson", "Hayden Blane"));
        characters.add(new Character("Lisa Lane", "Alexis Boozer Sterling"));
        database.addMovie(new Movie("Being Awesome", 2014, "drama", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Stephen", "Stephen Dypiangco"));
        characters.add(new Character("Patrick", "Patrick Epino"));
        characters.add(new Character("Tamlyn", "Tamlyn Tomito"));
        characters.add(new Character("Al", "Al Leong"));
        database.addMovie(new Movie("Awesome Asian Bad Guys", 2014, "comedy action", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Tim", "Zach Steffey"));
        characters.add(new Character("Dax", "Gerald Yelverton"));
        characters.add(new Character("Mils", "Jay Milnamow"));
        characters.add(new Character("Chris", "Thomas Beheler"));
        database.addMovie(new Movie("Awesome Movie", 2013, "comedy romace", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Gary Eastwood", "Guy Edmonds"));
        characters.add(new Character("Mark Zegrab", "Matt Zeremes"));
        characters.add(new Character("Jack Simpson", "Sion Burke"));
        characters.add(new Character("Kim Devine", "Rob Carlton"));
        database.addMovie(new Movie("Super Awesome!", 2015, "comedy musical", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Awesomest Maximus", "Will Sasso"));
        characters.add(new Character("Hottessa", "Kristanna Loken"));
        characters.add(new Character("Princess Ellen", "Sophie Monk"));
        characters.add(new Character("King Erotic", "Khary Payton"));
        database.addMovie(new Movie("The Legend of Awesomest Maximus", 2011, "action comedy", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Taat Pribadi", "Gading Marten"));
        characters.add(new Character("Nelson Manulang", "Boris Bokir"));
        characters.add(new Character("Ipang", "Kevin Ardillova"));
        characters.add(new Character("Gagah", "Ibnu Jamil"));
        database.addMovie(new Movie("Crazy Awesome Teachers", 2020, "comedy drama", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Slash Van-Zucoson", "Matt Tabor"));
        characters.add(new Character("Tiffany Daniels", "Justine Lesourd"));
        characters.add(new Character("Tanner Z", "David Adler"));
        characters.add(new Character("Mr. Daniels", "Chad Barnt"));
        database.addMovie(new Movie("Awesometown", 2015, "comedy", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Quinn", "Bee Avila"));
        characters.add(new Character("Athena", "Samantha Bowling"));
        characters.add(new Character("Chad", "Travis Lincold Cox"));
        characters.add(new Character("Ernie", "Nick Bell"));
        database.addMovie(new Movie("Back to Awesome", 2015, "comedy drama", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Quinn", "Bee Avila"));
        characters.add(new Character("Athena", "Samantha Bowling"));
        characters.add(new Character("Chad", "Travis Lincold Cox"));
        characters.add(new Character("Ernie", "Nick Bell"));
        database.addMovie(new Movie("This Is Gonna Be Awesome", 2015, "comedy drama", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Queen Jira", "Felissa Rose"));
        characters.add(new Character("Big Boss Jira", "Creep Creepersin"));
        characters.add(new Character("Man Ruin", "Natalie Shaheen"));
        characters.add(new Character("Kix", "Tara Alexis"));
        database.addMovie(new Movie("Awesome Girl Gang Street Fighter", 2013, "action comedy", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Khaleel Siddiqui", "Vaarssh Bhatnagar"));
        characters.add(new Character("Suneel", "Sunil Chaurasiyaa"));
        characters.add(new Character("Ghazal Siddhiqui", "Ambalika Sarkar"));
        characters.add(new Character("Rahul Sharma", "Rahul Romy Sharma"));
        database.addMovie(new Movie("Awesome Mausam", 2016, "romance", characters));
        characters = new ArrayList<Character>();
        characters.add(new Character("Morgan Lamour", "Joy Curtis"));
        characters.add(new Character("Shephanie", "Stephanie Jones"));
        characters.add(new Character("Chuck Tuna", "Dan Kopper"));
        characters.add(new Character("Fashion Designer", "Pat Mahoney"));
        database.addMovie(new Movie("Awesome Lotus", 1983, "action comedy", characters));

        // Creates the admin and add him/her to the database
        User admin = new User("admin", "Admin123");
        admin.makeAdmin();
        database.addUser(admin);

        // Save the newly created database
        save(database);
    }
}
