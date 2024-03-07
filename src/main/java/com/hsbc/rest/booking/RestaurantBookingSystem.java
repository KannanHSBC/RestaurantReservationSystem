package com.hsbc.rest.booking;

import com.hsbc.rest.booking.database.DatabaseConnection;
import com.hsbc.rest.booking.handler.ReserveTable;
import com.hsbc.rest.booking.handler.ShowBooking;
import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;


public class RestaurantBookingSystem {

    public static void main(String[] args) {
        DatabaseConnection.getConnection();
        MuServer server = MuServerBuilder.httpServer()
                .withHttpPort(8080)
                .addHandler(Method.GET, "/", (request, response, pathParams) -> {
                    response.write("Welcome to Restaurant Booking System");})
                .addHandler(Method.POST, "/booking/api/v1/book", new ReserveTable())
                .addHandler(Method.GET,"/booking/api/v1/viewBookings", new ShowBooking())
                .start();
        System.out.println("Started server successfully "+server.uri()+"/");
    }


}
