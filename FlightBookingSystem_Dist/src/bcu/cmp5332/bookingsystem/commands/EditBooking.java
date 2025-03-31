package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.data.BookingDataManager;

import java.io.IOException;
import java.util.List;

/**
 * Command to edit an existing booking in the flight booking system.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class EditBooking implements Command {
    private final int oldCustomerId;
    private final int newFlightId;

    /**
     * Constructs an EditBooking command.
     *
     * @param oldCustomerId the ID of the customer with the existing booking
     * @param newFlightId the ID of the new flight
     */
    public EditBooking(int oldCustomerId, int newFlightId) {
        this.oldCustomerId = oldCustomerId;
        this.newFlightId = newFlightId;
    }

    /**
     * Executes the command to edit the booking.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs during execution
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {

        Customer oldCustomer = flightBookingSystem.getCustomerByID(oldCustomerId);
        Flight newFlight = flightBookingSystem.getFlightByID(newFlightId);


        Booking booking = flightBookingSystem.getBookingByCustomerId(oldCustomerId);

        if (booking == null) {
            System.out.println("No booking found for customer " + oldCustomer.getName());
            return;
        }

        booking.setFlight(newFlight);

        try {
            BookingDataManager bookingDataManager = new BookingDataManager();

            List<Booking> sortedBookings = flightBookingSystem.getBookings();
            sortedBookings.sort((b1, b2) -> b1.getBookingDate().compareTo(b2.getBookingDate())); 
            
            bookingDataManager.storeData(flightBookingSystem);

            System.out.println("Booking updated for " + oldCustomer.getName() + " to flight " + newFlight.getFlightNumber() + ".");
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error saving updated data to file: " + e.getMessage());
        }
    }
}
