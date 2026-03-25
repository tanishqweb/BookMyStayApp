import java.util.*;

/*
BookMyStayApp
Use Case 9 – Error Handling & Validation
Version 9.0
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



/* CUSTOM EXCEPTION (UC9) */

class InvalidBookingException
        extends Exception{

    public InvalidBookingException(
            String message){

        super(message);

    }

}



/* VALIDATOR */

class ReservationValidator{

    public void validate(
            String guest,
            String roomType,
            RoomInventory inventory)

            throws InvalidBookingException{

        if(guest==null ||
                guest.trim().isEmpty()){

            throw new InvalidBookingException(
                    "Guest name cannot be empty");

        }

        if(!inventory.getAvailability()
                .containsKey(roomType)){

            throw new InvalidBookingException(
                    "Invalid room type selected");

        }

        if(inventory.getAvailability()
                .get(roomType)<=0){

            throw new InvalidBookingException(
                    "Room not available");

        }

    }

}



/* RESERVATION */

class Reservation{

    private String guest;
    private String roomType;

    public Reservation(
            String guest,
            String roomType){

        this.guest=guest;
        this.roomType=roomType;

    }

    public String getGuest(){

        return guest;

    }

    public String getRoomType(){

        return roomType;

    }

}



/* QUEUE */

class BookingRequestQueue{

    private Queue<Reservation> queue;

    public BookingRequestQueue(){

        queue=new LinkedList<>();

    }

    public void addRequest(
            Reservation r){

        queue.offer(r);

    }

    public Reservation getNext(){

        return queue.poll();

    }

}



/* MAIN */

public class BookMyStayApp{

    public static void main(String[] args){

        Scanner scanner=
                new Scanner(System.in);

        RoomInventory inventory=
                new RoomInventory();

        ReservationValidator validator=
                new ReservationValidator();

        BookingRequestQueue queue=
                new BookingRequestQueue();

        try{

            System.out.println(
                    "Booking Validation");

            System.out.print(
                    "Enter guest name: ");

            String guest=
                    scanner.nextLine();

            System.out.print(
                    "Enter room type (Single/Double/Suite): ");

            String type=
                    scanner.nextLine();


            validator.validate(
                    guest,
                    type,
                    inventory);


            Reservation reservation=
                    new Reservation(
                            guest,
                            type);

            queue.addRequest(reservation);


            System.out.println(
                    "Booking request accepted");


        }

        catch(InvalidBookingException e){

            System.out.println(
                    "Booking failed: "
                            +e.getMessage());

        }

        finally{

            scanner.close();

        }

    }

}
