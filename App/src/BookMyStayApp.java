import java.util.*;

/*
BookMyStayApp
Use Case 11 – Concurrent Booking Simulation
Version 11.0
*/


/* BOOKING REQUEST */

class BookingRequest{

    String guestName;
    String roomType;

    BookingRequest(String guestName,String roomType){

        this.guestName=guestName;
        this.roomType=roomType;

    }

}



/* BOOKING QUEUE */

class BookingRequestQueue{

    Queue<BookingRequest> queue=new LinkedList<>();

    public void addRequest(BookingRequest r){

        queue.add(r);

    }

    public BookingRequest getRequest(){

        return queue.poll();

    }

}



/* INVENTORY */

class RoomInventory{

    Map<String,Integer> availability=new HashMap<>();

    RoomInventory(){

        availability.put("Single",5);
        availability.put("Double",3);
        availability.put("Suite",2);

    }

}



/* ALLOCATION SERVICE */

class RoomAllocationService{

    public void allocateRoom(
            BookingRequest req,
            RoomInventory inventory){

        int rooms=
                inventory.availability.get(req.roomType);

        if(rooms>0){

            inventory.availability.put(
                    req.roomType,
                    rooms-1);

            System.out.println(
                    "Booking confirmed for Guest: "
                            +req.guestName+
                            ", Room Type: "
                            +req.roomType);

        }
        else{

            System.out.println(
                    "No rooms available for "
                            +req.guestName);

        }

    }

}



/* CONCURRENT PROCESSOR */

class ConcurrentBookingProcessor
        implements Runnable{

    BookingRequestQueue queue;

    RoomInventory inventory;

    RoomAllocationService service;


    ConcurrentBookingProcessor(

            BookingRequestQueue queue,
            RoomInventory inventory,
            RoomAllocationService service){

        this.queue=queue;
        this.inventory=inventory;
        this.service=service;

    }


    public void run(){

        BookingRequest req;

        synchronized(queue){

            req=queue.getRequest();

        }

        if(req!=null){

            synchronized(inventory){

                service.allocateRoom(
                        req,
                        inventory);

            }

        }

    }

}



/* MAIN */

public class BookMyStayApp{

    public static void main(String[] args)
            throws Exception{

        BookingRequestQueue queue=
                new BookingRequestQueue();

        RoomInventory inventory=
                new RoomInventory();

        RoomAllocationService service=
                new RoomAllocationService();


        queue.addRequest(
                new BookingRequest("Abhi","Single"));

        queue.addRequest(
                new BookingRequest("Vanmathi","Double"));

        queue.addRequest(
                new BookingRequest("Kunal","Suite"));

        queue.addRequest(
                new BookingRequest("Subha","Single"));


        Thread t1=
                new Thread(
                        new ConcurrentBookingProcessor(
                                queue,
                                inventory,
                                service));

        Thread t2=
                new Thread(
                        new ConcurrentBookingProcessor(
                                queue,
                                inventory,
                                service));


        System.out.println(
                "Concurrent Booking Simulation");


        t1.start();

        t2.start();


        t1.join();
        t2.join();


        System.out.println(
                "\nRemaining Inventory:");

        System.out.println(
                "Single: "
                        +inventory.availability.get("Single"));

        System.out.println(
                "Double: "
                        +inventory.availability.get("Double"));

        System.out.println(
                "Suite: "
                        +inventory.availability.get("Suite"));

    }

}