package com.valeryk.valueobjects.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {

        private String firstname;
        private String lastname;
        private int totalprice;
        private boolean depositpaid;
        private BookingDates bookingdates;
        private String additionalneeds;

        public Booking()
        {
                super();
        }

        private Booking(BookingBuilder builder) {
                this.firstname = builder.firstname;
                this.lastname = builder.lastname;
                this.totalprice = builder.totalprice;
                this.bookingdates = builder.bookingdates;
                this.additionalneeds = builder.additionalneeds;
        }

        public BookingDates getBookingdates() {
                return bookingdates;
        }

        public void setBookingdates(BookingDates bookingdates) {
                this.bookingdates = bookingdates;
        }

        public String getFirstname() {
                return firstname;
        }

        public void setFirstname(String firstname) {
                this.firstname = firstname;
        }

        public String getLastname() {
                return lastname;
        }

        public void setLastname(String lastname) {
                this.lastname = lastname;
        }

        public int getTotalprice() {
                return totalprice;
        }

        public void setTotalprice(int totalprice) {
                this.totalprice = totalprice;
        }

        public boolean isDepositpaid() {
                return depositpaid;
        }

        public void setDepositpaid(boolean depositpaid) {
                this.depositpaid = depositpaid;
        }

        public String getAdditionalneeds() {
                return additionalneeds;
        }

        public void setAdditionalneeds(String additionalneeds) {
                this.additionalneeds = additionalneeds;
        }

        public static class BookingBuilder {
                private String firstname;
                private String lastname;
                private int totalprice;
                private boolean depositpaid;
                private BookingDates bookingdates;
                private String additionalneeds;

                public BookingBuilder firstname(String firstname){
                       this.firstname = firstname;
                       return this;
                }

                public BookingBuilder lastname(String lastname){
                        this.lastname = lastname;
                        return this;
                }

                public BookingBuilder totalprice(int totalprice){
                        this.totalprice = totalprice;
                        return this;
                }

                public BookingBuilder depositpaid(boolean depositpaid){
                        this.depositpaid = depositpaid;
                        return this;
                }

                public BookingBuilder bookingdates(BookingDates bookingdates){
                        this.bookingdates = bookingdates;
                        return this;
                }

                public BookingBuilder additionalneeds(String additionalneeds){
                        this.additionalneeds = additionalneeds;
                        return this;
                }

                public Booking build() {
                        return new Booking(this);
                }


        }

        public String toString() {
                return "User: "+this.firstname+", "+this.lastname+", "+this.totalprice+", "+this.depositpaid+", "+this.bookingdates + this.additionalneeds;
        }


}
