package ru.javabegin.training.flight.databases;

import ru.javabegin.training.flight.spr.objects.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDB
{

    private static final String SPR_CITY_TABLE = "spr_city";

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String CODE = "code";
    private static final String DESC = "desc";
    private static final String COUNTRY_ID = "country_id";

    private CityDB(){}

    private static CityDB instance;

    public static CityDB getInstance()
    {
        if ( instance == null )
        {
            instance = new CityDB();
        }
        return instance;
    }

    public City getCity ( long id )
    {
        try
        {
            return getCity( getCityStmt( id ) );
        } catch (SQLException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally
        {
          AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    public City getCity ( String name )
    {
        try {
            return getCity( getCityStmt( name ) );
        } catch (SQLException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally
        {
            AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    private City getCity ( PreparedStatement stmt ) throws SQLException {
        City city = null;
        ResultSet rs = null;

        try
        {
            rs = stmt.executeQuery();
            rs.next();

            if ( rs.isFirst() )
            {
                city = new City();

                city.setId( rs.getLong( ID ) );
                city.setCode( rs.getString(CODE) );
                city.setCountry( CountryDB.getCountry( rs.getLong( COUNTRY_ID ) ) );
                city.setDesc( rs.getString(DESC) );
                city.setName( rs.getString( NAME ) );
            }
        } catch (SQLException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally
        {
            rs.close();
        }

           return city;
    }



    private PreparedStatement getCityStmt ( String name )  throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = " select * from " + SPR_CITY_TABLE + " where " + NAME + " = " + "?";
        PreparedStatement stmt = conn.prepareStatement( sql );
        stmt.setString(1 , name );
        return  stmt;
    }

    private PreparedStatement getCityStmt( long id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "select * from " + SPR_CITY_TABLE + " where " + ID + " = " + "?";
        PreparedStatement stmt = conn.prepareStatement( sql );
        stmt.setLong(1 , id);
        return stmt;
    }
}
