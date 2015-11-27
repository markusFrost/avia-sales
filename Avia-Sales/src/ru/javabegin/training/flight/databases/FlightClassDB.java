package ru.javabegin.training.flight.databases;


import ru.javabegin.training.flight.spr.objects.FlightClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightClassDB
{
    private static FlightClassDB instance;

    private FlightClassDB(){}

    public static FlightClassDB  getInstance()
    {
        if ( instance == null )
        {
            instance = new FlightClassDB();
        }
        return instance;
    }

    public FlightClass getFlightClass( long id )
    {
        try
        {
           return getFlightClass( getFlightClassStmt(id) );
        }
        catch (Exception e){}
        finally
        {
              AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    private FlightClass getFlightClass( PreparedStatement stmt ) throws SQLException
    {
        FlightClass flightClass = null;
        ResultSet rs = null;
        try
        {
           rs = stmt.executeQuery();

            rs.next();

            if ( rs.isFirst() )
            {
                flightClass = new FlightClass();
                flightClass.setId(rs.getLong("id"));
                flightClass.setName( rs.getString("name"));
                flightClass.setDesc( rs.getString("desc"));
            }
        }
        catch ( Exception e ){}
        finally
        {
            if ( rs != null )
            {
                rs.close();
            }
        }
        return  flightClass;
    }

    private PreparedStatement getFlightClassStmt( long id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "select * from spr_flight_class where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        return stmt;
    }
}
