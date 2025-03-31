package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Flight;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;

public class FlightTableModel extends AbstractTableModel {

    private final List<Flight> flights;
    private final String[] columnNames = {"Flight No", "Origin", "Destination", "Departure Date", "Capacity", "Price", "Dynamic Price"};

    public FlightTableModel(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public int getRowCount() {
        return flights.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flight flight = flights.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return flight.getFlightNumber();
            case 1:
                return flight.getOrigin();
            case 2:
                return flight.getDestination();
            case 3:
                return flight.getDepartureDate();
            case 4:
                return flight.getCapacity();
            case 5:
                return flight.getPrice();
            case 6:
                return flight.calculatePrice(LocalDate.now());
            default:
                return null;
        }
    }
}
