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



    public static void saveToJSONFile(String filename, ArrayList<Taxpayer> taxpayers) {
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
     public static ArrayList<Taxpayer> readJSON(String filename){

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

 }

public class Main {
    public static void main(String[] args) {
        ArrayList<Taxpayer> taxpayers = Taxpayer.readJSON("taxpayers.json");
        taxpayers.add(new Taxpayer(1, "John Doe", 100000, 12, 12000, 5000, 7000, "not paid off"));
        taxpayers.add(new Taxpayer(2, "Jane Smith", 85000, 10, 8500, 8500, 0, "paid"));
        taxpayers.add(new Taxpayer(3, "Carlos Ruiz", 120000, 15, 18000, 10000, 8000, "not paid off"));
        taxpayers.add(new Taxpayer(4, "Mei Lin", 95000, 12, 11400, 11400, 0, "paid"));
        taxpayers.add(new Taxpayer(5, "Amara Patel", 67000, 9, 6030, 3000, 3030, "not paid off"));
        taxpayers.add(new Taxpayer(6, "Liam O'Connor", 72000, 11, 7920, 7920, 0, "paid"));
        taxpayers.add(new Taxpayer(7, "Fatima Zahra", 134000, 16, 21440, 18000, 3440, "not paid off"));
        taxpayers.add(new Taxpayer(8, "Ethan Park", 58000, 8, 4640, 4640, 0, "paid"));
        taxpayers.add(new Taxpayer(9, "Sofia Rossi", 91000, 10, 9100, 6000, 3100, "not paid off"));
        taxpayers.add(new Taxpayer(10, "Tariq Al-Fulan", 105000, 13, 13650, 13000, 650, "not paid off"));


        Taxpayer.saveToJSONFile("taxpayers.json", taxpayers);
        for (Taxpayer s : taxpayers) {
            s.display();

        }


    }
}

