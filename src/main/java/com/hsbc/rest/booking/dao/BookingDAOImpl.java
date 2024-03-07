package com.hsbc.rest.booking.dao;

import com.hsbc.rest.booking.database.DatabaseConnection;
import com.hsbc.rest.booking.entity.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BookingDAOImpl implements BookingDAO{

    static final String INSERT_BOOKING = "INSERT INTO BOOKING(ID, CUSTOMER_NAME, TABLE_SIZE, BOOKING_DATE, BOOKING_TIME) VALUES  (?, ?, ?, ?, ?);";

    static final String VIEW_BOOKING = "SELECT * FROM BOOKING";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    @Override
    public int saveBooking() {
        int bookingValue = 0;
        try(
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKING);
        ) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            preparedStatement.setString(1, String.valueOf(new Random().nextInt()));
            preparedStatement.setString(2, "Tony"+ Math.random());
            preparedStatement.setString(3, "4");
            preparedStatement.setString(4, LocalDate.now().format(dateFormatter));
            preparedStatement.setString(5, LocalTime.now().format(timeFormatter));
            bookingValue = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingValue;
    }

    @Override
    public Set<Booking> viewBookingDetails() {
        Set<Booking> bookings = new HashSet<>();
        try(
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(VIEW_BOOKING);
            ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getString("ID"));
                booking.setCustomerName(resultSet.getString("CUSTOMER_NAME"));
                booking.setTableSize(resultSet.getInt("TABLE_SIZE"));
                booking.setBookingDate(resultSet.getString("BOOKING_DATE"));
                booking.setBookingTime(resultSet.getString("BOOKING_TIME"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean isBookingAlreadyExist(Booking booking) {
        boolean bookingAlreadyExist = false;
        LocalTime newBookingTime =  LocalTime.parse(booking.getBookingTime(), formatter);
        Set<Booking> bookingSet = viewBookingDetails();
        for (Booking booking1: bookingSet) {
            LocalTime reservationStartTime =  LocalTime.parse(booking1.getBookingTime(), formatter);
            LocalTime reservationEndTime = newBookingTime.plusHours(2);
            if (reservationEndTime.isAfter(reservationStartTime)) {
                if (reservationStartTime.isBefore(newBookingTime) && reservationEndTime.isAfter(newBookingTime)) {
                    bookingAlreadyExist = true;
                }
            } else if (newBookingTime.isAfter(reservationStartTime) || newBookingTime.isBefore(reservationEndTime)) {
                bookingAlreadyExist = true;
            }
        }
        return bookingAlreadyExist;
    }


}
