package Repository;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import Modele.Lehrer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class LehrerRepository extends JDBCRepository implements ICrudRepository<Lehrer>{

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

    @Override
    public Lehrer create(Lehrer obj) throws IOException, DasElementExistiertException, SQLException {

        this.startConnection();
        boolean wahr = false;
        try {
            resultSet = statement.executeQuery("SELECT idlehrer FROM lehrer");

            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("idlehrer");
                idList.add(id);
            }

            if(!idList.contains(obj.getLehrerID()))
            {
                wahr = true;
                String query = "INSERT INTO lehrer VALUES(?, ?, ?)";

                String vorname = obj.getVorname();
                String nachname = obj.getNachname();
                long id = obj.getLehrerID();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString (1, vorname);
                preparedStmt.setString (2, nachname);
                preparedStmt.setLong   (3, id);

                preparedStmt.execute();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.stopConnection();
        if(wahr)
            return obj;
        else
            throw new DasElementExistiertException("Der Lehrer existiert");
    }

    @Override
    public List<Lehrer> getAll() throws SQLException, IOException {

        List<Lehrer> list = new ArrayList<>();
        this.startConnection();
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lehrer");

            while(resultSet.next())
            {
                String vorname = resultSet.getString("Vorname");
                String nachname = resultSet.getString("Nachname");
                long id = resultSet.getLong("idlehrer");
                Lehrer lehrer = new Lehrer(vorname, nachname, id);
                list.add(lehrer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.stopConnection();
        if(list.size()>0)
            return list;
        return null;
    }

    @Override
    public Lehrer update(Lehrer obj) throws IOException, ListIsEmptyException {
        return null;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException {
        boolean wahr = false;
        this.startConnection();
        try {

            resultSet = statement.executeQuery("SELECT idlehrer FROM lehrer");

            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("idlehrer");
                idList.add(id);
            }

            if(!idList.contains(objID))
            {
                wahr = true;
                String query = "DELETE FROM lehrer WEHERE idlehrer = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong  (1, objID);

                preparedStmt.execute();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        this.stopConnection();

        if(wahr)
            return true;
        else
            throw new IllegalAccessException();

    }


    }

