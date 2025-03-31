package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * Command to add a new customer to the flight booking system.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class AddCustomer implements Command {
    private final int id;
    private final String name;
    private final String phone;
    private final String email;
    /**
     * Constructs an AddCustomer command.
     *
     * @param id the ID of the customer
     * @param name the name of the customer
     * @param phone the phone number of the customer
     * @param email the email address of the customer
     */
    public AddCustomer(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    /**
     * Executes the command to add a new customer.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if there is an error adding the customer
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = new Customer(this.id, name, phone, email, 1);
        flightBookingSystem.addCustomer(customer);
        System.out.println("Customer #" + customer.getId() + " added.");

        // Persist the data to file
        try {
            FlightBookingSystemData.store(flightBookingSystem);
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error while saving customer data: " + e.getMessage());
        }
    }
}
