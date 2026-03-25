import java.util.*;
import java.io.*;

/*
BookMyStayApp
Use Case 12 – Data Persistence & System Recovery
Version 12.0
*/


/* INVENTORY */

class RoomInventory{

    Map<String,Integer> availability=
            new HashMap<>();

    RoomInventory(){

        availability.put("Single",5);
        availability.put("Double",3);
        availability.put("Suite",2);

    }

}



/* PERSISTENCE SERVICE */

class FilePersistenceService{


    public void saveInventory(
            RoomInventory inventory,
            String filePath){

        try{

            PrintWriter writer=
                    new PrintWriter(filePath);

            for(String room:
                    inventory.availability.keySet()){

                writer.println(
                        room+"="
                                +inventory.availability.get(room));

            }

            writer.close();

            System.out.println(
                    "Inventory saved successfully.");

        }

        catch(Exception e){

            System.out.println(
                    "Error saving inventory");

        }

    }



    public void loadInventory(
            RoomInventory inventory,
            String filePath){

        try{

            File file=new File(filePath);

            if(!file.exists()){

                System.out.println(
                        "No valid inventory data found. Starting fresh.");

                return;

            }

            Scanner sc=
                    new Scanner(file);

            while(sc.hasNextLine()){

                String line=
                        sc.nextLine();

                String parts[]=
                        line.split("=");

                inventory.availability.put(
                        parts[0],
                        Integer.parseInt(parts[1]));

            }

            sc.close();

            System.out.println(
                    "Inventory restored.");

        }

        catch(Exception e){

            System.out.println(
                    "Error loading inventory");

        }

    }

}



/* MAIN */

public class BookMyStayApp{

    public static void main(String args[]){

        RoomInventory inventory=
                new RoomInventory();

        FilePersistenceService persistence=
                new FilePersistenceService();


        String filePath="inventory.txt";


        System.out.println(
                "System Recovery");

        persistence.loadInventory(
                inventory,
                filePath);


        System.out.println(
                "\nCurrent Inventory:");

        for(String room:
                inventory.availability.keySet()){

            System.out.println(
                    room+": "
                            +inventory.availability.get(room));

        }


        persistence.saveInventory(
                inventory,
                filePath);

    }

}
