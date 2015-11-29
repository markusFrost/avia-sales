package com.javabegin.avia.sales.servlets;

import ru.javabegin.training.flight.databases.*;
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

       // City c = CityDB.getInstance().getCity( 1 );

    // Company item =  CompanyDB.getInstance().getCompany(1);
      //
  //   ArrayList<Place> list = PlaceDB.getInstance().getPlacesByClass(1);

       //ArrayList<Place> list = PlaceDB.getInstance().getPlacesByAircraft(1)  ;

        Aircraft item = AircraftDB.getInstance().getAircraft(1);

       String val = "ldflfkd"; ///"|" + item.getCompany().getName() + "|";
        //val = list.get(0).getSeatLetter() + "";
        response.getWriter().write( val );
    }
}
