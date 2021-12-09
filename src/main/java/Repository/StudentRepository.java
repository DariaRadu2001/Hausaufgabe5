package Repository;

import Exception.DasElementExistiertException;
import Modele.Student;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentRepository implements ICrudRepository<Student>{

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private DBConnection dbConnection;

    /**
     * sucht, ob ein Student in der DB ist nach seiner ID
     * @param id des Students
     * @return true, wenn der Student existiert, false anders falls
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public boolean findOne(long id) throws SQLException, IOException {

        List<Long> idList = new ArrayList<>();
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT idstudent FROM student");

            while (resultSet.next()) {
                long id2 = resultSet.getLong("idstudent");
                idList.add(id2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        connection.close();
        return idList.contains(id);
    }

    /**
     * ein neuer Tupel wird fur Tabelle Student erledigt
     * @param obj, das Objekt die man hinlegt
     * @return das Objekt, wenn man es erledigen kann, anders, falls null
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws DasElementExistiertException, der Student existiert
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Student create(Student obj) throws IOException, DasElementExistiertException, SQLException {

        if(!this.findOne(obj.getStudentID())) {

            try {
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();

                String query = "INSERT INTO student VALUES(?, ?, ?, ?)";

                String vorname = obj.getVorname();
                String nachname = obj.getNachname();
                long id = obj.getStudentID();
                int anzahl = obj.getTotalKredits();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong(1, id);
                preparedStmt.setString(2, vorname);
                preparedStmt.setString(3, nachname);
                preparedStmt.setInt(4, anzahl);

                preparedStmt.execute();
                connection.close();
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            throw new DasElementExistiertException("Das Student existiert");
        return null;
    }

    /**
     * gibt alle Tupels aus der Tabelle Student
     * @return die Liste
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public List<Student> getAll() throws IOException, SQLException {
        List<Student> list = new ArrayList<>();

        try{
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM student");

            while(resultSet.next())
            {
                String vorname = resultSet.getString("Vorname");
                String nachname = resultSet.getString("Nachname");
                long id = resultSet.getLong("idstudent");
                int anzahl = resultSet.getInt("AnzahlKredits");
                Student student = new Student(vorname,nachname,id,anzahl);
                list.add(student);
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
     * löscht das Tupels aus Tabelle student
     * @param objID, des Objekts, das man löschen will
     * @return true, wenn der Student existiert, false anders falls
     * @throws IllegalAccessException, das Objekt, das man löschen will, existiert nicht
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException {

        if(this.findOne(objID))
        {

            try{
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();
                statement = connection.createStatement();
                String query = "DELETE FROM student WEHERE idstudent = ?";

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
        else
            throw new IllegalAccessException();

        return false;
    }

    /**
     * ander die Attribute eines Tupels
     * @param obj, das Objekt mit dem switch erledigt
     * @return  das Objekt, wenn man es erledigen kann, anders, falls null
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    @Override
    public Student update(Student obj) throws IOException,SQLException {
        if(this.findOne(obj.getStudentID())) {
            try {
                dbConnection = new DBConnection();
                connection = dbConnection.startConnection();
                String query = "UPDATE student SET Vorname = ? , Nachname = ?, AnzahlKredits =? WHERE idstudent = ?";

                String vorname = obj.getVorname();
                String nachname = obj.getNachname();
                long id = obj.getStudentID();
                int anzahl = obj.getTotalKredits();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, vorname);
                preparedStmt.setString(2, nachname);
                preparedStmt.setInt(3, anzahl);
                preparedStmt.setLong(4, id);

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
     * gibt der Anzahl von Kredits, die ein Student noch braucht
     * @param id des Students
     * @return Anzahl Kredits
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public int getNotwendigeKredits(long id) throws SQLException, IOException {
        List<Student> liste = this.getAll();
        for(Student student : liste)
        {
            if(id == student.getStudentID())
                return student.getNotwendigeKredits();
        }
        return 0;
    }

    /**
     * gibt alle Studenten, die bei einem Kurs teilnehmen
     * @param id des Kurses
     * @return die Liste von angemeldeten Studenten
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     */
    public List<Long> getStudentenAngemeldetBeiEineKurs(long id) throws SQLException {
        List<Long> listeAngemeldet = new ArrayList<>();

        try{
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM enrolled");
            while (resultSet.next()) {
                if(resultSet.getLong("idkurs") == id)
                {
                    long idStudent = resultSet.getLong("idstudent");
                    listeAngemeldet.add(idStudent);
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        connection.close();
        return listeAngemeldet;
    }

    /**
     * ändert die Anzahl der Kredite von dem Studenten, die bei einem Kurs teilnehmen
     * @param idKurs, des Kurses
     * @param neueECTS, neue Anzahl von ECTS
     * @param alteECTS, alte Anzahl von ECTS
     * @throws SQLException, wenn man das Connexion nicht erledigen kann
     * @throws IOException, wenn man das Connexion nicht erledigen kann
     */
    public void andernKredits(long idKurs, int neueECTS, int alteECTS) throws SQLException, IOException
    {
        List<Long> list = this.getStudentenAngemeldetBeiEineKurs(idKurs);
        List<Student> listeStudenten = this.getAll();
        for(Student student : listeStudenten)
        {
            if(list.contains(student.getStudentID()))
            {
                int ects = student.getTotalKredits() - alteECTS + neueECTS;
                Student studentZuAndern = new Student(student.getVorname(),student.getNachname(),student.getStudentID(),ects);
                this.update(studentZuAndern);
            }
        }
    }

    /**
     * Sortiert in steigender Reihenfolge die Liste von Studenten nach Anzahl ECTS
     */
    public List<Student> sortList() throws SQLException, IOException {
        List<Student> liste = this.getAll();
        liste.sort(Student::compareTo);
        return liste;
    }

    /**
     * filtert die Liste, nimmt nur die Studenten die 30 ECTS haben
     * @return die gefilterte Liste
     */
    public List<Student> filterList() throws SQLException, IOException {
        List<Student> liste = this.getAll();
        return liste.stream()
                .filter(student->student.getTotalKredits() == 30).toList();
    }

    /**
     * gibt dem Student nach seiner ID
     * @param id des Students
     * @return der Student
     */
    public Student getStudentNachId(long id) {

        Student student = null;
        try {
            dbConnection = new DBConnection();
            connection = dbConnection.startConnection();
            statement = connection.createStatement();
            String query = "SELECT * FROM student WHERE idstudent = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setLong(1, id);
            ResultSet resultSet = preparedStmt.executeQuery();

            if(resultSet.next())
            {
                String vorname = resultSet.getString("Vorname");
                String nachname = resultSet.getString("Nachname");
                int anzahl = resultSet.getInt("AnzahlKredits");
                student = new Student(vorname, nachname, id, anzahl);
            }

            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }
}
