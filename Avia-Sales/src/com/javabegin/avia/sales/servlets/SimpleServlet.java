package com.javabegin.avia.sales.servlets;

import ru.javabegin.training.flight.databases.*;
import ru.javabegin.training.flight.objects.Flight;
import ru.javabegin.training.flight.objects.Passenger;
import ru.javabegin.training.flight.objects.Reservation;
import ru.javabegin.training.flight.spr.objects.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;


@WebServlet(name = "SimpleServlet")
public class SimpleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=windows-1251");

       Flight flight = FlightDB.getInstance().getFlight(1);

       Place place = PlaceDB.getInstance().getPlace(2);

        Passenger passenger = PassengerDB.getInstance().getPassenger(1); //get from DB

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(1328418000000L);

        Reservation reserv = new Reservation();
        reserv.setAddInfo("Без обеда");
        reserv.setCode(UUID.randomUUID().toString());
        reserv.setPassenger(passenger);
        reserv.setReserveDateTime(date);
        reserv.setPlace(place);
        reserv.setFlight(flight);

      //boolean fl =   ReservationDB.getInstance().insertReservation(reserv);

        Reservation item = ReservationDB.getInstance().getReservation(1);
        ArrayList<Reservation> listReservations = ReservationDB.getInstance().getAllReservations();

       String val =    "";
        response.getWriter().write( val );

        AviaDB.getInstance().closeConnection();
    }
}
