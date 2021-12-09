package Repository;

import Exception.DasElementExistiertException;
import Modele.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StudentRepository implements ICrudRepository<Student>{

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    /**
     * erledigt die Connexion mit der DB
     */
    private void startConnection() throws IOException, SQLException {
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\Hausaufgabe5\\target\\config.properties");
        Properties prop = new Properties();
        prop.load(input);
        String url = prop.getProperty("db.url");
        String user = prop.getProperty("db.user");
        String password = prop.getProperty("db.password");
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
        List<Long> idList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("SELECT idstudent FROM student");

            while (resultSet.next()) {
                long id2 = resultSet.getLong("idstudent");
                idList.add(id2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idList.contains(id);
    }

    @Override
    public Student create(Student obj) throws IOException, DasElementExistiertException, SQLException {

        if(!this.findOne(obj.getStudentID())) {
            this.startConnection();
            try {
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
                this.stopConnection();
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            throw new DasElementExistiertException("Das Student existiert");
        return null;
    }

    @Override
    public List<Student> getAll() throws IOException, SQLException {
        List<Student> list = new ArrayList<>();
        this.startConnection();
        try{

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
        this.stopConnection();
        if(list.size()>0)
            return list;
        return null;
    }

    @Override
    public boolean delete(Long objID) throws IllegalAccessException, IOException, SQLException {

        if(this.findOne(objID))
        {
            this.startConnection();
            try{
                String query = "DELETE FROM student WEHERE idstudent = ?";

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
        else
            throw new IllegalAccessException();

        return false;
    }

    @Override
    public Student update(Student obj) throws IOException,SQLException {
        if(this.findOne(obj.getStudentID())) {
            this.startConnection();

            try {
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
                this.stopConnection();
                return obj;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
