package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Manages the loading and storing of booking data.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class BookingDataManager implements DataManager {

    private static final String RESOURCE = "./resources/data/bookings.txt";
    private static final String SEPARATOR = "::";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        List<String> lines = readLinesFromFile(RESOURCE);
        int lineNumber = 1;

        for (String line : lines) {
            try {
                Booking booking = parseBooking(line, fbs);
                fbs.addBooking(booking);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                throw new FlightBookingSystemException("Error parsing booking data on line " + lineNumber + ": " + ex.getMessage());
            }
            lineNumber++;
        }
    }

    private List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private Booking parseBooking(String line, FlightBookingSystem fbs) throws NumberFormatException, FlightBookingSystemException {
        String[] properties = line.split(SEPARATOR, -1);

        if (properties.length < 4) {
            throw new NumberFormatException("Incomplete booking data.");
        }

        int customerId = Integer.parseInt(properties[0]);
        int flightId = Integer.parseInt(properties[1]);
        LocalDate bookingDate = LocalDate.parse(properties[2]);
        int status = Integer.parseInt(properties[3]);

        Customer customer = fbs.getCustomerByID(customerId);
        Flight flight;
        try {
            flight = fbs.getFlightByID(flightId);
        } catch (FlightBookingSystemException ex) {
            throw new FlightBookingSystemException("Error parsing booking: " + ex.getMessage());
        }

        return new Booking(customer, flight, bookingDate, status);
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        List<String> bookingLines = new ArrayList<>();

        for (Booking booking : fbs.getBookings()) {
            bookingLines.add(formatBooking(booking));
        }

        writeLinesToFile(RESOURCE, bookingLines);
    }

    private String formatBooking(Booking booking) {
        return String.join(SEPARATOR,
                String.valueOf(booking.getCustomer().getId()),
                String.valueOf(booking.getFlight().getId()),
                booking.getBookingDate().toString(),
                String.valueOf(booking.getStatus()));
    }

    private void writeLinesToFile(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
