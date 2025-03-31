package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Command to add a new booking to the flight booking system.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class AddBooking implements Command {
    private final int customerId;
    private final int flightId;
    private final LocalDate bookingDate;
    private final int status;

    /**
     * Constructs an AddBooking command.
     *
     * @param customerId the ID of the customer
     * @param flightId the ID of the flight
     * @param bookingDate the date of the booking
     * @param status the status of the booking
     */
    public AddBooking(int customerId, int flightId, LocalDate bookingDate, int status) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    /**
     * Executes the command to add a new booking.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if there is an error adding the booking
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = getCustomer(flightBookingSystem);
        Flight flight = getFlight(flightBookingSystem);

        validateFlightCapacity(flight);

        Booking booking = createBooking(customer, flight);
        flightBookingSystem.addBooking(booking);

        try {
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error saving booking data: " + e.getMessage());
        }

        displayConfirmationMessage(customer, flight);
    }

    private Customer getCustomer(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        return flightBookingSystem.getCustomerByID(customerId);
    }

    private Flight getFlight(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        return flightBookingSystem.getFlightByID(flightId);
    }

    private void validateFlightCapacity(Flight flight) throws FlightBookingSystemException {
        if (flight.getPassengers().size() >= flight.getCapacity()) {
            throw new FlightBookingSystemException("Flight " + flight.getFlightNumber() + " is at full capacity. No more bookings can be made.");
        }
    }

    private Booking createBooking(Customer customer, Flight flight) {
        return new Booking(customer, flight, bookingDate, status);
    }

    private void displayConfirmationMessage(Customer customer, Flight flight) {
        System.out.println("Booking for " + customer.getName() + " added to flight " + flight.getFlightNumber() + ".");
    }
}
