package ru.javabegin.training.flight.databases;


import ru.javabegin.training.flight.spr.objects.FlightClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlightClassDB
{
    private FlightClassDB() {
    }
    private static FlightClassDB instance;

    public static FlightClassDB getInstance() {
        if (instance == null) {
            instance = new FlightClassDB();
        }

        return instance;
    }


    public FlightClass getFlightClass(long id) {
//        try
//        {
//            return getFlightClass(getFlightClassStmt(id));
//        } catch (SQLException ex)
//        {
//            Logger.getLogger(FlightClassDB.class.getName()).log(Level.SEVERE, null, ex);
//        } finally
//        {
//            AviaDB.getInstance().closeConnection();
//        }
        try
        {
            return getFlightClass(getFlightClassStmt(id));
        } catch (SQLException ex)
        {
            Logger.getLogger(FlightClassDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        AviaDB.getInstance().closeConnection();
        return null;
    }


    private FlightClass getFlightClass(PreparedStatement stmt) throws SQLException {

        FlightClass flightClass = null;
        ResultSet rs = null;

        try
        {
            rs = stmt.executeQuery();

            rs.next();
            if (rs.isFirst()) {
                flightClass = new FlightClass();
                flightClass.setId(rs.getLong("id"));
                flightClass.setName(rs.getString("name"));
                flightClass.setDesc(rs.getString("desc"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            if ( rs != null && !rs.isClosed() )
            {
                rs.close();
                stmt.close();
            }
        }

        ///FlightClass flightClass = new FlightClass();
        return flightClass;
    }


    private PreparedStatement getFlightClassStmt(long id) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_flight_class where id=?");
        stmt.setLong(1, id);
        return stmt;
    }

}
