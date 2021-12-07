import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class jdbc {
    public static void main(String[] args) {

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/labor5","root","daria20");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student");
            while(resultSet.next())
            {
                System.out.println(resultSet.getString("Vorname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
