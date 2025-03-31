package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.data.FlightDataManager;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Command to add a new flight to the flight booking system.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class AddFlight implements Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final int capacity;
    private final double price;
    private final double cancellationRebookFee;
    private final int status;

    /**
     * Constructs an AddFlight command with default status.
     *
     * @param flightNumber the flight number
     * @param origin the origin of the flight
     * @param destination the destination of the flight
     * @param departureDate the departure date of the flight
     * @param capacity the capacity of the flight
     * @param price the price of the flight
     * @param cancellationRebookFee the cancellation/rebooking fee
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double price, double cancellationRebookFee) {
        this(flightNumber, origin, destination, departureDate, capacity, price, cancellationRebookFee, 1);
    }

    /**
     * Constructs an AddFlight command with specified status.
     *
     * @param flightNumber the flight number
     * @param origin the origin of the flight
     * @param destination the destination of the flight
     * @param departureDate the departure date of the flight
     * @param capacity the capacity of the flight
     * @param price the price of the flight
     * @param cancellationRebookFee the cancellation/rebooking fee
     * @param status the status of the flight
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double price, double cancellationRebookFee, int status) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
        this.cancellationRebookFee = cancellationRebookFee;
        this.status = status;
    }

    /**
     * Executes the command to add a new flight.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if there is an error adding the flight
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = flightBookingSystem.getFlights().stream()
                          .mapToInt(Flight::getId)
                          .max()
                          .orElse(0);

        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, capacity, price, cancellationRebookFee, status);
        flightBookingSystem.addFlight(flight);

        FlightDataManager dataManager = new FlightDataManager();
        try {
            dataManager.storeData(flightBookingSystem);
        } catch (IOException e) {
            throw new FlightBookingSystemException("Failed to save flight data: " + e.getMessage());
        }

        System.out.println("Flight #" + flight.getId() + " added and saved to file.");
    }
}
