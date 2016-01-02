package ru.javabegin.training.flight.databases;


import ru.javabegin.training.flight.objects.Passenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PassengerDB
{
    private PassengerDB()
    {

    }
    private static PassengerDB instance;

    public static PassengerDB getInstance()
    {
        if (instance == null)
        {
            instance = new PassengerDB();
        }

        return instance;
    }

    // get AllPassengers,  getPassanger by ID
    // first of all we need to create statmetnt
    // then create method that fill passanger

    private ArrayList<Passenger> getPassengers(PreparedStatement stmt) throws SQLException
    {

        ArrayList<Passenger> list = new ArrayList<Passenger>();
        ResultSet rs = null;

        try {
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(fillPassenger(rs));
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return list;
    }

    public Passenger getPassenger(long id)
    {
        try
        {
            return getPassenger(getPassengerStmt(id));
        } catch (Exception ex)
        {

        }

        return null;
    }

    public ArrayList<Passenger> getAllPassengers()
    {
        try
        {
            return getPassengers(getAllPassengersStmt());
        } catch (Exception ex)
        {

        }

        return null;
    }

    private Passenger getPassenger(PreparedStatement stmt) throws SQLException
    {

        Passenger passenger = null;
        ResultSet rs = null;

        try
        {
            rs = stmt.executeQuery();

            rs.next();
            if (rs.isFirst())
            {
                passenger = fillPassenger(rs);
            }

        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
            if (stmt != null)
            {
                stmt.close();
            }
        }

        return passenger;
    }

    private Passenger fillPassenger(ResultSet rs) throws SQLException
    {
        Passenger passenger = new Passenger();
        passenger.setId(rs.getLong("id"));
        passenger.setDocumentNumber(rs.getString("document_number"));
        passenger.setEmail(rs.getString("email"));
        passenger.setFamilyName( rs.getString("family_name"));
        passenger.setGivenName(rs.getString("given_name"));
        passenger.setMiddleName(rs.getString("middle_name"));
        passenger.setPhone(rs.getString("phone"));

        return passenger;
    }

    private PreparedStatement getPassengerStmt( long id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "select * from passenger where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1,id);
        return stmt;
    }

    private PreparedStatement getAllPassengersStmt() throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "select * from passenger";
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt;
    }

    private PreparedStatement getInsertPassengerStmt(Passenger passenger) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "insert into passenger(given_name, middle_name, family_name, document_number, email, phone) values (?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, passenger.getGivenName());
        stmt.setString(2, passenger.getMiddleName());
        stmt.setString(3, passenger.getFamilyName());
        stmt.setString(4, passenger.getDocumentNumber());
        stmt.setString(5, passenger.getEmail());
        stmt.setString(6, passenger.getPhone());

        return stmt;
    }

}
