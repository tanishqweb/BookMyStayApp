import java.util.*;

/*
BookMyStayApp
Use Case 7 – Add-On Service Selection
Version 7.0
*/


/* ROOMS */

abstract class Room{

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds,int size,double price){

        this.beds=beds;
        this.size=size;
        this.price=price;

    }

}


class SingleRoom extends Room{

    public SingleRoom(){

        super(1,250,1500);

    }

}


class DoubleRoom extends Room{

    public DoubleRoom(){

        super(2,400,2500);

    }

}


class SuiteRoom extends Room{

    public SuiteRoom(){

        super(3,750,5000);

    }

}



/* INVENTORY */

class RoomInventory{

    private Map<String,Integer> availability;

    public RoomInventory(){

        availability=new HashMap<>();

        availability.put("Single",5);
        availability.put("Double",3);
        availability.put("Suite",2);

    }

    public Map<String,Integer> getAvailability(){

        return availability;

    }

}



/* RESERVATION */

class Reservation{

    private String guestName;
    private String roomType;
    private String reservationId;

    public Reservation(
            String guestName,
            String roomType,
            String reservationId){

        this.guestName=guestName;
        this.roomType=roomType;
        this.reservationId=reservationId;

    }

    public String getGuestName(){

        return guestName;

    }

    public String getRoomType(){

        return roomType;

    }

    public String getReservationId(){

        return reservationId;

    }

}



/* QUEUE */

class BookingRequestQueue{

    private Queue<Reservation> queue;

    public BookingRequestQueue(){

        queue=new LinkedList<>();

    }

    public void addRequest(Reservation r){

        queue.offer(r);

    }

    public Reservation getNext(){

        return queue.poll();

    }

    public boolean hasRequests(){

        return !queue.isEmpty();

    }

}



/* UC6 ALLOCATION */

class RoomAllocationService{

    private Set<String> allocatedRooms;

    private Map<String,Set<String>> roomsByType;

    public RoomAllocationService(){

        allocatedRooms=new HashSet<>();

        roomsByType=new HashMap<>();

    }

    public void allocateRoom(
            Reservation reservation,
            RoomInventory inventory){

        String type=reservation.getRoomType();

        Map<String,Integer> availability=
                inventory.getAvailability();


        if(availability.get(type)>0){

            String roomId=
                    generateRoomId(type);

            allocatedRooms.add(roomId);

            roomsByType
                    .putIfAbsent(type,new HashSet<>());

            roomsByType
                    .get(type)
                    .add(roomId);


            availability.put(
                    type,
                    availability.get(type)-1);


            System.out.println(
                    "Booking confirmed for "
                            +reservation.getGuestName()
                            +" Room ID: "
                            +roomId);

        }

    }


    private String generateRoomId(String type){

        int count=
                roomsByType
                        .getOrDefault(type,
                                new HashSet<>())
                        .size()+1;

        return type+"-"+count;

    }

}



/* UC7 ADDON SERVICE */

class AddOnService{

    private String serviceName;

    private double cost;

    public AddOnService(
            String serviceName,
            double cost){

        this.serviceName=serviceName;

        this.cost=cost;

    }

    public String getServiceName(){

        return serviceName;

    }

    public double getCost(){

        return cost;

    }

}



/* UC7 MANAGER */

class AddOnServiceManager{

    private Map<String,
            List<AddOnService>> servicesByReservation;

    public AddOnServiceManager(){

        servicesByReservation=new HashMap<>();

    }


    public void addService(
            String reservationId,
            AddOnService service){

        servicesByReservation
                .putIfAbsent(
                        reservationId,
                        new ArrayList<>());

        servicesByReservation
                .get(reservationId)
                .add(service);

    }


    public double calculateTotalServiceCost(
            String reservationId){

        double total=0;

        List<AddOnService> services=
                servicesByReservation.get(reservationId);

        if(services!=null){

            for(AddOnService s:services){

                total+=s.getCost();

            }

        }

        return total;

    }

}



/* MAIN */

public class BookMyStayApp{

    public static void main(String[] args){

        RoomInventory inventory=
                new RoomInventory();

        BookingRequestQueue queue=
                new BookingRequestQueue();

        RoomAllocationService allocation=
                new RoomAllocationService();

        AddOnServiceManager serviceManager=
                new AddOnServiceManager();


        Reservation r1=
                new Reservation(
                        "Abhi",
                        "Single",
                        "Single-1");

        queue.addRequest(r1);


        while(queue.hasRequests()){

            Reservation r=
                    queue.getNext();

            allocation.allocateRoom(r,inventory);

        }


        AddOnService breakfast=
                new AddOnService(
                        "Breakfast",
                        500);

        AddOnService spa=
                new AddOnService(
                        "Spa",
                        1000);


        serviceManager.addService(
                "Single-1",
                breakfast);

        serviceManager.addService(
                "Single-1",
                spa);


        System.out.println("\nAdd-On Service Selection");

        System.out.println(
                "Reservation ID: Single-1");

        System.out.println(
                "Total Add-On Cost: "
                        +serviceManager
                        .calculateTotalServiceCost(
                                "Single-1"));

    }

}