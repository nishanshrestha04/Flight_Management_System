package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import java.io.IOException;

/**
 * Command to cancel a booking in the flight booking system.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class CancelBooking implements Command {

    private final int customerId;
    private final int flightId;

    /**
     * Constructs a CancelBooking command.
     *
     * @param customerId the ID of the customer
     * @param flightId   the ID of the flight
     */
    public CancelBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /**
     * Executes the command to cancel the booking.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs during execution
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the booking to cancel
        Booking booking = flightBookingSystem.getBookingById(customerId, flightId);

        // Check if booking exists
        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        // Check if the booking is already canceled
        if (booking.getStatus() == 0) {
            System.out.println("The booking is already canceled.");
        } else {
            // Set the status of the booking to "cancel"
            booking.setStatus(0);

            // Optionally, remove the booking from the system
            flightBookingSystem.removeBooking(booking);

            // Save the updated data to the file
            try {
                BookingDataManager bookingDataManager = new BookingDataManager();
                bookingDataManager.storeData(flightBookingSystem); // Save updated data to file

                System.out.println("Booking canceled successfully.");
            } catch (IOException e) {
                throw new FlightBookingSystemException("Error saving updated data to file: " + e.getMessage());
            }
        }
    }
}
