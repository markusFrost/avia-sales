package ru.javabegin.training.flight.databases;


import ru.javabegin.training.flight.spr.objects.Aircraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AircraftDB
{
    private AircraftDB(){}

    private static AircraftDB instance;

    public static AircraftDB getInstance()
    {
        if ( instance == null )
        {
            instance = new AircraftDB();
        }
        return instance;
    }

    private static final String SPR_AIRCRAFT_TABLE = "spr_aircraft";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COMPANY_ID = "company_id";
    private static final String DESC = "desc";

    public Aircraft getAircraft ( long id )
    {
        try
        {
            return getAircraft( getAircraftStmt( id ) );
        }catch ( Exception e ){}
        finally
        {
             AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    private Aircraft getAircraft ( PreparedStatement stmt ) throws SQLException
    {
        Aircraft aircraft = null;
        ResultSet rs = null;

        try
        {
        rs = stmt.executeQuery();
        rs.next();


        if ( rs.isFirst() )
        {
            aircraft = new Aircraft();
            aircraft.setId(rs.getLong( ID ) );
            aircraft.setDesc(rs.getString( DESC ) );
            aircraft.setName(rs.getString( NAME ) );
           // aircraft.setPlaceList(PlaceDB.getInstance().getPlacesByAircraft(rs.getLong( ID ) ) );
           //    aircraft.setCompany(CompanyDB.getInstance().getCompany(rs.getInt( COMPANY_ID ) ) );
        }
        }catch ( Exception e  ) {}
        finally
        {
            rs.close();
        }

        return aircraft;
    }

    private PreparedStatement getAircraftStmt( long id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();

        String query = "select * from  " + SPR_AIRCRAFT_TABLE + "  where  " + ID + "  = ? ";

        PreparedStatement stmt = conn.prepareStatement( query );
        stmt.setLong( 1, id );

        return stmt;
    }
}

