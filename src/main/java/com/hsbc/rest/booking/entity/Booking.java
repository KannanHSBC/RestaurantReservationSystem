package com.hsbc.rest.booking.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Booking {
    String bookingId;
    String customerName;
    int tableSize;
    String bookingDate;
    String bookingTime;

}
