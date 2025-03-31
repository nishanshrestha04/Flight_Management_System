package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to show the details of a specific flight.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class ShowFlight implements Command {

    private final int flightId;

    /**
     * Constructs a ShowFlight command with the specified flight ID.
     *
     * @param flightId the ID of the flight to be shown
     */
    public ShowFlight(int flightId) {
        this.flightId = flightId;
    }
    /**
     * Executes the command to display the details of the flight with the specified ID.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if the flight ID is not found
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Flight flight = flightBookingSystem.getFlightByID(flightId);

        StringBuilder output = new StringBuilder();
        output.append("┏━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n")
              .append(String.format("┃ %-20s ┃ %-29s ┃%n", "Flight ID", flight.getId()))
              .append(String.format("┃ %-20s ┃ %-29s ┃%n", "Flight Number", flight.getFlightNumber()))
              .append(String.format("┃ %-20s ┃ %-29s ┃%n", "Origin", flight.getOrigin()))
              .append(String.format("┃ %-20s ┃ %-29s ┃%n", "Destination", flight.getDestination()))
              .append(String.format("┃ %-20s ┃ %-29s ┃%n", "Departure Date", flight.getDepartureDate()))
              .append(String.format("┃ %-20s ┃ %-29s ┃%n", "Number of Seats", flight.getCapacity()))
              .append(String.format("┃ %-20s ┃ %-29s ┃%n", "Available Seats", flight.getAvailableSeats()))
              .append(String.format("┃ %-20s ┃ %-29s ┃%n", "Price", flight.getPrice()))
              .append("┗━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n")
              .append("Passengers:\n");

        if (flight.getPassengers().isEmpty()) {
            output.append("No passengers on this flight.\n");
        } else {
            output.append("┏━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━┓\n")
                  .append(String.format("┃ %-24s ┃ %-21s ┃%n", "Name", "Phone Number"))
                  .append("┣━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━┫\n");
            flight.getPassengers().forEach(passenger -> {
                output.append(String.format("┃ %-24s ┃ %-21s ┃%n",
                    passenger.getName(), passenger.getPhone()));
            });

            output.append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━┛\n");
        }
        System.out.print(output);
    }
}
