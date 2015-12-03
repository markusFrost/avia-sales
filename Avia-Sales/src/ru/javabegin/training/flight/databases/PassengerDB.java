package ru.javabegin.training.flight.databases;


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

    // get AllPassengers, getPassangers getPassanger by ID
    // first of all we need to create statmetnt
    // then create method that fill passanger

}
