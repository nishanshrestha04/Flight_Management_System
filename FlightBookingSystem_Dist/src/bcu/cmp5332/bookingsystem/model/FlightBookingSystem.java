package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

/**
 * The FlightBookingSystem class manages flights, customers, and bookings.
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class FlightBookingSystem {

    private final LocalDate systemDate = LocalDate.parse("2020-11-11");
    private final Map<Integer, Customer> customers = new HashMap<>();
    private final Map<Integer, Flight> flights = new HashMap<>();
    private final List<Booking> bookings = new LinkedList<>();

    /**
     * Gets the system date.
     *
     *
     * @return the system date
     */
    public LocalDate getSystemDate() {
        return systemDate;
    }

    /**
     * Gets the list of flights.
     *
     * @return the list of flights
     */
    public List<Flight> getFlights() {
        return new ArrayList<>(flights.values());
    }

    /**
     * Gets the list of customers.
     *
     * @return the list of customers
     */
    public List<Customer> getCustomers() {
        return new ArrayList<>(customers.values());
    }

    /**
     * Gets the list of bookings.
     *
     * @return the list of bookings
     */
    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    /**
     * Gets a flight by its ID.
     *
     * @param id the flight ID
     * @return the flight
     * @throws FlightBookingSystemException if no flight is found with the given ID
     */
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        Flight flight = flights.get(id);
        if (flight == null) {
            throw new FlightBookingSystemException("No flight found with ID: " + id);
        }
        return flight;
    }

    /**
     * Gets a customer by their ID.
     *
     * @param id the customer ID
     * @return the customer
     * @throws FlightBookingSystemException if no customer is found with the given
     *                                      ID
     */
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new FlightBookingSystemException("No customer found with ID: " + id);
        }
        return customer;
    }

    /**
     * Gets a booking by customer ID.
     *
     * @param customerId the customer ID
     * @return the booking
     * @throws FlightBookingSystemException if no booking is found for the given
     *                                      customer ID
     */
    public Booking getBookingByCustomerId(int customerId) throws FlightBookingSystemException {
        return bookings.stream()
                .filter(booking -> booking.getCustomer().getId() == customerId)
                .findFirst()
                .orElseThrow(() -> new FlightBookingSystemException("Booking not found for customer ID " + customerId));
    }

    /**
     * Gets a booking by customer ID and flight ID.
     *
     * @param customerId the customer ID
     * @param flightId   the flight ID
     * @return the booking
     * @throws FlightBookingSystemException if no booking is found for the given
     *                                      customer ID and flight ID
     */
    public Booking getBookingById(int customerId, int flightId) throws FlightBookingSystemException {
        return bookings.stream()
                .filter(booking -> booking.getCustomer().getId() == customerId
                        && booking.getFlight().getId() == flightId)
                .findFirst()
                .orElseThrow(() -> new FlightBookingSystemException(
                        "Booking not found for customer ID " + customerId + " and flight ID " + flightId));
    }

    /**
     * Adds a flight to the system.
     *
     * @param flight the flight to add
     * @throws FlightBookingSystemException if a flight with the same number and
     *                                      departure date already exists
     */
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber())
                    && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    /**
     * Removes a flight from the system.
     *
     * @param flightId the flight ID
     * @throws FlightBookingSystemException if the flight is not found
     */
    public void removeFlight(int flightId) throws FlightBookingSystemException {
        Flight flight = getFlightByID(flightId);
        if (flight != null) {
            flights.remove(flightId);
        } else {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }
    }

    /**
     * Adds a customer to the system.
     *
     * @param customer the customer to add
     * @throws FlightBookingSystemException if a customer with the same ID already
     *                                      exists
     */
    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.putIfAbsent(customer.getId(), customer) != null) {
            throw new FlightBookingSystemException("Customer with ID " + customer.getId() + " already exists.");
        }
    }

    /**
     * Adds a booking to the system.
     *
     * @param booking the booking to add
     * @throws FlightBookingSystemException if the booking already exists or the
     *                                      flight does not exist
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        if (bookings.stream()
                .anyMatch(existing -> existing.getFlight().equals(booking.getFlight())
                        && existing.getBookingDate().equals(booking.getBookingDate())
                        && existing.getCustomer().equals(booking.getCustomer()))) {
            throw new FlightBookingSystemException(
                    "Booking already exists for this flight and customer on the given date.");
        }
        if (!flights.containsKey(booking.getFlight().getId())) {
            throw new FlightBookingSystemException(
                    "Flight with ID " + booking.getFlight().getId() + " does not exist.");
        }
        bookings.add(booking);
    }

    /**
     * Removes a booking from the system.
     *
     * @param booking the booking to remove
     * @throws FlightBookingSystemException if the booking is not found
     */
    public void removeBooking(Booking booking) throws FlightBookingSystemException {
        if (!bookings.remove(booking)) {
            throw new FlightBookingSystemException("Booking not found and cannot be removed.");
        }
    }

    /**
     * Gets the current date.
     *
     * @return the current date
     */
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
