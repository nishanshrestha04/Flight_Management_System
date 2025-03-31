# Flight Booking System

A Java-based Flight Booking System featuring both a Command-Line Interface (CLI) and a Graphical User Interface (GUI) built using Swing. This system allows administrators and users to manage flights, customers, and bookings, persisting data to text files.

## Description

This project implements a flight booking management system using Object-Oriented Programming (OOP) principles in Java. It provides functionalities for:

*   Managing flight details (adding, viewing, deleting, listing passengers).
*   Managing customer information (adding, viewing, deleting).
*   Handling bookings (creating, cancelling, updating).
*   Persisting data to flat text files (`flights.txt`, `customers.txt`, `bookings.txt`).
*   A choice between a CLI and a Swing-based GUI for interaction.
*   Basic dynamic pricing based on booking proximity to departure.
*   Handling cancellation/rebooking fees.
*   Error handling for invalid operations and data issues.

## Features

*   **Flight Management:**
    *   Add new flights with details like flight number, origin, destination, departure date, capacity, base price, and cancellation/rebooking fee.
    *   List all flights or filter by active/future flights.
    *   View detailed information about a specific flight, including its passenger list.
    *   Mark flights as inactive (deleted) - associated bookings are removed.
    *   View inactive (deleted) flights.
*   **Customer Management:**
    *   Register new customers with ID, name, phone number, and email.
    *   List all active customers.
    *   View detailed information about a specific customer, including their booking history.
    *   Mark customers as inactive (deleted).
    *   View inactive (deleted) customers.
*   **Booking Management:**
    *   Create new bookings linking a customer to a flight.
    *   Checks for flight capacity before booking.
    *   Calculate flight prices dynamically based on how close the booking is to the departure date.
    *   Cancel existing bookings (marks them as cancelled and potentially removes them). Informs user about cancellation fees.
    *   Update/Edit existing bookings (allows changing the flight for a customer's booking, potentially incurring a rebooking fee).
    *   View all current bookings.
*   **Interface Options:**
    *   Operate via a traditional Command-Line Interface (CLI).
    *   Launch a graphical user interface (GUI) using Java Swing.
*   **Data Persistence:**
    *   All flight, customer, and booking data is loaded from and saved to simple text files (`::` separated).
*   **Error Handling:**
    *   Custom exception (`FlightBookingSystemException`) for handling invalid commands and operational errors.

## Technology Stack

*   **Language:** Java
*   **UI:** Java Swing (for GUI), Console (for CLI)
*   **Testing:** JUnit 5 (basic unit tests included)

## Setup and Installation

1.  **Prerequisites:**
    *   Java Development Kit (JDK) installed (version 8 or higher recommended).
    *   An IDE like IntelliJ IDEA, Eclipse, or VS Code (optional, but helpful).
2.  **Get the Code:** Clone the repository or download the `FlightBookingSystem_Dist.zip` file and extract it.
3.  **Compile:** Open a terminal or command prompt, navigate to the `src` directory inside the extracted `FlightBookingSystem_Dist` folder, and compile the Java files:
    ```bash
    javac bcu/cmp5332/bookingsystem/main/Main.java -d ../bin
    # Or compile all .java files if needed (adjust path based on your setup)
    # find . -name "*.java" > sources.txt
    # javac @sources.txt -d ../bin
    ```
    *(Note: Compiling from within an IDE is usually simpler)*
4.  **Ensure Resources:** Make sure the `resources` directory (containing the `data` subdirectory) is accessible from where you run the application. When running from the command line as shown below, it should be in the parent directory of `bin`.

## Usage

### Running the Application

Navigate to the `bin` directory (created during compilation) in your terminal/command prompt and run the main class:

```bash
# From the directory containing the 'bin' and 'resources' folders
java -cp bin bcu.cmp5332.bookingsystem.main.Main
```


*(If running from the root project directory after compiling to bin, use: java -cp bin bcu.cmp5332.bookingsystem.main.Main)*

# Command-Line Interface (CLI)

Once the application starts, you'll see a > prompt. Enter commands as follows:

- help: Display the list of available commands.
- listflights: Show all active flights.
- listcustomers: Show all active customers.
- addflight: Follow prompts to add a new flight.
- addcustomer: Follow prompts to add a new customer.
- showflight [flight id]: Display details for a specific flight.
- showcustomer [customer id]: Display details for a specific customer.
- addbooking [customer id] [flight id]: Create a new booking.
- cancelbooking [customer id] [flight id]: Cancel an existing booking.
- editbooking [customer id] [new flight id]: Change the flight for an existing booking.
- removecustomer: Follow prompt to enter customer ID to mark as removed.
- removeflight: Follow prompt to enter flight ID to mark as inactive.
- loadgui: Close the CLI and launch the GUI.
- exit: Save data and exit the application.

# Graphical User Interface (GUI)
You can launch the GUI directly by running the application and then typing the loadgui command in the CLI.

The GUI provides menus for managing:

- Flights: View all, future, or deleted flights, add/delete flights, show passengers for a selected flight.
- Bookings: Issue, update, or cancel bookings, view all bookings.
- Customers: View active or deleted customers, add/delete customers.

Clicking on table rows often provides more context or triggers related actions (like showing passengers or booking details). Use the "Exit" button in the menu bar to save data and close the application.


# Project Structure
FlightBookingSystem_Dist/  
│  
├── 📁 Java Docs/                 # Generated Javadoc documentation  
│  
├── 📁 resources/                 # Contains application data  
│   └── 📁 data/                  # Stores text-based records  
│       ├── 📄 bookings.txt       # Stores booking information  
│       ├── 📄 customers.txt      # Stores customer information  
│       ├── 📄 flights.txt        # Stores flight information  
│  
├── 📁 src/                       # Source code  
│   └── 📁 bcu/cmp5332/bookingsystem/  
│       ├── 📁 commands/          # Command pattern implementations (AddFlight, AddCustomer, etc.)  
│       ├── 📁 data/              # Data loading & storage logic (DataManager interfaces & implementations)  
│       ├── 📁 gui/               # Java Swing GUI classes (MainWindow, AddFlightWindow, etc.)  
│       ├── 📁 main/              # Main application entry point, CLI parser, custom exceptions  
│       ├── 📁 model/             # Core domain models (Flight, Customer, Booking, FlightBookingSystem)  
│       ├── 📁 test/              # JUnit test classes  
│  
└── (Other project files like `.classpath`, `.project` if using an IDE)  



# Data Format
Data is stored in text files within the resources/data/ directory using :: as a separator.

- `flights.txt: id::flightNumber::origin::destination::departureDate::capacity::price::cancellationRebookFee::status::`
  - `status: 1 = active, 0 = inactive/deleted`
- `customers.txt: id::name::phone::email::status`
  - `status: 1 = active, 0 = inactive/deleted`
- `bookings.txt: customerId::flightId::bookingDate::status`
  - `status: 1 = active, 0 = cancelled`

# Authors
- `Ashlesha Shrestha - Ashlesha.Shrestha@mail.bcu.ac.uk (Student ID: 24128474)`
- `Nishan Shrestha - Nishan.Shrestha@mail.bcu.ac.uk (Student ID: 24128429)`

`Project Supervisor: Sumanta Silwal`

`(Submitted February 2025 for Bachelor of Science with Honours in Computer with Artificial Intelligence, Birmingham City University at Kathmandu, Nepal)`

# Testing
Basic unit tests using JUnit 5 are located in the src/bcu/cmp5332/bookingsystem/test/ directory. These can be run using a compatible IDE or a build tool configured for JUnit.
