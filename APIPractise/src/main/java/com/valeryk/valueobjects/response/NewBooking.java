package com.valeryk.valueobjects.response;

public class NewBooking {

    private int bookingid;
    private Booking booking;

    private NewBooking(){}

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


}
