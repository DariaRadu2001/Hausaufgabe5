package Repository;
import Modele.Enrolled;
import Exception.DasElementExistiertException;
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

    public Enrolled create(Enrolled obj) throws IOException, DasElementExistiertException, SQLException, ListIsEmptyException {

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
            throw new ListIsEmptyException("In der enrollment Liste ist leer");
        return list;
    }

    /*
    public boolean deleteEnrolled(Enrolledment obj) throws IOException, SQLException {

        boolean wahr = false;
        if (this.existiertKurs(obj.getIdKurs()) && this.existiertStudent(obj.getIdStudent())) {
            this.startConnection();

            try {

                List<Enrolledment> list = this.getAll();
                for (Enrolledment enrolledment : list) {
                    if (enrolledment.getIdStudent() == obj.getIdStudent() && enrolledment.getIdKurs() == obj.getIdKurs()) {
                        wahr = true;
                        break;
                    }

                }

                if (wahr) {
                    String query = "DELETE FROM enrolled WHERE idstudent = ? AND idkurs = ?";

                    long idStudent = obj.getIdStudent();
                    long idkurs = obj.getIdKurs();

                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setLong(1, idStudent);
                    preparedStmt.setLong(2, idkurs);

                    preparedStmt.execute();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            this.stopConnection();

        }
        return wahr;
    }
*/

    /**
     * sucht ein Objekt nach seiner ID
     *
     * @param idStudent des Students
     * @param idKurs    des Kurses
     * @return true, wenn das Objekt in deer DB ist, false andernfalls
     * @throws SQLException, wenn man die Connexion nicht erledigen kann
     * @throws IOException,  wenn man die Connexion nicht erledigen kann
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

            for (Enrolled enrolledment : list) {
                if (enrolledment.getIdStudent() == idStudent && enrolledment.getIdKurs() == idKurs) {
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

    public void deleteEnrolledNachKurs(long kursId) throws SQLException {

        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            String query = "DELETE FROM enrolled WEHERE idkurs = ?";

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
