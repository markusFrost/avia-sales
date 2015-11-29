package ru.javabegin.training.flight.databases;

import ru.javabegin.training.flight.spr.objects.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CompanyDB
{
    private static CompanyDB instance;
    private CompanyDB(){}

    public static CompanyDB getInstance()
    {
        if ( instance == null )
        {
            instance = new CompanyDB();
        }
        return instance;
    }

    public Company getCompany ( long id )
    {
        try
        {
           return getCompany( getCompanyStmt( id ) );
        }
        catch ( Exception e ){}
        finally
        {

        }
        AviaDB.getInstance().closeConnection();
        return null;
    }

    private Company getCompany( PreparedStatement stmt ) throws SQLException
    {
       Company company = null;
        ResultSet rs = null;

        try
        {
           rs = stmt.executeQuery();

            rs.next();
            if ( rs.isFirst() )
            {
                company = new Company();
                company.setId(rs.getLong("id"));
                company.setName(rs.getString("name"));
                company.setDesc(rs.getString("desc"));
            }
        }
        catch ( Exception e ) {}
        finally
        {
             if ( rs != null )
             {
                 rs.close();
             }
        }

        return company;
    }

    private PreparedStatement getCompanyStmt( long id ) throws SQLException
    {
        Connection conn = AviaDB.getInstance().getConnection();
        String sql = "select * from spr_company where id = ?";
        PreparedStatement stmt = conn.prepareStatement( sql ) ;
        stmt.setLong(1, id );
        return stmt;
    }
}
