package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * Interface for managing data loading and storing.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public interface DataManager {

    public static final String SEPARATOR = "::";

    /**
     * Loads data into the flight booking system.
     *
     * @param fbs the flight booking system
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if a flight booking system error occurs
     */
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException;

    /**
     * Stores data from the flight booking system.
     *
     * @param fbs the flight booking system
     * @throws IOException if an I/O error occurs
     */
    public void storeData(FlightBookingSystem fbs) throws IOException;
}
