package org.example;
import java.util.*;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

 class Taxpayer {
    public int id, totalincome, totaltax, taxpaid, taxdue, taxpercent;
    public String fullname;
    public String status;

    public Taxpayer() {} // Jackson needs this too

    public Taxpayer(int id, String fullname, int totalincome, int taxpercent, int totaltax, int taxpaid, int taxdue, String status) {
        this.id = id;
        this.fullname = fullname;
        this.totalincome = totalincome;
        this.taxpercent = taxpercent;
        this.totaltax = totaltax;
        this.taxpaid = taxpaid;
        this.taxdue = taxdue;
        this.status = status;
    }



    public static void save(String filename, ArrayList<Taxpayer> taxpayers) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            new File("src/main/resources").mkdirs(); // Ensure directory exists
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("src/main/resources/" + filename), taxpayers);
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     public static ArrayList<Taxpayer> read(String filename){

         ObjectMapper objectMapper = new ObjectMapper();
         ArrayList<Taxpayer> taxpayers = new ArrayList<>();
         try {
             // Read JSON file into Student array
             Taxpayer[] taxpayersArray = objectMapper.readValue(new File("src/main/resources/" + filename), Taxpayer[].class);

             // Convert array to List
             Collections.addAll(taxpayers, taxpayersArray);


         } catch (IOException e) {
             e.printStackTrace();
         }
         return taxpayers;
     }
     void display(){
        System.out.println("Taxpayer ID: " + id);
        System.out.println("Taxpayer Name: " + fullname);
        System.out.println("Taxpayer Total Income: " + totalincome);
         System.out.println("Taxpayer TaxPercent: " + taxpercent);
        System.out.println("Taxpayer Total Tax: " + totaltax);
        System.out.println("Taxpayer Taxpaid: " + taxpaid);
        System.out.println("Taxpayer Taxdue: " + taxdue);
         System.out.println("Taxpayer Status: " + status);

     }
     public static void delete(String filename, int idToDelete) {
         ObjectMapper objectMapper = new ObjectMapper();
         File file = new File("src/main/resources/" + filename);

         try {
             // Step 1: Read existing list
             ArrayList<Taxpayer> taxpayers = new ArrayList<>(Arrays.asList(
                     objectMapper.readValue(file, Taxpayer[].class)
             ));

             // Step 2: Remove taxpayer by ID
             taxpayers.removeIf(t -> t.id == idToDelete);

             // Step 3: Save updated list
             objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, taxpayers);
             System.out.println("Taxpayer with ID " + idToDelete + " removed successfully.");

         } catch (IOException e) {
             e.printStackTrace();
         }
     }
     public static void update(String filename, int idToUpdate, Taxpayer updatedTaxpayer) {
         ObjectMapper objectMapper = new ObjectMapper();
         File file = new File("src/main/resources/" + filename);

         try {
             // Step 1: Read existing taxpayers
             ArrayList<Taxpayer> taxpayers = new ArrayList<>(Arrays.asList(
                     objectMapper.readValue(file, Taxpayer[].class)
             ));

             // Step 2: Find and update taxpayer by ID
             boolean found = false;
             for (int i = 0; i < taxpayers.size(); i++) {
                 if (taxpayers.get(i).id == idToUpdate) {
                     taxpayers.set(i, updatedTaxpayer);
                     found = true;
                     break;
                 }
             }

             if (!found) {
                 System.out.println("Taxpayer with ID " + idToUpdate + " not found.");
                 return;
             }

             // Step 3: Save updated list
             objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, taxpayers);
             System.out.println("Taxpayer with ID " + idToUpdate + " updated successfully.");

         } catch (IOException e) {
             e.printStackTrace();
         }
     }

 }

public class Main {
    public static void main(String[] args) {
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");
        Taxpayer updated = new Taxpayer(
                2, "Jane Smith", 90000, 11, 9900, 9900, 0, "paid"
        );
        Taxpayer.save("taxpayers.json", taxpayers);
        Taxpayer.update("taxpayers.json", 2, updated );

    }
}

