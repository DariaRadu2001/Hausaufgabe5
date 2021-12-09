package Repository;
import Modele.Enrolled;
import Exception.ListIsEmptyException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrolledRepository {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private DBConnection dbConnection;

    /**
     * überprüft, ob ein Student existiert nach eine ID
     * @param id des Students
     * @return true, wenn der Student existiert, false anders falls
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    public boolean existiertStudent(Long id) throws SQLException {

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT idstudent FROM student");

            while (resultSet.next()) {
                long idStudent = resultSet.getLong("idstudent");
                if (idStudent == id) {
                    connection.close();
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return false;
    }

    /**
     * überprüft, ob ein Kurs existiert nach eine ID
     * @param id des Kurses
     * @return true, wenn der Student existiert, false anders falls
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    public boolean existiertKurs(Long id) throws SQLException{

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT idkurs FROM kurs");

            while (resultSet.next()) {
                long idkurs = resultSet.getLong("idkurs");
                if (idkurs == id) {
                    connection.close();
                    return true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return false;

    }

    /**
     * ein neuer Tupel wird fur Tabelle Enrolled erledigt
     * @param obj das Tupel
     * @return das Objekt, wenn man es erledigen kann, anders, falls null
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    public Enrolled create(Enrolled obj) throws IOException, SQLException {

        if (this.existiertKurs(obj.getIdKurs()) && this.existiertStudent(obj.getIdStudent())) {
            if(!this.findOne(obj.getIdStudent(),obj.getIdKurs()))
            {
                try {
                    dbConnection = new DBConnection();
                    connection = dbConnection.startConnection();
                    String query = "INSERT INTO enrolled VALUES(?, ?, ?)";

                    long id = Enrolled.id;
                    long idStudent = obj.getIdStudent();
                    long idKurs = obj.getIdKurs();

                    PreparedStatement preparedStmt = connection.prepareStatement(query);

                    preparedStmt.setLong(1, id);
                    preparedStmt.setLong(2, idStudent);
                    preparedStmt.setLong(3, idKurs);

                    preparedStmt.execute();
                    connection.close();
                    return obj;
                    }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * gibt alle Tupels aus der Tabelle Enrolled
     * @return die Liste
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws ListIsEmptyException, wenn die Liste leer ist
     */
    public List<Enrolled> getAll() throws IOException, SQLException, ListIsEmptyException {

        List<Enrolled> list = new ArrayList<>();

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM enrolled");


            while (resultSet.next()) {
                long idStudent = resultSet.getLong("idstudent");
                long idkurs = resultSet.getLong("idkurs");
                Enrolled enrolled = new Enrolled(idStudent, idkurs);
                list.add(enrolled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        connection.close();

        if (list.size() == 0)
            throw new ListIsEmptyException("Die enrollment Liste ist leer");
        return list;
    }

    /**
     * sucht ein Objekt nach seiner ID
     *
     * @param idStudent des Students
     * @param idKurs des Kurses
     * @return true, wenn das Objekt in deer DB ist, false andernfalls
     * @throws SQLException, wenn man die Connexion nicht erledigen kann
     * @throws IOException, wenn man die Connexion nicht erledigen kann
     */
    public boolean findOne(long idStudent, long idKurs) throws SQLException, IOException {

        boolean wahr = false;
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM enrolled");

            List<Enrolled> list = new ArrayList<>();
            while (resultSet.next()) {
                long idStudent2 = resultSet.getLong("idstudent");
                long idKurs2 = resultSet.getLong("idkurs");
                Enrolled enrolled = new Enrolled(idStudent2, idKurs2);
                list.add(enrolled);
            }

            for (Enrolled enrollment : list) {
                if (enrollment.getIdStudent() == idStudent && enrollment.getIdKurs() == idKurs) {
                    wahr = true;
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return wahr;
    }

    /**
     * löscht alle Tupels aus Tabelle enrolled die als idKurs das gegebene Id haben
     * @param kursId nachdem wir löschen
     * @throws SQLException, wenn man die Connexion nicht erledigen kann
     */
    public void deleteEnrolledNachKurs(long kursId) throws SQLException {

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM enrolled WHERE idkurs = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, kursId);

            preparedStmt.execute();

            }
             catch (Exception e)
             {
                e.printStackTrace();
             }

        connection.close();

    }

}
