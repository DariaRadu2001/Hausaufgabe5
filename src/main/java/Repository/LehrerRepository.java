package Repository;
import Exception.DasElementExistiertException;
import Modele.Lehrer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LehrerRepository implements ICrudRepository<Lehrer>{

    private Connection connection;
    private Statement statement;
    private DBConnection dbConnection;

    /**
     * schaut, ob ein Lehrer existiert nach seinem Id
     * @param id des Lehrers
     * @return true, wenn der Lehrer existiert, false anders falls
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public boolean findOne(long id) throws SQLException, IOException {

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idlehrer FROM lehrer");

            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                long id2 = resultSet.getLong("idlehrer");
                idList.add(id2);
            }

            connection.close();
            return idList.contains(id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * im DB wir in der Tabelle lehrer ein neuer Tupel hingelegt
     * @param obj, das Objekt die man hinlegt
     * @return das Objekt
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws DasElementExistiertException, das Objekt ist in der DB
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Lehrer create(Lehrer obj) throws IOException, DasElementExistiertException, SQLException {

        if(!this.findOne(obj.getLehrerID()))
        {

            try{
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();
                String query = "INSERT INTO lehrer VALUES(?, ?, ?)";

                String vornameLehrer = obj.getVorname();
                String nachnameLehrer = obj.getNachname();
                long id = obj.getLehrerID();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong   (1, id);
                preparedStmt.setString (2, vornameLehrer);
                preparedStmt.setString (3, nachnameLehrer);

                preparedStmt.execute();
                connection.close();
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

    /**
     * gibt alle Professoren
     * @return Liste mit allen Tuples aus der Tabelle lehrer
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public List<Lehrer> getAll() throws SQLException, IOException {

        List<Lehrer> list = new ArrayList<>();
        try{
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
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
        connection.close();
        if(list.size()>0)
            return list;
        return null;
    }

    /**
     * ändert die Attribute eines Tupels, indem man es nach seinem Id sucht
     * @param obj, das Objekt mit dem switch erledigt
     * @return das Objekt, anderes, falls null
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Lehrer update(Lehrer obj) throws IOException, SQLException {
        if(this.findOne(obj.getLehrerID())) {

            try {
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();
                String query = "UPDATE lehrer SET Vorname = ? , Nachname = ? WHERE idlehrer = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, obj.getVorname());
                preparedStmt.setString(2, obj.getNachname());
                preparedStmt.setLong(3, obj.getLehrerID());

                preparedStmt.execute();
                connection.close();
                return obj;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * löscht aus DB ein Lehrer
     * @param objID, das Objekt, das man löschen will
     * @return true, wenn der Lehrer existiert, false anders falls
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public boolean delete(Long objID) throws  IOException, SQLException {

        if(this.findOne(objID))
        {
            try{
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();
                String query = "DELETE FROM lehrer WEHERE idlehrer = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong  (1, objID);
                preparedStmt.execute();
                connection.close();
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

