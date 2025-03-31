package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
/**
 * Command to show the details of a specific customer.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class ShowCustomer implements Command {

    private final int customerId;

    /**
     * Constructs a ShowCustomer command with the specified customer ID.
     *
     * @param customerId the ID of the customer to be shown
     */
    public ShowCustomer(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Executes the command to display the details of the customer with the specified ID.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if the customer ID is not found
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = flightBookingSystem.getCustomerByID(customerId);

        StringBuilder output = new StringBuilder();
        output.append("┏━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n")
              .append(String.format("┃ %-14s ┃ %-25s ┃%n", "Customer ID", customer.getId()))
              .append(String.format("┃ %-14s ┃ %-25s ┃%n", "Name", customer.getName()))
              .append(String.format("┃ %-14s ┃ %-25s ┃%n", "Phone Number", customer.getPhone()))
              .append(String.format("┃ %-14s ┃ %-25s ┃%n", "Email", customer.getEmail()))
              .append("┗━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n")
              .append("Bookings:\n");

        if (customer.getBookings().isEmpty()) {
            output.append("No bookings made.\n");
        } else {
            output.append("┏━━━━━━━━━━━━━━━┳━━━━━━━━━━━━┳━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┓\n")
                  .append(String.format("┃ %-13s ┃ %-10s ┃ %-11s ┃ %-15s ┃%n", "Flight Number", "Origin", "Destination", "Departure Date"))
                  .append("┣━━━━━━━━━━━━━━━╋━━━━━━━━━━━━╋━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━┫\n");

            customer.getBookings().forEach(booking -> {
                Flight flight = booking.getFlight();
                output.append(String.format("┃ %-13s ┃ %-10s ┃ %-11s ┃ %-15s ┃%n",
                    flight.getFlightNumber(), flight.getOrigin(), flight.getDestination(), flight.getDepartureDate()));
            });

            output.append("┗━━━━━━━━━━━━━━━┻━━━━━━━━━━━━┻━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┛\n");
        }

        System.out.print(output);
    }
}
