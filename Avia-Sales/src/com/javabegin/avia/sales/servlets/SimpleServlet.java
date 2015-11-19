package com.javabegin.avia.sales.servlets;

import ru.javabegin.training.flight.databases.CityDB;
import ru.javabegin.training.flight.spr.objects.City;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "SimpleServlet")
public class SimpleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=windows-1251");
        //response.getWriter().write("Привет!");

        City c = CityDB.getInstance().getCity( 2 );

       String val =  c.getName();
        response.getWriter().write( val );
    }
}
