package com.javabegin.avia.sales.servlets;

import ru.javabegin.training.flight.databases.*;
import ru.javabegin.training.flight.objects.Flight;
import ru.javabegin.training.flight.spr.objects.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "SimpleServlet")
public class SimpleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=windows-1251");

        City c1 = CityDB.getInstance().getCity(1);
        City c2 = CityDB.getInstance().getCity(2);
        City c3 = CityDB.getInstance().getCity(3);

        long date = 1328418000000L;

        ArrayList<Flight> list = FlightDB.getInstance().getFlights( date, c3, c2 );

       String val =   "";
        response.getWriter().write( val );

        AviaDB.getInstance().closeConnection();
    }
}
