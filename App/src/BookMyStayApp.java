import java.util.*;

class Room {
    int roomId;
    String type;
    double price;
    boolean available;

    Room(int roomId, String type, double price, boolean available) {
        this.roomId = roomId;
        this.type = type;
        this.price = price;
        this.available = available;
    }
}

class BookingService {
    private Map<Integer, Room> inventory = new HashMap<>();
    private Queue<Integer> bookingQueue = new LinkedList<>();

    public BookingService() {
        inventory.put(1, new Room(1, "Single", 1000, true));
        inventory.put(2, new Room(2, "Double", 2000, true));
        inventory.put(3, new Room(3, "Suite", 4000, true));
    }

    public void requestBooking(int roomId) {
        if (inventory.containsKey(roomId)) {
            bookingQueue.add(roomId);
            System.out.println("Booking request added to queue");
        } else {
            System.out.println("Invalid room ID");
        }
    }

    public void processBooking() {
        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests");
            return;
        }

        int roomId = bookingQueue.poll();
        Room room = inventory.get(roomId);

        if (room.available) {
            room.available = false;
            System.out.println("Room " + roomId + " booked successfully");
        } else {
            System.out.println("Room " + roomId + " is not available");
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingService service = new BookingService();

        while (true) {
            System.out.println("1. Request Booking");
            System.out.println("2. Process Booking");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.println("Enter room ID:");
                int id = sc.nextInt();
                service.requestBooking(id);
            } else if (choice == 2) {
                service.processBooking();
            } else if (choice == 3) {
                break;
            }
        }
    }
}