package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.data.FlightDataManager;
import bcu.cmp5332.bookingsystem.model.Booking;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command to mark a flight as inactive in the flight booking system.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class DeleteFlight implements Command {

    private final int flightId;

    /**
     * Constructs a new DeleteFlight object with the specified flight ID.
     *
     * @param flightId the ID of the flight to be marked as inactive
     */
    public DeleteFlight(int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the command to mark the flight as inactive in the flight booking system and updates the data file.
     *
     * @param flightBookingSystem the flight booking system in which the flight status will be updated
     * @throws FlightBookingSystemException if an error occurs while updating the flight status
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Flight flight = flightBookingSystem.getFlightByID(flightId);
        if (flight != null) {
            // Remove bookings associated with the flight
            List<Booking> bookingsToRemove = flightBookingSystem.getBookings().stream()
                .filter(booking -> booking.getFlight().getId() == flightId)
                .collect(Collectors.toList());

            for (Booking booking : bookingsToRemove) {
                flightBookingSystem.removeBooking(booking);
            }

            flight.setStatus(0);

            FlightDataManager dataManager = new FlightDataManager();
            try {
                dataManager.storeData(flightBookingSystem);
            } catch (IOException e) {
                throw new FlightBookingSystemException("Failed to update flight data: " + e.getMessage());
            }

            System.out.println("Flight #" + flightId + " status set to inactive.");
        } else {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }
    }
}
