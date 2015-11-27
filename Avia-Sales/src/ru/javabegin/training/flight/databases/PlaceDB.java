package ru.javabegin.training.flight.databases;

import ru.javabegin.training.flight.spr.objects.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceDB
{
     private PlaceDB(){}
    private static PlaceDB instance;

    public static PlaceDB getInstance()
    {
        if ( instance == null )
        {
            instance = new PlaceDB();
        }
        return instance;
    }

    private static final String SPR_PLACE_TABLE = "spr_place";

    private static final String ID = "id";
    private static final String SEAT_LETTER = "seat_letter";
    private static final String SEAT_NUMBER = "seat_number";
    private static final String FLIGHT_CLASS_ID = "flight_class_id";










    private PreparedStatement getPlaceStmtByAirCraft( long aircraft_id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();

        String query = "SELECT * FROM avia.spr_place where id in (select place_id from spr_aircraft_place where aircraft_id=?) order by flight_class_id, seat_letter";
        PreparedStatement stmt = conn.prepareStatement( query );
        stmt.setLong( 1, aircraft_id );
        return stmt;

    }



    // -------------Block get Placces by FlightClass ID  ---------------------------

    public ArrayList<Place> getPlacesByClass( long flightClassId )
    {
        try
        {
            return getPlaces(getPlaceStmtByClass( flightClassId ) ) ;
        }
        catch (Exception e){}
        finally
        {
              AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    private PreparedStatement getPlaceStmtByClass( long flight_class_id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();

        String query = "select * from  " + SPR_PLACE_TABLE + "  where  " + FLIGHT_CLASS_ID + "  = ?";
        PreparedStatement stmt = conn.prepareStatement( query );
        stmt.setLong( 1, flight_class_id );
        return stmt;

    }

    private ArrayList<Place> getPlaces( PreparedStatement stmt ) throws SQLException {
        ArrayList<Place> list = new ArrayList<Place>();
        ResultSet rs = null;

        try
        {
            rs = stmt.executeQuery();
            while (rs.next())
            {
                list.add( fillPlace(rs));
            }
        }
        catch ( Exception e){}
        finally
        {
            if ( rs != null)
            {
                rs.close();
            }
        }

        return list;
    }



    // -------------Block get Placces by FlightClass ID  ---------------------------




























    // **********************************************************

    // -------------Block get Place by Id  ---------------------------

    public Place getPlace(long id)
    {
        try
        {
            return getPlace( getPlaceStmt(id));
        }
        catch (Exception e){}
        finally
        {
            AviaDB.getInstance().closeConnection();
        }
        return  null;
    }

    private PreparedStatement getPlaceStmt( long id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();

        String query = "select * from  " + SPR_PLACE_TABLE + "  where  " + ID + "  = ?";
        PreparedStatement stmt = conn.prepareStatement( query );
        stmt.setLong( 1, id );
        return stmt;

    }


    private Place getPlace ( PreparedStatement stmt )
    {
        Place place = null;
        ResultSet rs = null;

        try
        {
            rs = stmt.executeQuery();
            rs.next();
            if ( rs.isFirst())
            {
                place = fillPlace(rs);
            }
        }
        catch ( Exception e){}
        finally
        {
            if ( rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return place;
    }

    private Place fillPlace( ResultSet rs ) throws SQLException {
        Place place = new Place();
        place.setId( rs.getLong(ID));
        place.setSeatLetter(rs.getString(SEAT_LETTER).charAt(0));
        place.setSeatNumber(rs.getInt(SEAT_NUMBER));
//        place.setFlightClass();

        return  place;
    }



    // -------------Block get Place by Id  ---------------------------






}
