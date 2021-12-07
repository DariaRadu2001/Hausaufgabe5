import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class App1 {
    public static void main(String[] args) {
        try (OutputStream output = new FileOutputStream("C:\\Users\\User\\IdeaProjects\\Hausaufgabe5\\target\\config.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("db.url", "localhost");
            prop.setProperty("db.user", "root");
            prop.setProperty("db.password", "daria20");

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}

