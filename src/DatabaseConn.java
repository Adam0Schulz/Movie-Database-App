
import java.io.*;
import java.util.ArrayList;

public abstract class DatabaseConn {

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
        characters.add(new Character("Charlie", "Mikey Day"));
        characters.add(new Character("Lori", "Dominique Swain"));
        characters.add(new Character("Gabriel", "Chris Kattan"));
        characters.add(new Character("Max", "Trevor Heins"));
        database.addMovie(new Movie("Totally Awesome", 2006, "comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Billie Joe Armstrong", "Billie Joe Armstrong"));
        characters.add(new Character("Tré Cool", "Tré Cool"));
        characters.add(new Character("Mike Dirnt", "Mike Dirnt"));
        characters.add(new Character("Jason Freese", "Jason Freese"));
        database.addMovie(new Movie("Green Day: Awesome As Fuck", 2011, "music", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Brandon", "Ben Diskin"));
        characters.add(new Character("News Reporter", "Jessica Gee-George"));
        characters.add(new Character("Gage", "Grant George"));
        characters.add(new Character("Art Department", "Traian Virgil Georgescu"));
        database.addMovie(
                new Movie("Team Hot Wheels: The Origin of Awesome!", 2014, "animation adventure family", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Awesome Lotus", "Lorraine Masterson"));
        characters.add(new Character("Morgan Lamour", "Joy Curtis"));
        characters.add(new Character("Stephanie", "Stephanie Jones"));
        characters.add(new Character("Chuck Tuna", "Dan Kopper"));
        database.addMovie(new Movie("Awesome Lotus", 1983, "action comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Noah Humphrey", "Noah Humphrey"));
        characters.add(new Character("Zane Little", "Zane Little"));
        characters.add(new Character("Joe Finley", "Joe Finley"));
        characters.add(new Character("Geo", "Chris Humphrey"));
        database.addMovie(new Movie("Planet Awesome", 2016, "comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Norman", "Tom McCamus"));
        characters.add(new Character("Erica", "Laurie Paton"));
        characters.add(new Character("Umberto", "Jacques Lussier"));
        characters.add(new Character("Septimus Fabius", "David Hemblen"));
        database.addMovie(new Movie("Norman's Awesome Experience", 1988, "comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Norman", "Tom McCamus"));
        characters.add(new Character("Erica", "Laurie Paton"));
        characters.add(new Character("Umberto", "Jacques Lussier"));
        characters.add(new Character("Septimus Fabius", "David Hemblen"));
        database.addMovie(new Movie("Norman's Awesome Experience", 1988, "comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Winston Sterzel", "Winston Sterzel"));
        characters.add(new Character("Naomi Wu", "Naomi Wu"));
        database.addMovie(new Movie("Stay Awesome, China!", 2019, "documentary", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Tim", "Zach Steffey"));
        characters.add(new Character("Dax", "Gerald Yelverton"));
        characters.add(new Character("Mils", "Jay Milnamow"));
        characters.add(new Character("Chris", "Thomas Beheler"));
        database.addMovie(new Movie("Awesome Movie", 2013, "comedy adventure romance", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Gary Eastwood", "Guy Edmonds"));
        characters.add(new Character("Mark Zegrab", "Matt Zeremes"));
        characters.add(new Character("Jack Simpson", "Simon Burke"));
        characters.add(new Character("Kim Devine", "Rob Carlton"));
        database.addMovie(new Movie("Super Awesome!", 2015, "comedy musical", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Khaleel Siddiqui", "Sunil Chaurasiyaa"));
        characters.add(new Character("Suneel", "Matt Zeremes"));
        characters.add(new Character("Suhasini Mulay", "Suhasini Mulay"));
        characters.add(new Character("Ghazal Siddhiqui", "Ambalika Sarkar"));
        database.addMovie(new Movie("Awesome Mausam!", 2016, "romance", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Sydney", "Jessica Jaffe"));
        characters.add(new Character("Prop Girl", "Orlea Mattson"));
        characters.add(new Character("Guy1", "Ehab Ali"));
        characters.add(new Character("Guy2", "Dean Hall"));
        database.addMovie(new Movie("Awesome Burger!", 2020, "short comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Peter", "Tyler Sopland"));
        characters.add(new Character("Maria", "Molly Jackson"));
        characters.add(new Character("Mark", "Isaac Parra-Azocar"));
        characters.add(new Character("Justine", "Talita Maia"));
        database.addMovie(new Movie("Cool, Awesome, and Desirable", 2021, "short comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Lennon", "Jessica McKenna"));
        characters.add(new Character("Gluko", "Fred Tatasciore"));
        characters.add(new Character("Fried Egg", "Jon Luke Thomas"));
        characters.add(new Character("Grandma", "Dana Snyder"));
        database.addMovie(new Movie("Little Big Awesome", 2016, "animation short adventure", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Fraser Agar", "Fraser Agar"));
        characters.add(new Character("Becky Blow", "Becky Blow"));
        characters.add(new Character("Baby Hank", "Baby Hank"));
        characters.add(new Character("Kyle Huinink", "Kyle Huinink"));
        database.addMovie(new Movie("Dark Souls Is AWESOME!", 2014, "comedy game-show", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Quinn", "Bee Avila"));
        characters.add(new Character("Ernie", "Nick Bell"));
        characters.add(new Character("Athena", "Samantha Bowling"));
        characters.add(new Character("Chad", "Travis Lincoln Cox"));
        database.addMovie(new Movie("Back to Awesome", 2015, "comedy drama", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Paul Brown", "Paul Brown"));
        characters.add(new Character("Cindy Shook", "Cindy Shook"));
        characters.add(new Character("Jon Hammond", "Jon Hammond"));
        characters.add(new Character("Delfino Ramos", "Delfino Ramos"));
        database.addMovie(new Movie("Awesome 80s", 2011, "comedy drama reality-tv", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Eirian Cohen", "Meleine Fox"));
        characters.add(new Character("Judge", "Wes Dolan"));
        characters.add(new Character("Dave Samuels", "David Samuels"));
        characters.add(new Character("Jared Cooper", "Rob Ireland"));
        database.addMovie(new Movie("Awesome Killer Audition", 2012, "thriller", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Steve Tom", "Steve Tom"));
        characters.add(new Character("Jim Carrey", "Jim Carrey"));
        characters.add(new Character("Jeff Daniels", "Jeff Daniels"));
        characters.add(new Character("Charles B. Wessler", "Charles B. Wessler"));
        database.addMovie(new Movie("That's Awesome! the Story of 'Dumb and Dumber", 2015, "documentary", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Jordan Raskopoulos", "Jordan Raskopoulos"));
        characters.add(new Character("Benny Davis", "Benny Davis"));
        characters.add(new Character("Jeff Daniels", "Jeff Daniels"));
        characters.add(new Character("Lee Naimo", "Lee Naimo"));
        database.addMovie(new Movie("The Axis of Awesome", 2011, "short comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Vidar", "Vince Major"));
        characters.add(new Character("Odin", "Schno Mozingo"));
        characters.add(new Character("Mistress Helen", "Jane Edwina Seymour"));
        characters.add(new Character("Liv", "Adele René"));
        database.addMovie(new Movie("Total Awesome Viking Power", 2015, "short comedy action", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Jazz", "Carlene Begnaud"));
        characters.add(new Character("Mercedes", "Jasmine Benitez"));
        characters.add(new Character("Rick Cataldo", "Rick Cataldo"));
        characters.add(new Character("Jennifer Cruz", "Jennifer Cruz"));
        database.addMovie(new Movie("WSU: The Awesome Challenge", 2009, "sport", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Dude", "Chris Wood"));
        characters.add(new Character("Lisa", "Sarah Michelle Gellar"));
        characters.add(new Character("Lyn", "Lena Headey"));
        characters.add(new Character("Skeletor", "Mark Hamill"));
        database.addMovie(new Movie("Do Something Awesome", 2017, "short", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Jamie Lissow", "Jamie Lissow"));
        database.addMovie(new Movie("Jamie Lissow: Something Awesome", 2019, "comedy", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("James Jones", "Jonah Ain"));
        characters.add(new Character("Muck", "Sophie Aldred"));
        characters.add(new Character("Victor", "David Bedella"));
        characters.add(new Character("Narrator", "Michael Brandon"));
        database.addMovie(new Movie("Awesome Adventures Vol. 4: Preschool Party Surprise", 2002, "animation cartoon",
                characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Announcer", "Bob Odenkirk"));
        characters.add(new Character("Dr. Steve Brule", "John C. Reilly"));
        characters.add(new Character("Richard Dunn", "Richard Dunn"));
        characters.add(new Character("David Liebe Hart", "David Liebe Hart"));
        database.addMovie(new Movie("Tim and Eric Awesome Show, Great Job!", 2007, "comedy music", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Announcer", "Bob Odenkirk"));
        characters.add(new Character("Dr. Steve Brule", "John C. Reilly"));
        characters.add(new Character("Richard Dunn", "Richard Dunn"));
        characters.add(new Character("David Liebe Hart", "David Liebe Hart"));
        database.addMovie(new Movie("An Awesome Book of Love", 2013, "short drama music", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Announcer", "Bob Odenkirk"));
        characters.add(new Character("Dr. Steve Brule", "John C. Reilly"));
        characters.add(new Character("Richard Dunn", "Richard Dunn"));
        characters.add(new Character("David Liebe Hart", "David Liebe Hart"));
        database.addMovie(new Movie("The Amazing Adventures of Awesome", 2020, "short animation family", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Andy Colby", "Randy Josselyn"));
        characters.add(new Character("Bonny Colby", "Jessica Puscas"));
        characters.add(new Character("Mrs.Colby", "Dianne Kay"));
        characters.add(new Character("Video Store Clerk", "John Bluto"));
        database.addMovie(new Movie("Andy Colby's Incredible Adventure", 1988, "adventure fantasy sci-fi", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Announcer", "Mr. Lawrence"));
        characters.add(new Character("The MC Bat Commander", "Christian Jacobs"));
        characters.add(new Character("Jimmy the Robot", "James Briggs"));
        characters.add(new Character("Crash McLarson", "Chad Larson"));
        database.addMovie(new Movie("The Aquabats! Seriously Awesome!", 2012, "adventure comedy family", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Dorothy", "Dorothy Fairbairn"));
        characters.add(new Character("Voice", "Allison Khoury"));
        database.addMovie(
                new Movie("11 Life Lessons from an Awesome Old Dyke", 2015, "documentary short biography", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Splinter", "Peter Renaday"));
        characters.add(new Character("The MC Bat Commander", "Christian Jacobs"));
        characters.add(new Character("Jimmy the Robot", "James Briggs"));
        characters.add(new Character("Crash McLarson", "Chad Larson"));
        database.addMovie(
                new Movie("Teenage Mutant Ninja Turtles: The Turtles Awesome Easter", 1991, "animation", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Muno", "Adam Deibert"));
        characters.add(new Character("Brobee", "Amos Watene"));
        characters.add(new Character("Plex", "Christian Jacobs"));
        characters.add(new Character("Toodee", "Erin Pearce"));
        database.addMovie(new Movie("Yo Gabba Gabba: Very Awesome Holiday Show", 2014, "family", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Kai Hansen", "Kai Hansen"));
        characters.add(new Character("Henjo Richter", "Henjo Richter"));
        characters.add(new Character("Dirk Schlächter", "Dirk Schlächter"));
        characters.add(new Character("Dan Zimmermann", "Dan Zimmermann"));
        database.addMovie(new Movie("Gamma Ray: Hell Yeah!!! The Awesome Foursome - Live in Montreal", 2008, "music",
                characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Taat Pribadi", "Gading Marten"));
        characters.add(new Character("Nelson Manulang", "Boris Bokir"));
        characters.add(new Character("Ipang", "Kevin Ardillova"));
        characters.add(new Character("Gagah", "Ibnu Jamil"));
        database.addMovie(new Movie("Crazy Awesome Teachers", 2020, "comedy drama", characters));

        characters = new ArrayList<Character>();
        characters.add(new Character("Luka Bursac", "Luka Bursac"));
        characters.add(new Character("Vladimir Gvojic", "Vladimir Gvojic"));
        characters.add(new Character("Milan Maric", "Milan Maric"));
        characters.add(new Character("Andrej Veljanovski", "Andrej Veljanovski"));
        database.addMovie(new Movie("You Look Awesome When I'm Fucked Up", 2013, "short drama", characters));

        // Creates the admin and add him/her to the database
        User admin = new User("admin", "Admin123");
        admin.makeAdmin();
        database.addUser(admin);

        // Save the newly created database
        save(database);
    }
}
