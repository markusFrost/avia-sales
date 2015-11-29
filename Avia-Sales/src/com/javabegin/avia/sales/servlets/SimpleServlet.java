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
        //response.getWriter().write("Привет!");

        //ArrayList<Flight> list =   FlightDB.getInstance().getAllFlights();

        //Flight item = FlightDB.getInstance().getFlight(1);

        Aircraft item = AircraftDB.getInstance().getAircraft(1);

        AviaDB.getInstance().closeConnection();

       String val =   ""; ///"|" + item.getCompany().getName() + "|";
        //val = list.get(0).getSeatLetter() + "";
        response.getWriter().write( val );
    }
}
