package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

/**
 * The Booking class represents a booking in the flight booking system.
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class Booking {

    private static int nextId = 1;
    private int id;
    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    private int status;

    /**
     * Constructs a new Booking.
     * @param customer the customer making the booking
     * @param flight the flight being booked
     * @param bookingDate the date of the booking
     * @param status the status of the booking
     */
    public Booking(Customer customer, Flight flight, LocalDate bookingDate, int status) {
        this.id = nextId++;
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    /**
     * Gets the booking ID.
     * @return the booking ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the customer making the booking.
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer making the booking.
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the flight being booked.
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight being booked.
     * @param flight the flight
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Gets the date of the booking.
     * @return the booking date
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the date of the booking.
     * @param bookingDate the booking date
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Gets the status of the booking.
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the booking.
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
