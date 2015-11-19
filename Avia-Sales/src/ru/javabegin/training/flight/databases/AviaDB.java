package ru.javabegin.training.flight.databases;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AviaDB
{
    private Connection conn;
    private static InitialContext ic;
    private static DataSource ds;

    private static AviaDB instance;

    private AviaDB(){}

    public static AviaDB getInstance()
    {
           if ( instance == null )
           {
               instance = new AviaDB();
           }
        return instance;
    }

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    private static final String DB_NAME = "avia";
    static final String USER = "admin";
    static final String PASS = "123456";

    public  Connection getConnection()
    {
        try
        {
            // Do not forget to include lib mysql.jar!!!!!!!!!!!!
            if ( conn == null || conn.isClosed() )
            {
                //Register JDBC driver
                Class.forName( JDBC_DRIVER );
                conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return conn;
    }


    public void closeConnection()
    {
        if ( conn != null )
        {
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

}
