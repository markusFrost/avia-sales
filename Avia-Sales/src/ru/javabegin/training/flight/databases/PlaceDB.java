package ru.javabegin.training.flight.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        String query = "select spr_place.id, spr_place.seat_letter, spr_place.seat_number, spr_place.flight_class_id from spr_place inner join spr_aircraft_place where spr_aircraft_place.place_id = spr_place.id and spr_aircraft_place.aircraft_id = ?";
        PreparedStatement stmt = conn.prepareStatement( query );
        stmt.setLong( 1, aircraft_id );
        return stmt;

    }

    private PreparedStatement getPlaceStmtByClass( long flight_class_id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();

        String query = "select * from  " + SPR_PLACE_TABLE + "  where  " + FLIGHT_CLASS_ID + "  = ?";
        PreparedStatement stmt = conn.prepareStatement( query );
        stmt.setLong( 1, flight_class_id );
        return stmt;

    }


    private PreparedStatement getPlaceStmt( long id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();

        String query = "select * from  " + SPR_PLACE_TABLE + "  where  " + ID + "  = ?";
        PreparedStatement stmt = conn.prepareStatement( query );
        stmt.setLong( 1, id );
        return stmt;

    }



}
