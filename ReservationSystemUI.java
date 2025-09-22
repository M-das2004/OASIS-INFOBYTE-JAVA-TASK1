import java.util.*;

// Reservation class: stores individual booking details
class Reservation {
    private int pnr;
    private String passengerName;
    private String trainNo;
    private String trainName;
    private String classType;
    private String journeyDate;
    private String source;
    private String destination;

    public Reservation(int pnr, String passengerName, String trainNo, String trainName,
                       String classType, String journeyDate, String source, String destination) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.source = source;
        this.destination = destination;
    }

    public int getPnr() {
        return pnr;
    }

    public String toString() {
        return "PNR: " + pnr + ", Name: " + passengerName + ", Train: " + trainNo + " - " + trainName +
                ", Class: " + classType + ", Date: " + journeyDate +
                ", From: " + source + " To: " + destination;
    }
}

// ReservationSystem class: manages reservations
class ReservationSystem {
    private List<Reservation> reservations = new ArrayList<>();
    private int nextPnr = 1001; // starting PNR

    public Reservation makeReservation(String name, String trainNo, String trainName,
                                       String classType, String date, String source, String destination) {
        Reservation reservation = new Reservation(nextPnr++, name, trainNo, trainName,
                                                  classType, date, source, destination);
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation getReservationByPnr(int pnr) {
        for (Reservation r : reservations) {
            if (r.getPnr() == pnr) return r;
        }
        return null;
    }

    public boolean cancelReservation(int pnr) {
        Reservation reservation = getReservationByPnr(pnr);
        if (reservation != null) {
            reservations.remove(reservation);
            return true;
        }
        return false;
    }
}

// ReservationSystemUI class: handles user interaction
class ReservationSystemUI {
    private ReservationSystem reservationSystem = new ReservationSystem();
    private Scanner scanner = new Scanner(System.in);

    // Login method
    public void login() {
        System.out.print("Enter Login ID: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (loginId.equals("admin") && password.equals("admin")) {
            System.out.println("Login Successful!\n");
            start();
        } else {
            System.out.println("Invalid Login. Try again.\n");
            login();
        }
    }

    // Main menu
    public void start() {
        while (true) {
            System.out.println("\n--- Online Reservation System ---");
            System.out.println("1. Make a reservation");
            System.out.println("2. View all reservations");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Passenger Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Train No: ");
                    String trainNo = scanner.nextLine();
                    System.out.print("Train Name: ");
                    String trainName = scanner.nextLine();
                    System.out.print("Class Type: ");
                    String classType = scanner.nextLine();
                    System.out.print("Journey Date: ");
                    String date = scanner.nextLine();
                    System.out.print("Source: ");
                    String source = scanner.nextLine();
                    System.out.print("Destination: ");
                    String destination = scanner.nextLine();

                    Reservation r = reservationSystem.makeReservation(name, trainNo, trainName,
                                                                      classType, date, source, destination);
                    System.out.println("Reservation made successfully. PNR: " + r.getPnr());
                    break;

                case 2:
                    System.out.println("\n--- All Reservations ---");
                    if (reservationSystem.getReservations().isEmpty()) {
                        System.out.println("No reservations found.");
                    } else {
                        for (Reservation res : reservationSystem.getReservations()) {
                            System.out.println(res);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter PNR to cancel: ");
                    int pnr = scanner.nextInt();
                    if (reservationSystem.cancelReservation(pnr)) {
                        System.out.println("Reservation Cancelled Successfully.");
                    } else {
                        System.out.println("PNR not found.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting... Thank you!");
                    return;

                default:
                    System.out.println("Invalid Choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        ReservationSystemUI ui = new ReservationSystemUI();
        ui.login();
    }
}