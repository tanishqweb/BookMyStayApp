import java.util.*;

/*
BookMyStayApp
Use Case 10 – Booking Cancellation & Inventory Rollback
Version 10.0
*/


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



/* UC10 CANCELLATION SERVICE */

class CancellationService{

    //reservationID -> roomType
    private Map<String,String> reservationRoomMap;

    //rollback history
    private Stack<String> rollbackStack;

    public CancellationService(){

        reservationRoomMap=new HashMap<>();

        rollbackStack=new Stack<>();

    }


    public void registerBooking(
            String reservationId,
            String roomType){

        reservationRoomMap.put(
                reservationId,
                roomType);

    }


    public void cancelBooking(
            String reservationId,
            RoomInventory inventory){

        if(!reservationRoomMap
                .containsKey(reservationId)){

            System.out.println(
                    "Invalid reservation ID");

            return;

        }

        String roomType=
                reservationRoomMap
                        .get(reservationId);


        Map<String,Integer> availability=
                inventory.getAvailability();


        availability.put(
                roomType,
                availability.get(roomType)+1);


        rollbackStack.push(reservationId);


        reservationRoomMap.remove(
                reservationId);


        System.out.println(
                "Booking cancelled successfully. Inventory restored for room type: "
                        +roomType);

    }


    public void showRollbackHistory(){

        System.out.println(
                "\nRollback History (Most Recent First):");

        while(!rollbackStack.isEmpty()){

            System.out.println(
                    "Released Reservation ID: "
                            +rollbackStack.pop());

        }

    }

}



/* MAIN */

public class BookMyStayApp{

    public static void main(String[] args){

        RoomInventory inventory=
                new RoomInventory();

        CancellationService cancel=
                new CancellationService();


        //simulate confirmed booking
        cancel.registerBooking(
                "Single-1",
                "Single");


        System.out.println(
                "Booking Cancellation");


        cancel.cancelBooking(
                "Single-1",
                inventory);


        cancel.showRollbackHistory();


        System.out.println(
                "\nUpdated Single Room Availability: "
                        +inventory.getAvailability()
                        .get("Single"));

    }

}
