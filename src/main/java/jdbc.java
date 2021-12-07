import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class jdbc {
    public static void main(String[] args) throws IOException {
       /* String url;
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student");
            while(resultSet.next())
            {
                System.out.println(resultSet.getString("Vorname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
