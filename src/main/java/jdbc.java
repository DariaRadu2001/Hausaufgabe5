import Modele.Student;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/*
 connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student");
            List<Student> list = new ArrayList<>();
            while(resultSet.next())
            {
                String vname = resultSet.getString("Vorname");
                System.out.println(vname);
                String name = resultSet.getString("Nachname");
                System.out.println(name);
                long id = resultSet.getLong("idstudent");
                System.out.println(id);
                int anzahl = resultSet.getInt("AnzahlKredits");
                System.out.println(anzahl);
                Student student = new Student(vname,name,id,anzahl);
                list.add(student);
            }
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
 */




public class jdbc {
    public static void main(String[] args) throws IOException {/*
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
        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            Statement statement = connection.createStatement();

            String query = "UPDATE student SET Vorname = ? , Nachname = ?, AnzahlKredits =? WHERE idstudent = ?";

            String vorname = "Daria";
            String nachname = "Radu";
            long id = 1L;
            int anzahl = 11;

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, vorname);
            preparedStmt.setString(2, nachname);
            preparedStmt.setInt(3, anzahl);
            preparedStmt.setLong(4, id);

            preparedStmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}
