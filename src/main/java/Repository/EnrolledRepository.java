package Repository;

import Modele.Enrolledment;
import Exception.DasElementExistiertException;
import Exception.ListIsEmptyException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public abstract class EnrolledRepository implements ICrudRepository<Enrolledment>{


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

    public boolean existiertStudent(Long id) throws SQLException, IOException {
        this.startConnection();
        try {
            resultSet = statement.executeQuery("SELECT idstudent FROM student");

            while (resultSet.next()) {
                long idStudent = resultSet.getLong("idstudent");
                if (idStudent == id) {
                    this.stopConnection();
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.stopConnection();
        return false;
    }

    public boolean existiertKurs(Long id) throws SQLException, IOException {
        this.startConnection();
        try {
            resultSet = statement.executeQuery("SELECT idkurs FROM kurs");

            while (resultSet.next()) {
                long idkurs = resultSet.getLong("idkurs");
                if (idkurs == id) {
                    this.stopConnection();
                    return true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.stopConnection();
        return false;
    }

    @Override
    public Enrolledment create(Enrolledment obj) throws IOException, DasElementExistiertException, SQLException {

        if(this.existiertKurs(obj.getIdKurs()) && this.existiertStudent(obj.getIdStudent()))
        {
            this.startConnection();
            boolean wahr = true;
            try {
                resultSet = statement.executeQuery("SELECT * FROM enrolled");

                List<Enrolledment> list = new ArrayList<>();
                while (resultSet.next()) {
                    long idStudent = resultSet.getLong("idstudent");
                    long idkurs = resultSet.getLong("idkurs");
                    Enrolledment enrolled = new Enrolledment(idStudent,idkurs);
                    list.add(enrolled);
                }

                for(Enrolledment enrolledment : list)
                {
                    if (enrolledment.getIdStudent() == obj.getIdStudent() && enrolledment.getIdKurs() == obj.getIdKurs()) {
                        wahr = false;
                        break;
                    }

                }

                if(wahr)
                {
                    String query = "INSERT INTO enrolled VALUES(?, ?)";

                    long idStudent =  obj.getIdStudent();
                    long idkurs = obj.getIdKurs();

                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setLong   (1, idStudent);
                    preparedStmt.setLong (2, idkurs);

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
        else
            return null;
    }

    @Override
    public List<Enrolledment> getAll() throws IOException, SQLException, ListIsEmptyException {

        List<Enrolledment> list = new ArrayList<>();
        this.startConnection();
        try {
            resultSet = statement.executeQuery("SELECT * FROM enrolled");


            while (resultSet.next()) {
                long idStudent = resultSet.getLong("idstudent");
                long idkurs = resultSet.getLong("idkurs");
                Enrolledment enrolled = new Enrolledment(idStudent,idkurs);
                list.add(enrolled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.stopConnection();

        if(list.size()==0)
            throw new ListIsEmptyException("In der enrollment Liste ist leer");
        return list;
    }

    @Override
    public Enrolledment update(Enrolledment obj) throws IOException, ListIsEmptyException, SQLException {

        this.startConnection();



        this.startConnection();

        return null;
    }


    public boolean deleteEnrolled(Enrolledment obj) throws IOException, SQLException {

        boolean wahr = false ;
        if(this.existiertKurs(obj.getIdKurs()) && this.existiertStudent(obj.getIdStudent()))
        {
            this.startConnection();

            try {

                List<Enrolledment> list = this.getAll();
                for(Enrolledment enrolledment : list)
                {
                    if (enrolledment.getIdStudent() == obj.getIdStudent() && enrolledment.getIdKurs() == obj.getIdKurs()) {
                        wahr = true;
                        break;
                    }

                }

                if(wahr)
                {
                    String query = "DELETE FROM student WEHERE idstudent = ? AND idkurs = ?";

                    long idStudent =  obj.getIdStudent();
                    long idkurs = obj.getIdKurs();

                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setLong   (1, idStudent);
                    preparedStmt.setLong (2, idkurs);

                    preparedStmt.execute();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            this.stopConnection();

        }
        return wahr;
    }
}
