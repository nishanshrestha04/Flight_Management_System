package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the loading and storing of the entire flight booking system data.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class FlightBookingSystemData {

    private static final List<DataManager> dataManagers = new ArrayList<>();

    static {
        dataManagers.add(new FlightDataManager());
        dataManagers.add(new CustomerDataManager());
        dataManagers.add(new BookingDataManager());
    }

    /**
     * Loads the flight booking system data.
     *
     * @return the flight booking system
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if a flight booking system error occurs
     */
    public static FlightBookingSystem load() throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        try {
            for (DataManager dm : dataManagers) {
                dm.loadData(fbs);
            }

            List<Customer> customersList = fbs.getCustomers();
            for (Customer customer : customersList) {
                customer.populate(fbs);
            }

            List<Flight> flightsList = fbs.getFlights();
            for (Flight flight : flightsList) {
                flight.populate(fbs);
            }
        } catch (FlightBookingSystemException ex) {
            throw new FlightBookingSystemException("Error loading flight booking system data: " + ex.getMessage());
        }
        return fbs;
    }

    /**
     * Stores the flight booking system data.
     *
     * @param fbs the flight booking system
     * @throws IOException if an I/O error occurs
     */
    public static void store(FlightBookingSystem fbs) throws IOException {
        for (DataManager dm : dataManagers) {
            dm.storeData(fbs);
        }
    }
}
