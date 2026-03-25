import java.util.*;

class Room {
    int roomId;
    String type;
    boolean available;

    Room(int roomId, String type, boolean available) {
        this.roomId = roomId;
        this.type = type;
        this.available = available;
    }
}

class Reservation {
    int reservationId;
    int roomId;
    String customerName;

    Reservation(int reservationId, int roomId, String customerName) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.customerName = customerName;
    }
}

class ReservationService {
    private Map<Integer, Room> rooms = new HashMap<>();
    private Map<Integer, Reservation> reservations = new HashMap<>();
    private int reservationCounter = 1;

    public ReservationService() {
        rooms.put(1, new Room(1, "Single", true));
        rooms.put(2, new Room(2, "Double", true));
        rooms.put(3, new Room(3, "Suite", true));
    }

    public void confirmReservation(int roomId, String name) {
        Room room = rooms.get(roomId);

        if (room == null) {
            System.out.println("Invalid room ID");
            return;
        }

        if (!room.available) {
            System.out.println("Room not available");
            return;
        }

        room.available = false;
        Reservation res = new Reservation(reservationCounter++, roomId, name);
        reservations.put(res.reservationId, res);

        System.out.println("Reservation confirmed");
        System.out.println("Reservation ID: " + res.reservationId);
        System.out.println("Room ID: " + roomId);
        System.out.println("Customer Name: " + name);
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
            return;
        }

        for (Reservation r : reservations.values()) {
            System.out.println("Reservation ID: " + r.reservationId +
                    ", Room ID: " + r.roomId +
                    ", Customer: " + r.customerName);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ReservationService service = new ReservationService();

        while (true) {
            System.out.println("1. Confirm Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("Enter room ID:");
                int roomId = sc.nextInt();
                sc.nextLine();

                System.out.println("Enter customer name:");
                String name = sc.nextLine();

                service.confirmReservation(roomId, name);
            } else if (choice == 2) {
                service.viewReservations();
            } else if (choice == 3) {
                break;
            }
        }
    }
}