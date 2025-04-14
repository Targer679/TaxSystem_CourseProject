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

     public Taxpayer(int id, String fullname, int totalincome, int taxpercent, int taxpaid) {
         this.id = id;
         this.fullname = fullname;
         this.totalincome = totalincome;
         this.taxpercent = taxpercent;
         this.totaltax = (taxpercent * totalincome) / 100;
         this.taxpaid = taxpaid;
         this.taxdue = this.totaltax - this.taxpaid;

         if (this.taxdue == 0) {
             this.status = "PAID";
         } else if (this.taxdue > 0) {
             this.status = "PARTIALLY PAID";
         } else {
             this.status = "OVERPAID";
         }
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
        System.out.println("Taxpayer yearly Total Income: " + totalincome + "$");
         System.out.println("Taxpayer TaxPercent: " + taxpercent + "%");
        System.out.println("Taxpayer Total Tax: " + totaltax + "$");
        System.out.println("Taxpayer Taxpaid: " + taxpaid + "$");
        System.out.println("Taxpayer Taxdue: " + taxdue + "$");
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
        Scanner input = new Scanner(System.in);
        System.out.println("THIS IS IRS TAX MANAGEMENT SYSTEM");
        System.out.println("ARE YOU AN ADMIN?\nYES/NO");
        String ans = input.nextLine();

        if (ans.equalsIgnoreCase("yes")) {
            System.out.println("ENTER A PASSWORD:");
            String password = input.nextLine();
            if (password.equals("4815162342")) {
                System.out.println("YOU ARE SUCCESSFULLY LOGGED IN AS AN ADMIN");
                System.out.println("WANT DO YOU WANT TO DO?\nADD A TAXPAYER - 1\nGET AN INFORMAION OF A TAXPAYER - 2\nUPDATE AN INFORMATION OF A TAXPAYER - 3\nREMOVE A TAXPAYER - 4");
                int ch = input.nextInt();
                if (ch == 1) {
                    System.out.println("ENTER ID:");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.println("ENTER FULL NAME:");
                    String name = input.nextLine();
                    System.out.println("ENTER TOTAL YEARLY INCOME:");
                    int tin = input.nextInt();
                    System.out.println("ENTER TAX  PERCENT:");
                    int taxprc = input.nextInt();
                    System.out.println("ENTER PAID AMOUNT OF TAX:");
                    int txpd = input.nextInt();
                    taxpayers.add(new Taxpayer(id, name, tin, taxprc, txpd));
                    Taxpayer.save("taxpayers.json", taxpayers);
                }
                if (ch == 2) {
                    Taxpayer.read("taxpayers.json");
                    System.out.println("ENTER ID:");
                    int id = input.nextInt();
                    for (Taxpayer taxpayer : taxpayers) {
                        if (taxpayer.id == id) {
                            taxpayer.display();
                        }
                    }

                }
                if (ch == 3) {
                    System.out.println("ENTER ID OF A TAXPAYER WHOSE INFO YOU WANT TO CHANGE:");
                    int id = input.nextInt();
                    Taxpayer.read("taxpayers.json");
                    for (Taxpayer taxpayer : taxpayers) {
                        if (taxpayer.id == id) {
                            taxpayer.display();
                        }
                        System.out.println("WHAT DO YOU WANT TO CHANGE?");
                        System.out.println("ID - 1\n NAME - 2\n TOTAL YEARLY INCOME - 3\n TAX PERCENTAGE - 4\n SUBMIT TAXES -5");
                        int vv = input.nextInt();
                    }


                } else if (!password.equals("4815162342")) {
                    System.out.println("WRONG PASSWORD");
                }
            }
            if (ans.equalsIgnoreCase("no")) {
                System.out.println("YOU ARE IN A USER MODE ONLY VIEWING DATA AND TAX SUBMISSION IS ALLOWED");
            }


        }
    }
}

