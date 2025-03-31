package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Manages the loading and storing of customer data.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class CustomerDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/customers.txt";
    private static final String SEPARATOR = "::";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int lineNumber = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int customerId = Integer.parseInt(properties[0]);
                    String customerName = properties[1];
                    String customerPhoneNumber = properties[2];
                    String email = properties[3];
                    int status = Integer.parseInt(properties[4]);

                    Customer customer = new Customer(customerId, customerName, customerPhoneNumber, email, status);
                    fbs.addCustomer(customer);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    throw new FlightBookingSystemException("Unable to parse customer data on line " + lineNumber
                            + "\nError: " + ex.getMessage());
                }
                lineNumber++;
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.print(customer.getStatus());
                out.println();
            }
        }
    }
}
