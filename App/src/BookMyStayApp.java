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

class RoomService {
    private Map<Integer, Room> inventory = new HashMap<>();

    public RoomService() {
        inventory.put(1, new Room(1, "Single", 1000, true));
        inventory.put(2, new Room(2, "Double", 2000, true));
        inventory.put(3, new Room(3, "Suite", 4000, false));
        inventory.put(4, new Room(4, "Single", 1200, true));
    }

    public List<Room> search(String type, double maxPrice) {
        List<Room> result = new ArrayList<>();
        for (Room room : inventory.values()) {
            if (room.available && room.type.equalsIgnoreCase(type) && room.price <= maxPrice) {
                result.add(room);
            }
        }
        return result;
    }

    public boolean checkAvailability(int roomId) {
        Room room = inventory.get(roomId);
        if (room == null) return false;
        return room.available;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomService service = new RoomService();

        System.out.println("Enter room type:");
        String type = sc.nextLine();

        System.out.println("Enter max price:");
        double price = sc.nextDouble();

        List<Room> rooms = service.search(type, price);

        if (rooms.isEmpty()) {
            System.out.println("No rooms found");
        } else {
            System.out.println("Available Rooms:");
            for (Room r : rooms) {
                System.out.println("Room ID: " + r.roomId + ", Type: " + r.type + ", Price: " + r.price);
            }
        }

        System.out.println("Enter room ID to check availability:");
        int id = sc.nextInt();

        if (service.checkAvailability(id)) {
            System.out.println("Room is available");
        } else {
            System.out.println("Room is not available");
        }
    }
}