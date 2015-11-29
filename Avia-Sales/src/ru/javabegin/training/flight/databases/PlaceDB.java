package ru.javabegin.training.flight.databases;

import ru.javabegin.training.flight.spr.objects.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaceDB
{
    private PlaceDB() {
    }
    private static PlaceDB instance;

    public static PlaceDB getInstance() {
        if (instance == null) {
            instance = new PlaceDB();
        }

        return instance;
    }

    public Place getPlace(long id)
    {
        try {
            return getPlace(getPlaceStmt(id));
        } catch (SQLException ex) {
            Logger.getLogger(PlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          //  AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    public ArrayList<Place> getPlacesByClass(long flightClassId)
    {
        try {
            return getPlaces(getPlaceStmtByClass(flightClassId));
        } catch (SQLException ex) {
            Logger.getLogger(PlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           // AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    public ArrayList<Place> getPlacesByAircraft(long aircraftId)
    {
        try {
            return getPlaces(getPlaceStmtByAircraft(aircraftId));
        } catch (SQLException ex) {
            Logger.getLogger(PlaceDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            //AviaDB.getInstance().closeConnection();
        }
       // AviaDB.getInstance().closeConnection();
        return null;
    }

    private ArrayList<Place> getPlaces(PreparedStatement stmt) throws SQLException {

        ArrayList<Place> list = new ArrayList<Place>();
        ResultSet rs = null;

        try {
            rs = stmt.executeQuery();

            while (rs.next())
            {
                list.add(fillPlace(rs));

                if ( rs.isClosed())
                {
                    boolean fl = true;
                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            rs.close();
            stmt.close();
        }

        return list;
    }

    private Place fillPlace(ResultSet rs) throws SQLException
    {
        Place place = new Place();
        place.setId(rs.getLong("id"));
        place.setSeatLetter(rs.getString("seat_letter").charAt(0));
        place.setSeatNumber(rs.getInt("seat_number"));

        long fl_class_id = rs.getLong("flight_class_id");
        place.setFlightClass(FlightClassDB.getInstance().getFlightClass( fl_class_id ));

        return place;
    }


    private Place getPlace(PreparedStatement stmt) {

        Place place = null;
        ResultSet rs = null;

        try {
            rs = stmt.executeQuery();

            rs.next();
            if (rs.isFirst()) {
                place = fillPlace(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PlaceDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return place;
    }





    private PreparedStatement getPlaceStmt(long id) throws SQLException {
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_place where id=?");
        stmt.setLong(1, id);
        return stmt;
    }

    private PreparedStatement getPlaceStmtByClass(long flightClassId) throws SQLException {
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("select * from spr_place where flight_class_id=?");
        stmt.setLong(1, flightClassId);
        return stmt;
    }

    private PreparedStatement getPlaceStmtByAircraft(long aircraftId) throws SQLException {
        Connection conn = AviaDB.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM avia.spr_place where id in (select place_id from spr_aircraft_place where aircraft_id=?) order by flight_class_id, seat_letter");
        stmt.setLong(1, aircraftId);
        return stmt;
    }
}
