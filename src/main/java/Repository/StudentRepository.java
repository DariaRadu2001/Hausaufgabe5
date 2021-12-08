package Repository;

import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import Modele.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StudentRepository implements ICrudRepository<Student>{

    private String url;
    private String user;
    private String password;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    /**
     * erledigt die Connexion mit der DB
     */
    void startConnection() throws IOException, SQLException {
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
    public Student create(Student obj) throws IOException, DasElementExistiertException, SQLException {

        this.startConnection();
        boolean wahr = false;
        try {
            resultSet = statement.executeQuery("SELECT idstudent FROM student");

            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("idstudent");
                idList.add(id);
            }

            if(!idList.contains(obj.getStudentID()))
            {
                wahr = true;
                String query = "INSERT INTO student VALUES(?, ?, ?, ?)";

                String vorname = obj.getVorname();
                String nachname = obj.getNachname();
                long id = obj.getStudentID();
                int anzahl = obj.getTotalKredits();

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong   (1, id);
                preparedStmt.setString (2, vorname);
                preparedStmt.setString (3, nachname);
                preparedStmt.setInt(4, anzahl);

                preparedStmt.execute();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.stopConnection();
        if(wahr)
            return obj;
        else
            throw new DasElementExistiertException("Das Student existiert");

    }

    @Override
    public List<Student> getAll() throws IOException, SQLException {
        List<Student> list = new ArrayList<>();
        this.startConnection();
        try{
            connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student");

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

        this.startConnection();
        boolean wahr = false;
        try {
            resultSet = statement.executeQuery("SELECT idstudent FROM student");

            List<Long> idList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("idstudent");
                idList.add(id);
            }

            if(!idList.contains(objID))
            {
                wahr = true;
                String query = "DELETE FROM student WEHERE idstudent = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setLong  (1, objID);

                preparedStmt.execute();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.stopConnection();

        if(wahr)
            return wahr;
        else
            throw new IllegalAccessException();
    }

    @Override
    public Student update(Student obj) throws IOException, ListIsEmptyException {
        return null;
    }
}
