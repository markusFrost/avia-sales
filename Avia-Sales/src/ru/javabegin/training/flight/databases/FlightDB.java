package ru.javabegin.training.flight.databases;


import ru.javabegin.training.flight.objects.Flight;
import ru.javabegin.training.flight.spr.objects.City;
import ru.javabegin.training.flight.utils.GMTCalendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class FlightDB
{
    public static final int INTERVAL = 1;


    private FlightDB(){}

    private static FlightDB instance;

    public static FlightDB getInstance()
    {
        if ( instance == null )
        {
            instance = new FlightDB();
        }
        return instance;
    }

    public ArrayList<Flight> getFlights( long dateTime, City cityFrom, City cityTo)
    {
        try
        {
            Calendar c = GMTCalendar.getInstance();
            c.setTimeInMillis(dateTime);
            return getFlights(getFlightsStmt(c, cityFrom, cityTo ) );
        }
        catch (Exception e){}
        return null;
    }

    public ArrayList<Flight> getAllFlights()
    {
        try
        {
            return getFlights( getAllFlightsStmt());
        }
        catch (Exception e){}
        finally
        {
           // AviaDB.getInstance().closeConnection();
        }
        return null;
    }

    public Flight getFlight ( long id )
    {
        try
        {
           return getFlight(getFlightStmt(id));
        }
        catch ( Exception e ){}
        return null;
    }

    private Flight getFlight( PreparedStatement stmt ) throws SQLException {
        Flight flight = null;
        ResultSet rs = null;

        try
        {
           rs = stmt.executeQuery();

            rs.next();
            if ( rs.isFirst())
            {
                flight = fillFlight(rs);
            }
        }
        catch ( Exception e ){}
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
        return flight;
    }

    private ArrayList<Flight> getFlights( PreparedStatement stmt ) throws SQLException
    {
        ArrayList<Flight> list = new ArrayList<Flight>();
        ResultSet rs = null;

        try
        {
            rs = stmt.executeQuery();
            while ( rs.next())
            {
               list.add( fillFlight(rs));
            }
        }
        catch (Exception e)
        {
             e.printStackTrace();
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

        return list;
    }

    private static final String MIN = " мин.";
    private static final String HOUR = " ч.  ";
    private static final String DAY = " д.  ";

    private Flight fillFlight( ResultSet rs ) throws SQLException
    {
        Calendar dateDepart = Calendar.getInstance();
        Calendar dateCome = Calendar.getInstance();

        dateDepart.setTimeInMillis(rs.getLong("date_depart"));
        dateCome.setTimeInMillis(rs.getLong("date_come"));

        Flight flight = new Flight();
        flight.setId(rs.getLong("id"));
        flight.setCode(rs.getString("code"));
        flight.setDateDepart(dateDepart);
        flight.setDateCome(dateCome);
        flight.setAircraft(AircraftDB.getInstance().getAircraft(rs.getLong("aircraft_id")));
        flight.setCityFrom(CityDB.getInstance().getCity(rs.getLong("city_from_id")));
        flight.setCityTo(CityDB.getInstance().getCity(rs.getLong("city_to_id")));

        StringBuilder sb = new StringBuilder();

        int dayDiff = dateCome.get( Calendar.DAY_OF_YEAR) - dateDepart.get(Calendar.DAY_OF_YEAR);
        int hourDiff = dateCome.get( Calendar.HOUR_OF_DAY) - dateDepart.get(Calendar.HOUR_OF_DAY);
        int minDiff =  dateCome.get( Calendar.MINUTE) - dateDepart.get(Calendar.MINUTE);

        if ( dayDiff > 0 )
        {
             sb.append(dayDiff).append(DAY);
        }

        if ( hourDiff > 0 )
        {
            sb.append(hourDiff).append(HOUR);
        }

        if ( minDiff > 0 )
        {
            sb.append(minDiff).append(MIN);
        }

        flight.setDuration( sb.toString() );

        return flight;
    }

    private PreparedStatement getAllFlightsStmt() throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "SELECT * FROM flight;" ;
        PreparedStatement stmt = conn.prepareStatement( sql );
        return stmt;
    }

    private PreparedStatement getFlightStmt( long id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "SELECT * FROM flight where id = ?";
        PreparedStatement stmt = conn.prepareStatement( sql );
        stmt.setLong(1, id);
        return stmt;
    }

    private PreparedStatement getFlightsStmt( Calendar dateTime, City cityFrom, City cityTo) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "select * from flight where date_depart>=? and  date_depart<? and " +
                " city_from_id=? and city_to_id=?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        //  we do not need time = only date
        dateTime.set(Calendar.HOUR_OF_DAY, 0);
        dateTime.set(Calendar.MINUTE, 0);
        dateTime.set(Calendar.SECOND, 0);
        dateTime.set(Calendar.MILLISECOND, 0);

        // interval of searching

        Calendar dateTimeInterval = (Calendar) dateTime.clone();
        dateTimeInterval.add(Calendar.DATE, INTERVAL );

        stmt.setLong(1, dateTime.getTimeInMillis());
        stmt.setLong(2, dateTimeInterval.getTimeInMillis());
        stmt.setLong(3, cityFrom.getId());
        stmt.setLong(4, cityTo.getId());

        return stmt;

    }
}
