import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class App1 {
    /**
     * von hier nimmt man die Daten fur die Connexion
     */
    public static void main(String[] args) {
        try (OutputStream output = new FileOutputStream("C:\\Users\\User\\IdeaProjects\\Hausaufgabe5\\target\\config.properties")) {

            Properties prop = new Properties();

            prop.setProperty("db.url", "jdbc:mysql://127.0.0.1:3306/labor5");
            prop.setProperty("db.user", "root");
            prop.setProperty("db.password", "daria20");

            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}

