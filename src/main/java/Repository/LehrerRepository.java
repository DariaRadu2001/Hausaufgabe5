package Repository;
import Exception.DasElementExistiertException;
import Modele.Lehrer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class LehrerRepository implements ICrudRepository<Lehrer>{

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * erledigt die Connexion mit der DB
     */
    private void startConnection() throws IOException, SQLException {
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
    private void stopConnection() throws SQLException {
        this.connection.close();
    }

    public boolean findOne(long id) throws SQLException, IOException {
        this.startConnection();
        try {
            resultSet = statement.executeQuery("SELECT idlehrer FROM lehrer");

            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                long id2 = resultSet.getLong("idlehrer");
                idList.add(id2);
            }

            return idList.contains(id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Lehrer create(Lehrer obj) throws IOException, DasElementExistiertException, SQLException {

        if(!this.findOne(obj.getLehrerID()))
        {
            this.startConnection();
            try{
                String query = "INSERT INTO lehrer VALUES(?, ?, ?)";

                String vorname = obj.getVorname();
                String nachname = obj.getNachname();
                long id = obj.getLehrerID();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString (1, vorname);
                preparedStmt.setString (2, nachname);
                preparedStmt.setLong   (3, id);

                preparedStmt.execute();
                this.startConnection();
                return obj;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
            throw new DasElementExistiertException("Der Lehrer existiert");

        return null;
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
    public Lehrer update(Lehrer obj) throws IOException, SQLException {
        if(this.findOne(obj.getLehrerID())) {
            this.startConnection();

            try {
                String query = "UPDATE lehrer SET Vorname = ? , Nachname = ? WHERE idlehrer = ?";

                String vorname = obj.getVorname();
                String nachname = obj.getNachname();
                long id = obj.getLehrerID();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, vorname);
                preparedStmt.setString(2, nachname);
                preparedStmt.setLong(3, id);

                preparedStmt.execute();
                this.stopConnection();
                return obj;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long objID) throws  IOException, SQLException {

        if(this.findOne(objID))
        {
            this.startConnection();
            try{
                String query = "DELETE FROM lehrer WEHERE idlehrer = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong  (1, objID);
                preparedStmt.execute();
                this.stopConnection();
                return true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }


}

