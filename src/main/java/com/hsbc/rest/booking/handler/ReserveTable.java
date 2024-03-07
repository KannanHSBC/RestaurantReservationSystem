package com.hsbc.rest.booking.handler;

import com.google.gson.Gson;
import com.hsbc.rest.booking.dao.BookingDAO;
import com.hsbc.rest.booking.dao.BookingDAOImpl;
import com.hsbc.rest.booking.entity.Booking;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import io.muserver.RouteHandler;

import java.util.Map;

public class ReserveTable implements RouteHandler {

    BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    public void handle(MuRequest muRequest, MuResponse muResponse, Map<String, String> map) {
        Gson gson = new Gson();
        try {
            Booking booking = gson.fromJson(muRequest.readBodyAsString(), Booking.class);
            if (bookingDAO.isBookingAlreadyExist(booking)) {
                muResponse.sendChunk("Sorry booking table not available");
            } else {
                bookingDAO.saveBooking();
                muResponse.status(200);
                muResponse.sendChunk("Booking created for : " + booking.getBookingDate() +" at: "+booking.getBookingTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
