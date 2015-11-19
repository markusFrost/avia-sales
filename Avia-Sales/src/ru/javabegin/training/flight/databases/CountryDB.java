package ru.javabegin.training.flight.databases;

import ru.javabegin.training.flight.spr.objects.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CountryDB
{
    private static final String SPR_COUNTRY_TABLE = "spr_country";

    private static final String ID= "id";
    private static final String CODE = "code";
    private static final String FLAG = "flag";
    private static final String DESC = "desc";
    private static final String NAME = "name";

    public static Country getCountry ( long id ) throws SQLException
    {
        Country country = null;
        Connection conn = AviaDB.getInstance().getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = " select * from " + SPR_COUNTRY_TABLE + " where "  + id + " = " + "?";

        try
        {
             stmt = conn.prepareStatement( sql );
            stmt.setLong( 1, id );

            rs = stmt.executeQuery();
            rs.first();

            country = new Country();
            country.setId(rs.getLong( ID ) );
            country.setCode(rs.getString( CODE ) );
            country.setFlag(rs.getBytes( FLAG ) );
            country.setDesc(rs.getString( DESC ) );
            country.setName(rs.getString( NAME ) );
        }
        finally
        {
           if ( rs != null )
           {
               rs.close();
           }
            if ( stmt != null )
            {
                stmt.close();
            }
        }

        return country;
    }
}
