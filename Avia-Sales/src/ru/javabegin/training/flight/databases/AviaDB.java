package ru.javabegin.training.flight.databases;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
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

    public Connection getConnection()
    {
        try
        {
               if ( conn == null || conn.isClosed() )
               {
                   ic = new InitialContext();
                   ds = (DataSource) ic.lookup("java:comp/env/jdbc/Avia");
                   conn = ds.getConnection();
               }
        } catch (SQLException ex) {
            Logger.getLogger(AviaDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(AviaDB.class.getName()).log(Level.SEVERE, null, ex);
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
