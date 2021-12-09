package Repository;
import Modele.Kurs;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KursRepository implements ICrudRepository<Kurs> {

    private Connection connection;
    private Statement statement;
    private DBConnection dbConnection;


    /**
     * überprüft, ob ein Lehrer existiert
     * @param id nachdem man sucht
     * @return true, wenn der Student existiert, false anders falls
     */
    public boolean existiertLehrer(Long id) {

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idlehrer FROM lehrer");

            while (resultSet.next()) {
                long idLehrer = resultSet.getLong("idlehrer");
                if (idLehrer == id) {
                    connection.close();
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ein neuer Tupel wird fur Tabelle Kurs erledigt
     * @param obj, das Objekt die man hinlegt
     * @return das Objekt, wenn man es erledigen kann, anders, falls null
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Kurs create(Kurs obj) throws IOException, SQLException {

        if (this.existiertLehrer(obj.getLehrer())) {
            if(!this.findOne(obj.getID()))
            {
                try {
                    dbConnection = new DBConnection();
                    connection = dbConnection.startConnection();
                    String query = "INSERT INTO kurs VALUES(?, ?, ?, ?, ?)";
                    long id = obj.getID();
                    String name = obj.getName();
                    long lehrer = obj.getLehrer();
                    int anzahl = obj.getMaximaleAnzahlStudenten();
                    int ects = obj.getEcts();

                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setLong(1, id);
                    preparedStmt.setString(2, name);
                    preparedStmt.setLong(3, lehrer);
                    preparedStmt.setInt(4, anzahl);
                    preparedStmt.setInt(5, ects);


                    preparedStmt.execute();
                    connection.close();
                    return obj;


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
        return null;
    }

    /**
     * gibt alle Tupels aus der Tabelle Kurs
     * @return die Liste
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public List<Kurs> getAll() throws SQLException, IOException {
        List<Kurs> list = new ArrayList<>();

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kurs");

            while (resultSet.next()) {
                long id = resultSet.getLong("idkurs");
                String name = resultSet.getString("Name");
                long idLehrer = resultSet.getLong("idlehrer");
                int anzahl = resultSet.getInt("maxAnzahl");
                int ects = resultSet.getInt("ECTS");
                Kurs kurs = new Kurs(id, name, idLehrer, anzahl, ects);
                list.add(kurs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        if (list.size() > 0)
            return list;
        return null;
    }

    /**
     * ander die Attribute eines Tupels
     * @param obj, das Objekt mit dem switch erledigt
     * @return das Objekt, wenn man es erledigen kann, anders, falls null
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Kurs update(Kurs obj) throws IOException, SQLException {

        if (this.findOne(obj.getID()) && this.existiertLehrer(obj.getLehrer())) {
            try {
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();

                String query = "UPDATE kurs SET Name = ? , idlehrer = ?, maxAnzahl = ?, ECTS = ? WHERE idkurs = ?";

                long id = obj.getID();
                String name = obj.getName();
                long idLehrer = obj.getLehrer();
                int anzahl = obj.getMaximaleAnzahlStudenten();
                int ects = obj.getEcts();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, name);
                preparedStmt.setLong(2, idLehrer);
                preparedStmt.setInt(3, anzahl);
                preparedStmt.setInt(4, ects);
                preparedStmt.setLong(5, id);

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
     * löscht das Tupels aus Tabelle kurs
     * @param objID, das Objekt, das man löschen will
     * @return true, wenn der Kurs existiert, false anders falls
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public boolean delete(Long objID) throws IOException, SQLException {
        if (this.findOne(objID)) {
            try {
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();
                String query = "DELETE FROM kurs WHERE idkurs = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong(1, objID);

                preparedStmt.execute();
                connection.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * sucht, ob ein Kurs in der DB ist nach seiner ID
     * @param id des Kurses
     * @return true, wenn der Student existiert, false anders falls
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public boolean findOne(long id) throws SQLException, IOException {

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kurs");

            List<Long> list = new ArrayList<>();
            while (resultSet.next()) {
                long idKurs = resultSet.getLong("idkurs");
                list.add(idKurs);
            }
            connection.close();
            return list.contains(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ander die ECTS eines Kurses
     * @param id des Kurses
     * @param ects die neue ECTS
     * @return die alte ECTS
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public int andernECTS(long id, int ects) throws SQLException, IOException {

        if (this.findOne(id)) {
            try {
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();
                String query1 = "SELECT ECTS FROM kurs WHERE idkurs = ?";
                PreparedStatement preparedStmt1 = connection.prepareStatement(query1);
                preparedStmt1.setLong(1, id);
                ResultSet resultSet = preparedStmt1.executeQuery();
                int alteECTS = 0;
                while (resultSet.next())
                    alteECTS = resultSet.getInt("ECTS");

                String query = "UPDATE kurs SET ECTS = ? WHERE idkurs = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, ects);
                preparedStmt.setLong(2, id);

                preparedStmt.execute();
                connection.close();
                return alteECTS;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

    /**
     * gibt der Anzahl der Studenten angemeldet bei einem Kurs
     * @param id des Kurses
     * @return Anzahl angemeldeten Studenten
     */
    public int getAnzahlStudenten(long id) {

        int ct = 0;
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM enrolled");
            while (resultSet.next()) {
                if (resultSet.getLong("idkurs") == id)
                    ct++;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ct;
    }

    /**
     * Schaut welche Kurse freie Platzen haben
     * @return Liste Kurse mit freien Platzen
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public List<Kurs> kurseFreiePlatze() throws SQLException, IOException {
        List<Kurs> alleKurse = this.getAll();
        List<Kurs> freiePlatze = new ArrayList<>();
        for (Kurs kurs : alleKurse) {
            if ((kurs.getMaximaleAnzahlStudenten() - this.getAnzahlStudenten(kurs.getID())) != 0)
                freiePlatze.add(kurs);
        }
        return freiePlatze;
    }

    /**
     * filtert die Liste nach die Anzahl von ECTS(die Kurse die > 5 ECTS haben)
     * @return die gefilterte Liste
     */
    public List<Kurs> filter() throws SQLException, IOException {
        List<Kurs> liste = this.getAll();
        return liste.stream()
                .filter(kurs -> kurs.getEcts() > 5).toList();
    }

    /**
     * sortiert die Liste in steigender Reihenfolge nach Anzahl der ECTS
     */
    public List<Kurs> sort() throws SQLException, IOException {
        List<Kurs> liste = this.getAll();
        liste.sort(Kurs::compareTo);
        return liste;
    }

    /**
     * gibt einen Kurs nach dem Id
     * @param id des Kurses
     * @return der Kurs
     */
    public Kurs getKursNachId(long id) {

        Kurs kurs = null;
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            String query = "SELECT * FROM kurs WHERE idkurs = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, id);
            ResultSet resultSet = preparedStmt.executeQuery();

            if(resultSet.next())
            {
                String name = resultSet.getString("Name");
                long idLehrer = resultSet.getLong("idlehrer");
                int anzahl = resultSet.getInt("maxAnzahl");
                int ects = resultSet.getInt("ECTS");
                kurs = new Kurs(id, name, idLehrer, anzahl, ects);
            }

            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return kurs;
    }

}
