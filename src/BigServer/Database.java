package BigServer;
import javax.xml.transform.Result;
import java.sql.*;
public class Database {

    Connection connection=null;
    PreparedStatement s;
    String url;
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  //Checking for jdbc Driver, If there is ClassNotFoundException then either mysqlconnect jar FIle is not included or jar file is corrupted
            url = "jdbc:mysql://localhost:3306/";
            connection=DriverManager.getConnection(url,"root","karan12345");  //Connection with Mysql
            s=connection.prepareStatement("create database if not exists InterCode");  //Creating Database
            s.execute();
            s=connection.prepareStatement("use InterCode");
            s.execute();
            String query=" create table if not exists ClientTable(FirstName varchar(100) not null,LastName varchar(100) not null,UserName varchar(100),Password varchar(100),Id varchar(100) primary key);"; //Creating a table
            s=connection.prepareStatement(query);
            s.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Inserting NewClient into Database
    public void InsertNewClient(String FirstName,String Lastname,String UserName,String Password,String Id) throws SQLException {

            if(connection==null)
                connection=DriverManager.getConnection(url,"root","karan12345");

            String query="insert into ClientTable values(?,?,?,?,?)";
            s=connection.prepareStatement(query);
            s.setString(1,FirstName);
            s.setString(2,Lastname);
            s.setString(3,UserName);
            s.setString(4,Password);
            s.setString(5,Id);
            s.execute();
    }

    public Boolean CheckDuplicateId(String Id) throws SQLException {
        String query="select id from ClientTable where id="+'"'+Id+'"'+";";
        s=connection.prepareStatement(query);
        ResultSet resultSet=s.executeQuery();

        if(resultSet.next()==false) //resultset is empty
            return true;

        return false;
    }

    public Boolean CheckAuthentication(String UserName,String Password) throws SQLException {
        String query="select UserName,Password from ClientTable where UserName="+'"'+UserName+'"'+"and Password="+'"'+Password+'"'+";";
        s=connection.prepareStatement(query);
        ResultSet resultSet=s.executeQuery();

        while(resultSet.next())
        {
            if(resultSet.getString("UserName").equals(UserName)&&resultSet.getString("Password").equals(Password))
                return true;
        }

        return false;
    }
}
