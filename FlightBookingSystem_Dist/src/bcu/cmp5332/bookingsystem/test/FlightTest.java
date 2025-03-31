package bcu.cmp5332.bookingsystem.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;

import java.time.LocalDate;
import java.util.Arrays;

public class FlightTest {

    private Flight flight;
    private Customer customer1;
    private Customer customer2;

    @Before
    public void setUp() {
        customer1 = new Customer(1, "John Doe", "john@example.com", "123456789", 1);
        customer2 = new Customer(2, "Jane Doe", "jane@example.com", "987654321", 1);
        LocalDate departureDate = LocalDate.of(2024, 2, 15);
        flight = new Flight(1, "FL123", "New York", "London", departureDate, 150, 500.00, 10.0, 1);
    }

    @Test
    public void testAddPassenger() throws FlightBookingSystemException {
        flight.addPassenger(customer1);
        flight.addPassenger(customer2);
        assertEquals(Arrays.asList(customer1, customer2), flight.getPassengers());
    }

    @Test
    public void testRemovePassenger() throws FlightBookingSystemException {
        flight.addPassenger(customer1);
        flight.removePassenger(customer1);
        assertFalse(flight.getPassengers().contains(customer1));
    }

    @Test(expected = FlightBookingSystemException.class)
    public void testRemoveNonExistentPassenger() throws FlightBookingSystemException {
        flight.removePassenger(customer1);
    }

    @Test
    public void testGetDetailsLong() throws FlightBookingSystemException {
        flight.addPassenger(customer1);
        
        String expectedDetails = "Flight Number: FL123\n" +
                "Origin: New York\n" +
                "Destination: London\n" +
                "Departure Date: 15/02/2024\n" +
                "Capacity: 150\n" +
                "Price: 500.0\n" +
                "Passengers:\n" +
                "John Doe\n";
        
        assertEquals(expectedDetails, flight.getDetailsLong());
    }
}
