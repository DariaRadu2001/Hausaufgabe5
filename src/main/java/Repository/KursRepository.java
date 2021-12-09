package Repository;
import Exception.DasElementExistiertException;
import Modele.Kurs;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KursRepository implements ICrudRepository<Kurs> {

    private Connection connection;
    private Statement statement;
    private DBConnection dbConnection;


    public boolean existiertLehrer(Long id) throws SQLException, IOException {

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

    @Override
    public boolean delete(Long objID) throws IOException, SQLException {
        if (this.findOne(objID)) {
            try {
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();
                String query = "DELETE FROM kurs WEHERE idkurs = ?";
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

    public int getAnzahlStudenten(long id) throws SQLException, IOException {

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

    /*
        public int getECTS(long id) throws SQLException, IOException {
            List<Kurs> liste = this.getAll();
            for(Kurs kurs : liste)
            {
                if(kurs.getID() == id)
                    return kurs.getEcts();
            }
            return 30;
        }
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
     *
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

    public Kurs getKursNachId(long id) throws SQLException, IOException {

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
