package bcu.cmp5332.bookingsystem.commands;
import bcu.cmp5332.bookingsystem.data.CustomerDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;

/**
 * Command to delete a customer from the flight booking system.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class DeleteCustomer implements Command {

    /**
     * The ID of the customer to be deleted.
     */
    private final int customerId;

    /**
     * Constructs a DeleteCustomer object with the specified customer ID.
     *
     * @param customerId the ID of the customer to be deleted
     */
    public DeleteCustomer(int customerId) {
        this.customerId = customerId;
    }
    /**
     * Executes the command to delete the customer from the flight booking system.
     * It sets the status of the customer to "removed" if it is not already removed.
     *
     * @param flightBookingSystem the flight booking system from which the customer is to be deleted
     * @throws FlightBookingSystemException if an error occurs while deleting the customer
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the customer to remove
        Customer customer = flightBookingSystem.getCustomerByID(customerId);

        // Check if the customer exists
        if (customer == null) {
            System.out.println("Customer #" + customerId + " not found.");
            return;
        }

        // Check if the customer is already marked as removed
        if (customer.getStatus() == 0) {
            System.out.println("The customer is already removed.");
        } else {
            // Set the customer's status to "removed" (0)
            customer.setStatus(0);

            // Output confirmation message
            System.out.println("Customer #" + customerId + " removed successfully.");
            try {
                CustomerDataManager customerDataManager = new CustomerDataManager();
                customerDataManager.storeData(flightBookingSystem);
            } catch (IOException e) {
                throw new FlightBookingSystemException("Error updating customer data in the file: " + e.getMessage());
            }
        }
    }
}
