import java.util.*;

/*
BookMyStayApp
Use Case 8 – Booking History & Reporting
Version 8.0
*/


/* ROOM */

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

    public Reservation(String guestName,String roomType){

        this.guestName=guestName;
        this.roomType=roomType;

    }

    public String getGuestName(){

        return guestName;

    }

    public String getRoomType(){

        return roomType;

    }

}



/* UC8 BOOKING HISTORY */

class BookingHistory{

    private List<Reservation> confirmedReservations;

    public BookingHistory(){

        confirmedReservations=new ArrayList<>();

    }

    public void addReservation(
            Reservation reservation){

        confirmedReservations.add(reservation);

    }

    public List<Reservation>
    getConfirmedReservations(){

        return confirmedReservations;

    }

}



/* UC8 REPORT */

class BookingReportService{

    public void generateReport(
            BookingHistory history){

        System.out.println(
                "\nBooking History Report");

        for(Reservation r :
                history.getConfirmedReservations()){

            System.out.println(
                    "Guest: "
                            +r.getGuestName()
                            +", Room Type: "
                            +r.getRoomType());

        }

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



/* ALLOCATION */

class RoomAllocationService{

    public void allocateRoom(
            Reservation reservation,
            RoomInventory inventory){

        Map<String,Integer> availability=
                inventory.getAvailability();

        String type=
                reservation.getRoomType();


        if(availability.get(type)>0){

            availability.put(
                    type,
                    availability.get(type)-1);

            System.out.println(
                    "Booking confirmed for "
                            +reservation.getGuestName());

        }

    }

}



/* MAIN */

public class BookMyStayApp{

    public static void main(String[] args){

        RoomInventory inventory=
                new RoomInventory();

        BookingRequestQueue queue=
                new BookingRequestQueue();

        BookingHistory history=
                new BookingHistory();

        BookingReportService report=
                new BookingReportService();

        RoomAllocationService allocation=
                new RoomAllocationService();


        Reservation r1=
                new Reservation("Abhi","Single");

        Reservation r2=
                new Reservation("Subha","Double");

        Reservation r3=
                new Reservation("Vanmathi","Suite");


        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);


        while(queue.hasRequests()){

            Reservation r=
                    queue.getNext();

            allocation.allocateRoom(
                    r,
                    inventory);

            history.addReservation(r);

        }


        System.out.println(
                "\nBooking History and Reporting");

        report.generateReport(history);

    }

}