import java.util.*;

class Flight {
    private String flightName;
    private int availableSeats;
    private int ticketPrice;
    private Map<String, Passenger> bookings;
    private int bookingCounter;

    public Flight(String flightName) {
        this.flightName = flightName;
        this.availableSeats = 50;
        this.ticketPrice = 5000;
        this.bookings = new HashMap<>();
        this.bookingCounter = 0;
    }

    public String bookingTickets(String passengerName, int age, int seats) {
        if (seats <= availableSeats) {
            bookingCounter++;
            String bookingId = "T" + bookingCounter;
            Passenger passenger = new Passenger(bookingId, passengerName, age, seats);
            bookings.put(bookingId, passenger);
            availableSeats -= seats;
            ticketPrice += 200 * seats;
            return bookingId;
        } else {
            System.out.println("Booking failed: seats not available");
            return null;
        }
    }

    public boolean cancelBooking(String bookingId) {
        Passenger passenger = bookings.get(bookingId);
        if (passenger != null) {
            availableSeats += passenger.getSeatsBooked();
            ticketPrice -= 200 * passenger.getSeatsBooked();
            bookings.remove(bookingId);
            System.out.println("Booking canceled successfully.");
            return true;
        } else {
            System.out.println("Cancellation failed: Booking ID not found.");
            return false;
        }
    }

    public void displayDetails() {
        System.out.println("Flight Name: " + flightName);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Ticket Price: " + ticketPrice);
    }

    public void printDetails() {
        System.out.println("Flight Name: " + flightName);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Ticket Price: " + ticketPrice);
        System.out.println("Bookings:");
        for (Passenger passenger : bookings.values()) {
            System.out.println(passenger);
        }
    }
}

class Passenger {
    private String bookingId;
    private String name;
    private int age;
    private int seatsBooked;

    public Passenger(String bookingId, String name, int age, int seatsBooked) {
        this.bookingId = bookingId;
        this.name = name;
        this.age = age;
        this.seatsBooked = seatsBooked;
    }

    public String getBookingId() {
        return bookingId;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    @Override
    public String toString() {
        return "Passenger{bookingId='" + bookingId + "', name='" + name + "', age=" + age + ", seatsBooked=" + seatsBooked + '}';
    }
}

class FlightReservationSystem {
    private Map<String, Flight> flights;

    public FlightReservationSystem() {
        flights = new HashMap<>();
        flights.put("Indigo", new Flight("Indigo"));
        flights.put("Spicejet", new Flight("Spicejet"));
    }

    public boolean hasFlight(String flightName) {
        return flights.containsKey(flightName);
    }

    public String bookTickets(String flightName, String passengerName, int age, int seats) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            return flight.bookingTickets(passengerName, age, seats);
        } else {
            System.out.println("Booking Failed: Flight not found.");
            return null;
        }
    }

    public boolean cancelTickets(String flightName, String bookingId) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            return flight.cancelBooking(bookingId);
        } else {
            System.out.println("Cancellation Failed: Flight not found.");
            return false;
        }
    }

    public void displayFlightDetails(String flightName) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            flight.displayDetails();
        } else {
            System.out.println("Flight not found.");
        }
    }

    public void printFlightDetails(String flightName) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            flight.printDetails();
        } else {
            System.out.println("Flight not found.");
        }
    }
}

public class Main1 {
    public static void main(String[] args) {
        FlightReservationSystem system = new FlightReservationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Flight Reservation System ---");
            System.out.println("1. Book Tickets");
            System.out.println("2. Cancel Tickets");
            System.out.println("3. Print Flight Details");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter flight name (Indigo/Spicejet): ");
                    String flightName = scanner.nextLine();

                    if (system.hasFlight(flightName)) {
                        system.displayFlightDetails(flightName);
                        System.out.print("Enter Passenger Name: ");
                        String passengerName = scanner.nextLine();
                        System.out.print("Enter Passenger Age: ");
                        int age = scanner.nextInt();
                        System.out.print("Enter Seats to be Booked: ");
                        int seats = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        String bookingId = system.bookTickets(flightName, passengerName, age, seats);
                        if (bookingId != null) {
                            System.out.println("Booking Successful! Your Booking ID is: " + bookingId);
                        } else {
                            System.out.println("Booking Failed. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid Flight Name. Please try again.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Flight Name (Indigo/Spicejet): ");
                    flightName = scanner.nextLine();
                    System.out.print("Enter Booking ID: ");
                    String bookingId = scanner.nextLine();
                    system.cancelTickets(flightName, bookingId);
                    break;

                case 3:
                    System.out.print("Enter Flight Name (Indigo/Spicejet): ");
                    flightName = scanner.nextLine();
                    system.printFlightDetails(flightName);
                    break;

                case 4:
                    System.out.println("Exiting System. Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        }
    }
}
