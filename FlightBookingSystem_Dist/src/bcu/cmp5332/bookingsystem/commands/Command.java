package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Interface for commands in the flight booking system.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public interface Command {

    public static final String HELP_MESSAGE = "Commands:\n"
        + "\tlistflights                                   print all flights\n"
        + "\tlistcustomers                                 print all customers\n"
        + "\taddflight                                     add a new flight\n"
        + "\taddcustomer                                   add a new customer\n"
        + "\tshowflight [flight id]                        show flight details\n"
        + "\tshowcustomer [customer id]                    show customer details\n"
        + "\taddbooking [customer id] [flight id]          add a new booking\n"
        + "\tcancelbooking [customer id] [flight id]       cancel a booking\n"
        + "\teditbooking [old_customer id] [flight id]     update a booking\n"
        + "\tremovecustomer [customer id]                  remove customer\n"
        + "\tloadgui                                       loads the GUI version of the app\n"
        + "\thelp                                          prints this help message\n"
        + "\texit                                          exits the program";

    /**
     * Executes the command.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs during execution
     */
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException;

}
