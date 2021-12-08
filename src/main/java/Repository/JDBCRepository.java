package Repository;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class JDBCRepository{

    private String url;
    private String user;
    private String password;
    private Connection connection;

    /**
     * erledigt die Connexion mit der DB
     */
    void startConnection() throws IOException, SQLException {
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\Hausaufgabe5\\target\\config.properties");
        Properties prop = new Properties();
        prop.load(input);
        url = prop.getProperty("db.url");
        user = prop.getProperty("db.user");
        password = prop.getProperty("db.password");
        System.out.println(url+ " "+user+ " "+password);
    }

    /**
     * stopt die Connexion mit der DB
     */
    void stopConnection() throws SQLException {
        this.connection.close();
    }

    /**
     * sucht ein Objekt nach seiner ID
     *
     * @param id des Objektes
     * @return true, wenn das Objekt in deer DB ist, false andernfalls
     */
    boolean findOne(long id) {
        return false;
    }
}
