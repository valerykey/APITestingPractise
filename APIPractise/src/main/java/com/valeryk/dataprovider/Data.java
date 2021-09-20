package com.valeryk.dataprovider;

import com.valeryk.valueobjects.request.Authentication;
import com.valeryk.valueobjects.response.Booking;
import com.valeryk.valueobjects.response.BookingDates;

public class Data {

    public static Authentication createAuthentificationObject(String username, String password){
        return new Authentication.AuthenticationBuilder().username(username).password(password).build();
    }

    public static Booking createBookingObject(String firstname, String lastname, int totalprice, boolean deposit, BookingDates bookingDates, String additionalneeds){
        return new Booking.BookingBuilder().firstname(firstname).lastname(lastname).totalprice(totalprice).depositpaid(deposit).bookingdates(bookingDates).additionalneeds(additionalneeds).build();
    }

}
