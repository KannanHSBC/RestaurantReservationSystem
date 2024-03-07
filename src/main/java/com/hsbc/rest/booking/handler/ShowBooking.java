package com.hsbc.rest.booking.handler;

import com.google.gson.Gson;
import com.hsbc.rest.booking.dao.BookingDAO;
import com.hsbc.rest.booking.dao.BookingDAOImpl;
import com.hsbc.rest.booking.entity.Booking;
import io.muserver.*;

import java.util.Map;
import java.util.Set;

public class ShowBooking implements RouteHandler {

    BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    public void handle(MuRequest muRequest, MuResponse muResponse, Map<String, String> map) {
        Set<Booking> bookingSet = bookingDAO.viewBookingDetails();
        muResponse.headers().set(HeaderNames.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
        Gson gson = new Gson();
        String jsonArray = gson.toJson(bookingSet);
        muResponse.status(200);
        muResponse.sendChunk(jsonArray);
    }
}
