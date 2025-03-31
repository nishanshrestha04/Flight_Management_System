package bcu.cmp5332.bookingsystem.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * The Flight class represents a flight in the booking system.
 * @author Ashlesha Shrestha
 *
 * @author Nishan Shrestha
 */
public class Flight {

    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private int capacity;
    private double price;
    private double cancellationRebookFee;
    private int status; // Ensure status is correctly handled
    private final List<Customer> passengers = new ArrayList<>();

    /**
     * Constructs a new Flight.
     *
     * @param id                    the flight ID
     * @param flightNumber          the flight number
     * @param origin                the origin of the flight
     * @param destination           the destination of the flight
     * @param departureDate         the departure date of the flight
     * @param capacity              the capacity of the flight
     * @param price                 the price of the flight
     * @param cancellationRebookFee the cancellation/rebook fee
     * @param status                the status of the flight
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int capacity,
            double price, double cancellationRebookFee, int status) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
        this.cancellationRebookFee = cancellationRebookFee;
        this.status = status; // Ensure status is correctly set
    }

    /**
     * Populates the flight with passengers from the booking system.
     *
     * @param fbs the flight booking system
     * @throws FlightBookingSystemException if an error occurs while populating
     */
    public void populate(FlightBookingSystem fbs) throws FlightBookingSystemException {
        try (BufferedReader bookingsReader = new BufferedReader(new FileReader("./resources/data/bookings.txt"))) {
            bookingsReader.lines()
                    .map(line -> line.split("::"))
                    .filter(data -> Integer.parseInt(data[1]) == this.id)
                    .forEach(data -> {
                        try {
                            passengers.add(fbs.getCustomerByID(Integer.parseInt(data[0])));
                        } catch (FlightBookingSystemException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Customer> getPassengers() {
        return passengers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getCancellationRebookFee() {
        return cancellationRebookFee;
    }

    public void setCancellationRebookFee(double cancellationRebookFee) {
        this.cancellationRebookFee = cancellationRebookFee;
    }

    /**
     * Gets a short description of the flight.
     *
     * @return a short description of the flight
     */
    public String getDetailsShort() {
        String flightIcon = "✈️";
        String arrow = "➡️";
        String dateIcon = "📅";

        return String.format(
                "%s Flight #%d | %s %s %s | %s %s",
                flightIcon, id, origin, arrow, destination,
                dateIcon, departureDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
    }

    /**
     * Gets a detailed description of the flight.
     *
     * @return a detailed description of the flight
     */
    public String getDetailsLong() {
        StringBuilder details = new StringBuilder()
                .append("Flight Number: ").append(flightNumber).append("\n")
                .append("Origin: ").append(origin).append("\n")
                .append("Destination: ").append(destination).append("\n")
                .append("Departure Date: ").append(departureDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .append("\n")
                .append("Capacity: ").append(capacity).append("\n")
                .append("Price: ").append(price).append("\n")
                .append("Passengers:\n");
        passengers.forEach(p -> details.append(p.getName()).append("\n"));
        return details.toString();
    }

    /**
     * Adds a passenger to the flight.
     *
     * @param passenger the passenger to add
     * @throws FlightBookingSystemException if the flight is at full capacity
     */
    public void addPassenger(Customer passenger) throws FlightBookingSystemException {
        if (passengers.size() >= capacity) {
            throw new FlightBookingSystemException("Flight is at full capacity. No more bookings can be made.");
        }
        passengers.add(passenger);
    }

    /**
     * Removes a passenger from the flight.
     *
     * @param passenger the passenger to remove
     * @throws FlightBookingSystemException if the passenger is not booked on this
     *                                      flight
     */
    public void removePassenger(Customer passenger) throws FlightBookingSystemException {
        if (!passengers.remove(passenger)) {
            throw new FlightBookingSystemException(
                    "Passenger " + passenger.getName() + " is not booked on this flight.");
        }
    }

    /**
     * Gets the number of available seats on the flight.
     *
     * @return the number of available seats
     */
    public int getAvailableSeats() {
        return capacity - passengers.size();
    }

    /**
     * Calculates the price of the flight based on the booking date.
     * If the booking date is near, the price is increased.
     * @param bookingDate The date of booking.
     * @return The calculated price.
     */
    public double calculatePrice(LocalDate bookingDate) {
        double basePrice = this.price;
        long daysUntilDeparture = ChronoUnit.DAYS.between(bookingDate, this.departureDate);

        if (daysUntilDeparture <= 7) {
            return basePrice * 1.2; // Increase price by 20% if booking is within a week
        } else if (daysUntilDeparture <= 30) {
            return basePrice * 1.1; // Increase price by 10% if booking is within a month
        } else {
            return basePrice;
        }
    }
}
