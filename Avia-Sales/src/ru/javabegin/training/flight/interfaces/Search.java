package ru.javabegin.training.flight.interfaces;

import ru.javabegin.training.flight.objects.Flight;
import ru.javabegin.training.flight.spr.objects.City;

import java.util.Date;
import java.util.List;

public interface Search
{
      List<Flight> searchFlight ( Date date, City cityFrom, City cityTo, int placeCount );
}
