package com.hsbc.rest.booking.dao;

import com.hsbc.rest.booking.entity.Booking;

import java.util.Set;

public interface BookingDAO {
    int saveBooking();
    Set<Booking> viewBookingDetails();
    boolean isBookingAlreadyExist(Booking booking);
}
