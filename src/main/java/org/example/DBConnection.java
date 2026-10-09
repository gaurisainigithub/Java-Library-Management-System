package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private  static final String PASSWORD = "gurushi123";

    public static Connection getConnection()
    {
        Connection connection =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        }
        catch(ClassNotFoundException e)
        {
            System.out.println("My Sql Jdbc driver not found");
        }
        catch(SQLException e1)
        {
            System.out.println("Database Connection Failed");
        }

        return connection;
    }
}
