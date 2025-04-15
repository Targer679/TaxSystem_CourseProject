package org.example;
import java.util.*;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

class Taxpayer {
    public int id, totalincome, totaltax, taxpaid, taxdue, taxpercent;
    public String fullname;
    public String status;

    public Taxpayer() {
    } // Jackson needs this too

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
        } else if (this.taxdue > 0 && this.taxpaid > 0) {
            this.status = "PARTIALLY PAID";
        } else if (this.taxdue < 0) {
            this.status = "OVERPAID";
        }
        else if(this.taxpaid == 0){
            this.status = "NOT PAID";
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

    public static ArrayList<Taxpayer> read(String filename) {

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

    void display() {
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
    public static void userMode(){
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");
        System.out.println("YOU ARE IN A USER MODE ONLY VIEWING DATA AND TAX SUBMISSION IS ALLOWED");
        System.out.println("WHAT DO YOU WANT TO DO?\nVIEW MY PERSONAL INFO - 1\nSUBMIT MY TAXES - 2");
        int choice = input.nextInt();
        if (choice == 1) {
            System.out.println("ENTER ID:");
            int id = input.nextInt();
            boolean found = false;
            for (Taxpayer taxpayer : taxpayers) {
                if (taxpayer.id == id) {
                    taxpayer.display();
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("TAXPAYER NOT FOUND");
            }
        }
        if(choice == 2){

                    Taxpayer.submitTax();

        }
    }
    public static void adminMode() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");
        System.out.println("ENTER A PASSWORD:");
        String password = input.nextLine();
        if (password.equals("4815162342")) {
            System.out.println("YOU ARE SUCCESSFULLY LOGGED IN AS AN ADMIN");
            System.out.println("WANT DO YOU WANT TO DO?\nADD A TAXPAYER - 1\nGET AN INFORMAION OF A TAXPAYER - 2\nUPDATE AN INFORMATION OF A TAXPAYER - 3\nREMOVE A TAXPAYER - 4");
            int ch = input.nextInt();
            if (ch == 1) {
                // Auto-generate unique ID
                int maxId = 0;
                for (Taxpayer t : taxpayers) {
                    if (t.id > maxId) {
                        maxId = t.id;
                    }
                }
                int newId = maxId + 1;

                System.out.println("Generated Taxpayer ID: " + newId);
                input.nextLine(); // consume newline
                System.out.println("ENTER FULL NAME:");
                String name = input.nextLine();
                System.out.println("ENTER TOTAL YEARLY INCOME:");
                int tin = input.nextInt();
                System.out.println("ENTER TAX  PERCENT:");
                int taxprc = input.nextInt();
                System.out.println("ENTER PAID AMOUNT OF TAX:");
                int txpd = input.nextInt();
                Taxpayer t = new Taxpayer(newId, name, tin, taxprc, txpd);
                taxpayers.add(t);
                Taxpayer.save("taxpayers.json", taxpayers);
                System.out.println("Taxpayer added successfully!");
                System.out.println("Added Info:");
                t.display();

            }
            if (ch == 2) {
                Taxpayer.read("taxpayers.json");
                System.out.println("ENTER ID:");
                int id = input.nextInt();
                boolean found = false;
                for (Taxpayer taxpayer : taxpayers) {
                    if (taxpayer.id == id) {
                        taxpayer.display();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("TAXPAYER NOT FOUND");
                }

            }
            if (ch == 3) {
                System.out.println("WHAT DO YOU WANT TO CHANGE?");


                System.out.println("NAME - 1\nTOTAL YEARLY INCOME - 2\nTAX PERCENTAGE - 3\nSUBMIT TAXES - 4\nCHANGE ALL - 5");

                int vv = input.nextInt();
                if (vv == 1) {
                    Taxpayer.changeName();
                }
                else if (vv == 2) {
                    Taxpayer.changeIncome();
                }
                else if (vv == 3) {
                    Taxpayer.changePercent();
                }
                else if (vv == 4) {
                    Taxpayer.changeTax();
                }
                else if (vv == 5) {
                    Taxpayer.changeAll();
                }


            }
            if(ch == 4){
                System.out.println("ENTER ID OF A TAXPAYER WHOSE INFORMATION YPU WANT TO DELETE:");
                int id = input.nextInt();
                boolean found = false;
                for (Taxpayer taxpayer : taxpayers) {
                    if (taxpayer.id == id) {
                        Taxpayer.delete("taxpayers.json", taxpayer.id);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("TAXPAYER NOT FOUND");
                }
            }
        }

        else if (!password.equals("4815162342")) {
            System.out.println("WRONG PASSWORD.");
        }

    }

    public static void changeIncome() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine(); // Consume leftover newline

        boolean found = false;

        for (Taxpayer taxpayer : taxpayers) {
            if (taxpayer.id == id) {
                System.out.println("ORIGINAL INFORMATION:");
                taxpayer.display();

                System.out.println("ENTER UPDATED TOTAL INCOME:");
                int income = input.nextInt();

                Taxpayer updated = new Taxpayer(taxpayer.id, taxpayer.fullname, income, taxpayer.taxpercent, taxpayer.taxpaid);

                Taxpayer.update("taxpayers.json", taxpayer.id, updated);
                System.out.println("UPDATED INFORMATION:");
                updated.display();
                System.out.println("Taxpayer with ID " + taxpayer.id + " updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
        }
    }
    public static void changeName() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine(); // Consume leftover newline

        boolean found = false;

        for (Taxpayer taxpayer : taxpayers) {
            if (taxpayer.id == id) {
                System.out.println("ORIGINAL INFORMATION:");
                taxpayer.display();

                System.out.println("ENTER NEW NAME:");
                String name = input.nextLine();

                Taxpayer updated = new Taxpayer(taxpayer.id, name, taxpayer.totalincome, taxpayer.taxpercent, taxpayer.taxpaid);

                Taxpayer.update("taxpayers.json", taxpayer.id, updated);
                System.out.println("UPDATED INFORMATION:");
                updated.display();
                System.out.println("Taxpayer with ID " + taxpayer.id + " updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
        }
    }
    public static void changePercent() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine(); // Consume leftover newline

        boolean found = false;

        for (Taxpayer taxpayer : taxpayers) {
            if (taxpayer.id == id) {
                System.out.println("ORIGINAL INFORMATION:");
                taxpayer.display();

                System.out.println("ENTER UPDATED TAX PERCENTAGE:");
                int percentage = input.nextInt();

                Taxpayer updated = new Taxpayer(taxpayer.id, taxpayer.fullname, taxpayer.totalincome, percentage, taxpayer.taxpaid);

                Taxpayer.update("taxpayers.json", taxpayer.id, updated);
                System.out.println("UPDATED INFORMATION:");
                updated.display();
                System.out.println("Taxpayer with ID " + taxpayer.id + " updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
        }
    }
    public static void submitTax() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER YOUR ID:");
        int id = input.nextInt();
        input.nextLine(); // Consume leftover newline

        boolean found = false;

        for (Taxpayer taxpayer : taxpayers) {
            if (taxpayer.id == id) {
                System.out.println("ORIGINAL INFORMATION:");
                taxpayer.display();

                System.out.println("ENTER AMOUNT OF USD YOU WANT TO SUBMIT:");
                int tax = input.nextInt();

                Taxpayer updated = new Taxpayer(taxpayer.id, taxpayer.fullname, taxpayer.totalincome, taxpayer.taxpercent, taxpayer.taxpaid+tax);

                Taxpayer.update("taxpayers.json", taxpayer.id, updated);
                System.out.println("UPDATED INFORMATION:");
                updated.display();
                System.out.println("Taxpayer with ID " + taxpayer.id + " updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
        }
    }
    public static void changeAll() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine(); // Consume leftover newline

        boolean found = false;

        for (Taxpayer taxpayer : taxpayers) {
            if (taxpayer.id == id) {
                System.out.println("ORIGINAL INFORMATION:");
                taxpayer.display();

                System.out.println("ENTER UPDATED NAME:");
                String name = input.nextLine();
                System.out.println("ENTER UPDATED TOTAL YEARLY INCOME:");
                int income = input.nextInt();
                System.out.println("ENTER UPDATED TAX PERCENTAGE:");
                int per = input.nextInt();
                System.out.println("ENTER UPDATED AMOUNT OF USD SUBMITTED:");
                int tax = input.nextInt();

                Taxpayer updated = new Taxpayer(taxpayer.id, name, income, per, tax);

                Taxpayer.update("taxpayers.json", taxpayer.id, updated);
                System.out.println("UPDATED INFORMATION:");
                updated.display();
                System.out.println("Taxpayer with ID " + taxpayer.id + " updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
        }
    }
    public static void changeTax() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE TAXES YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine(); // Consume leftover newline

        boolean found = false;

        for (Taxpayer taxpayer : taxpayers) {
            if (taxpayer.id == id) {
                System.out.println("ORIGINAL INFORMATION:");
                taxpayer.display();

                System.out.println("ENTER UPDATED AMOUNT OF SUBMITTED USD:");
                int tax = input.nextInt();

                Taxpayer updated = new Taxpayer(taxpayer.id, taxpayer.fullname, taxpayer.totalincome, taxpayer.taxpercent, tax);

                Taxpayer.update("taxpayers.json", taxpayer.id, updated);
                System.out.println("UPDATED INFORMATION:");
                updated.display();
                System.out.println("Taxpayer with ID " + taxpayer.id + " updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
        }
    }


}

class Main {
    public static void main(String[] args) {
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");
        Scanner input = new Scanner(System.in);
        System.out.println("THIS IS IRS TAX MANAGEMENT SYSTEM");
        System.out.println("ARE YOU AN ADMIN?\nYES/NO");
        String ans = input.nextLine();
        if (ans.equalsIgnoreCase("yes")) {
            Taxpayer.adminMode();
        }

        if (ans.equalsIgnoreCase("no")) {
            Taxpayer.userMode();
        }


    }
}