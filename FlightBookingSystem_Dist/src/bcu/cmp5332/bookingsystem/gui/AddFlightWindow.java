package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * The AddFlightWindow class provides a graphical user interface for adding a new flight.
 * It allows the user to enter the flight details and then add the flight to the system.
 * This class implements the ActionListener interface to handle button clicks.
 *
 * @author Ashlesha Shrestha
 * @author Nishan Shrestha
 */
public class AddFlightWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField flightNoText = new JTextField();
    private JTextField originText = new JTextField();
    private JTextField destinationText = new JTextField();
    private JTextField depDateText = new JTextField();
    private JTextField capacityText = new JTextField();
    private JTextField priceText = new JTextField();
    private JTextField cancellationRebookFee = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs a new AddFlightWindow object with a reference to the main window.
     * @param mw The MainWindow object representing the main window of the application.
     */
    public AddFlightWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the contents of the frame, setting up the layout and components.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle exception if setting look and feel fails
        }

        setTitle("Add a New Flight");

        setSize(400, 300);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(8, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel flightNoLabel = new JLabel("Flight No ");
        flightNoLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Increase tooltip text size
        flightNoLabel.setToolTipText("Enter the Flight Number");
        topPanel.add(flightNoLabel);
        topPanel.add(flightNoText);
        JLabel originLabel = new JLabel("Origin ");
        originLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Increase tooltip text size
        originLabel.setToolTipText("Enter the Origin");
        topPanel.add(originLabel);
        topPanel.add(originText);
        JLabel destinationLabel = new JLabel("Destination ");
        destinationLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Increase tooltip text size
        destinationLabel.setToolTipText("Enter the Destination");
        topPanel.add(destinationLabel);
        topPanel.add(destinationText);
        JLabel depDateLabel = new JLabel("Departure Date (YYYY-MM-DD): ");
        depDateLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Increase tooltip text size
        depDateLabel.setToolTipText("Enter the Departure Date in YYYY-MM-DD format");
        topPanel.add(depDateLabel);
        topPanel.add(depDateText);
        JLabel capacityLabel = new JLabel("Capacity ");
        capacityLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Increase tooltip text size
        capacityLabel.setToolTipText("Enter the Capacity");
        topPanel.add(capacityLabel);
        topPanel.add(capacityText);
        JLabel priceLabel = new JLabel("Price ");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setToolTipText("Enter the Price");
        topPanel.add(priceLabel);
        topPanel.add(priceText);
        JLabel cancellationRebookFeeLabel = new JLabel("Cancellation/Rebook Fee ");
        cancellationRebookFeeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        cancellationRebookFeeLabel.setToolTipText("Enter the Cancellation/Rebook Fee");
        topPanel.add(cancellationRebookFeeLabel);
        topPanel.add(cancellationRebookFee);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    /**
     * Handles action events generated by buttons in the window.
     * @param ae The ActionEvent object representing the action event.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addFlight();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    /**
     * Adds the flight based on the user input.
     * Prompts for confirmation before adding the flight.
     * Displays error messages if any error occurs during the addition process.
     */
    private void addFlight() {
        try {
            String flightNumber = flightNoText.getText();
            String origin = originText.getText();
            String destination = destinationText.getText();
            LocalDate departureDate = LocalDate.parse(depDateText.getText());
            int capacity = Integer.parseInt(capacityText.getText());
            double price = Double.parseDouble(priceText.getText());
            double cancellationRebookPrice = Double.parseDouble(cancellationRebookFee.getText());
            int status = 1;

            // Create and execute the AddFlight Command
            Command addFlight = new AddFlight(flightNumber, origin, destination, departureDate, capacity, price, cancellationRebookPrice, status);
            addFlight.execute(mw.getFlightBookingSystem());

            // Refresh the entire GUI
            mw.refreshGUI();

            // Hide (close) the AddFlightWindow
            this.setVisible(false);
        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(this, "Date must be in YYYY-MM-DD format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Capacity and Price must be valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
