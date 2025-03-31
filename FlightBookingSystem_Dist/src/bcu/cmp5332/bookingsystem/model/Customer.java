package bcu.cmp5332.bookingsystem.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * The Customer class represents a customer in the booking system.
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class Customer {

    private int id;
    private String name;
    private String phone;
    private String email;
    private int status;
    private final List<Booking> bookings = new ArrayList<>();

    /**
     * Constructs a new Customer.
     * @param id the customer ID
     * @param name the name of the customer
     * @param phone the phone number of the customer
     * @param email the email address of the customer
     * @param status the status of the customer
     */
    public Customer(int id, String name, String phone, String email, int status) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.phone = Objects.requireNonNull(phone, "Phone cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.setStatus(status);
    }

    /**
     * Populates the customer with bookings from the booking system.
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs while populating
     */
    public void populate(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        try (BufferedReader bookingsReader = new BufferedReader(new FileReader("./resources/data/bookings.txt"))) {
            String bookingLine;
            while ((bookingLine = bookingsReader.readLine()) != null) {
                String[] bookingData = bookingLine.split("::");
                int customerId = Integer.parseInt(bookingData[0]);
                int flightId = Integer.parseInt(bookingData[1]);

                if (customerId == this.id) {
                    Flight flight = flightBookingSystem.getFlightByID(flightId);
                    LocalDate bookingDate = LocalDate.parse(bookingData[2]);
                    int status = Integer.parseInt(bookingData[3]);


                    boolean bookingExists = bookings.stream()
                            .anyMatch(existingBooking -> existingBooking.getFlight().getId() == flightId &&
                                    existingBooking.getBookingDate().equals(bookingDate));

                    if (!bookingExists) {
                        Booking booking = new Booking(this, flight, bookingDate, status);
                        bookings.add(booking);
                    }
                }
            }
        } catch (IOException | NumberFormatException ex) {
            throw new FlightBookingSystemException("Error loading bookings: " + ex.getMessage(), ex);
        }
    }

    // Getters and Setters
    /**
     * Gets the customer ID.
     * @return the customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the customer ID.
     * @param id the customer ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the customer.
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * @param name the name of the customer
     */
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
    }

    /**
     * Gets the phone number of the customer.
     * @return the phone number of the customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     * @param phone the phone number of the customer
     */
    public void setPhone(String phone) {
        this.phone = Objects.requireNonNull(phone, "Phone cannot be null");
    }

    /**
     * Gets the email address of the customer.
     * @return the email address of the customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     * @param email the email address of the customer
     */
    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email, "Email cannot be null");
    }

    /**
     * Gets the list of bookings for the customer.
     * @return the list of bookings
     */
    public List<Booking> getBookings() {
        return new ArrayList<>(bookings); // Return a copy to ensure immutability
    }

    /**
     * Adds a booking to the customer's list of bookings.
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        Objects.requireNonNull(booking, "Booking cannot be null");
        bookings.add(booking);
    }

    /**
     * Cancels a booking for the customer.
     * @param booking the booking to cancel
     */
    public void cancelBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Gets the status of the customer.
     * @return the status of the customer
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of the customer.
     * @param status the status of the customer
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the detailed information of the customer.
     * @return the detailed information of the customer
     */
    public String getDetailsLong() {
        // Check if the customer is removed (status = 0)
        if (status == 0) {
            return "Customer #" + id + " has been removed.\n";
        }

        StringBuilder details = new StringBuilder();

        // Unicode box characters
        String horizontal = "─";
        String vertical = "│";
        String topLeft = "┌", topRight = "┐", bottomLeft = "└", bottomRight = "┘";
        String midLeft = "├", midRight = "┤", midMid = "┼";
        String topMid = "┬", bottomMid = "┴";

        // Table Borders
        String customerHeader = String.format("%s%-12s%s%-15s%s%-25s%s", vertical, "Customer ID", vertical, "Name", vertical, "Email", vertical);
        String bookingHeader = String.format("%s%-12s%s%-15s%s", vertical, "Flight No", vertical, "Departure Date", vertical);

        String customerBorder = topLeft + horizontal.repeat(12) + topMid + horizontal.repeat(15) + topMid + horizontal.repeat(25) + topRight;
        String midBorder = midLeft + horizontal.repeat(12) + midMid + horizontal.repeat(15) + midMid + horizontal.repeat(25) + midRight;
        String bottomBorder = bottomLeft + horizontal.repeat(12) + bottomMid + horizontal.repeat(15) + bottomMid + horizontal.repeat(25) + bottomRight;

        // Header
        details.append(customerBorder).append("\n");
        details.append(customerHeader).append("\n");
        details.append(midBorder).append("\n");

        // Customer Details
        details.append(String.format("%s%-12d%s%-15s%s%-25s%s\n", vertical, id, vertical, name, vertical, email, vertical));
        details.append(bottomBorder).append("\n");

        // Additional Info
        details.append(String.format("Phone: %-15s  Number of Bookings: %d\n", phone, bookings.size()));

        // Bookings Section
        if (bookings.isEmpty()) {
            details.append("╔════════════════════╗\n");
            details.append("║  No Bookings Found ║\n");
            details.append("╚════════════════════╝\n");
        } else {
            String bookingBorder = topLeft + horizontal.repeat(12) + topMid + horizontal.repeat(15) + topRight;
            String bookingBottom = bottomLeft + horizontal.repeat(12) + bottomMid + horizontal.repeat(15) + bottomRight;

            details.append(bookingBorder).append("\n");
            details.append(bookingHeader).append("\n");
            details.append(midBorder.replace(horizontal.repeat(25), horizontal.repeat(15))).append("\n");

            bookings.forEach(booking -> details.append(
                String.format("%s%-12s%s%-15s%s\n",
                    vertical,
                    booking.getFlight().getFlightNumber(),
                    vertical,
                    booking.getFlight().getDepartureDate(),
                    vertical
                )
            ));
            details.append(bookingBottom).append("\n");
        }

        return details.toString();
    }



}
