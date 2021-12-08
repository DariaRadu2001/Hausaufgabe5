package Repository;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import Modele.Kurs;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KursRepository extends JDBCRepository implements ICrudRepository<Kurs>{

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * erledigt die Connexion mit der DB
     */
    void startConnection() throws IOException, SQLException {
        String url;
        String user;
        String password;
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\Hausaufgabe5\\target\\config.properties");
        Properties prop = new Properties();
        prop.load(input);
        url = prop.getProperty("db.url");
        user = prop.getProperty("db.user");
        password = prop.getProperty("db.password");
        System.out.println(url+ " "+user+ " "+password);
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    /**
     * stopt die Connexion mit der DB
     */
    void stopConnection() throws SQLException {
        this.connection.close();
    }

    public boolean existiertLehrer(Long id) throws SQLException, IOException {
        this.startConnection();
        try {
            resultSet = statement.executeQuery("SELECT idlehrer FROM lehrer");

            while (resultSet.next()) {
                long idLehrer = resultSet.getLong("idlehrer");
                if (idLehrer == id) {
                    this.stopConnection();
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.stopConnection();
        return false;
    }


    @Override
    public Kurs create(Kurs obj) throws IOException, DasElementExistiertException, SQLException {

        if(this.existiertLehrer(obj.getLehrer()))
        {
            this.startConnection();
            try {
                resultSet = statement.executeQuery("SELECT * FROM kurs");

                List<Long> list = new ArrayList<>();
                while (resultSet.next()) {
                    long idkurs = resultSet.getLong("idkurs");
                    list.add(idkurs);
                }

                if(!list.contains(obj.getID()))
                {
                    String query = "INSERT INTO kurs VALUES(?, ?, ?, ?, ?)";
                    long id = obj.getID();
                    String name = obj.getName();
                    long lehrer =  obj.getLehrer();
                    int anzahl = obj.getMaximaleAnzahlStudenten();
                    int ects = obj.getEcts();

                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setLong   (1, id);
                    preparedStmt.setString (2, name);
                    preparedStmt.setLong   (3, lehrer);
                    preparedStmt.setInt(4, anzahl);
                    preparedStmt.setInt(5, ects);


                    preparedStmt.execute();

                }
                else
                    throw new DasElementExistiertException("Das Kurs existiert");

            } catch (Exception e) {
                e.printStackTrace();
            }

            this.stopConnection();
            return obj;
        }
        else
            return null;
    }

    @Override
    public List<Kurs> getAll() {
        return null;
    }

    @Override
    public Kurs update(Kurs obj) throws IOException, ListIsEmptyException {
        return null;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException {
        return false;
    }
}
