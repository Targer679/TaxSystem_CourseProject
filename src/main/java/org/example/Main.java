package org.example;
import java.util.*;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

class LogEntry {
    private String timestamp;
    private String userType;
    private int userId;
    public String action;
    private String details;

    public LogEntry() {}

    public LogEntry(String userType, int userId, String action, String details) {
        this.timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        this.userType = userType;
        this.userId = userId;
        this.action = action;
        this.details = details;
    }


    public String getTimestamp() { return timestamp; }
    public String getUserType() { return userType; }
    public int getUserId() { return userId; }
    public String getAction() { return action; }
    public String getDetails() { return details; }

    @Override
    public String toString() {
        return String.format("[%s] %s (User ID: %d): %s - %s",
                timestamp, userType, userId, action, details);
    }
}

class Taxpayer {
    public int id, totalincome, totaltax, taxpaid, taxdue, taxpercent;
    public String fullname;
    public String status;

    private static ArrayList<LogEntry> activityLog = new ArrayList<>();
    private static final String LOG_FILE = "activity_log.json";

    public Taxpayer() {
    }

    public Taxpayer(int id, String fullname, int totalincome, int taxpercent, int taxpaid) {
        this.id = id;
        this.fullname = fullname;
        this.totalincome = totalincome;
        this.taxpercent = taxpercent;
        this.totaltax = (taxpercent * totalincome) / 100;
        this.taxpaid = taxpaid;
        this.taxdue = this.totaltax - this.taxpaid;

        if (this.taxpaid == 0) {
            this.status = "NOT PAID";
        } else if (this.taxdue == 0) {
            this.status = "PAID";
        } else if (this.taxdue > 0) {
            this.status = "PARTIALLY PAID";
        } else {
            this.status = "OVERPAID";
        }
    }

    // Logging methods
    public static void logActivity(String userType, int userId, String action, String details) {
        LogEntry entry = new LogEntry(userType, userId, action, details);
        activityLog.add(entry);
        saveLogToFile();
    }
    public static void viewLog(){

    }

    public static void saveLogToFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            new File("src/main/resources").mkdirs();
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("src/main/resources/" + LOG_FILE), activityLog);
        } catch (IOException e) {
            System.err.println("Error saving activity log: " + e.getMessage());
        }
    }

    public static void loadLogFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/" + LOG_FILE);
        if (file.exists()) {
            try {
                LogEntry[] entries = objectMapper.readValue(file, LogEntry[].class);
                activityLog.clear();
                Collections.addAll(activityLog, entries);
            } catch (IOException e) {
                System.err.println("Error loading activity log: " + e.getMessage());
            }
        }
    }

    public static void viewActivityLog() {
        loadLogFromFile();
        System.out.println("\nACTIVITY LOG:");
        for (LogEntry entry : activityLog) {
            System.out.println(entry);
        }
        System.out.println();
    }

    public static void save(String filename, ArrayList<Taxpayer> taxpayers) {
        logActivity("SYSTEM", -1, "DATA_SAVE", "Saving taxpayers data");
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
        logActivity("SYSTEM", -1, "DATA_LOAD", "Loading taxpayers data");
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Taxpayer> taxpayers = new ArrayList<>();
        try {
            Taxpayer[] taxpayersArray = objectMapper.readValue(new File("src/main/resources/" + filename), Taxpayer[].class);
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
        logActivity("ADMIN", -1, "DELETE_TAXPAYER", "Attempting to delete taxpayer ID " + idToDelete);
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/" + filename);

        try {
            ArrayList<Taxpayer> taxpayers = new ArrayList<>(Arrays.asList(
                    objectMapper.readValue(file, Taxpayer[].class)
            ));

            taxpayers.removeIf(t -> t.id == idToDelete);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, taxpayers);
            System.out.println("Taxpayer with ID " + idToDelete + " removed successfully.");
            logActivity("ADMIN", -1, "DELETE_SUCCESS", "Successfully deleted taxpayer ID " + idToDelete);
        } catch (IOException e) {
            e.printStackTrace();
            logActivity("ADMIN", -1, "DELETE_FAILED", "Failed to delete taxpayer ID " + idToDelete);
        }
    }

    public static void update(String filename, int idToUpdate, Taxpayer updatedTaxpayer) {
        logActivity("ADMIN", -1, "UPDATE_TAXPAYER", "Attempting to update taxpayer ID " + idToUpdate);
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/" + filename);

        try {
            ArrayList<Taxpayer> taxpayers = new ArrayList<>(Arrays.asList(
                    objectMapper.readValue(file, Taxpayer[].class)
            ));

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
                logActivity("ADMIN", -1, "UPDATE_FAILED", "Taxpayer ID " + idToUpdate + " not found");
                return;
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, taxpayers);
            System.out.println("Taxpayer with ID " + idToUpdate + " updated successfully.");
            logActivity("ADMIN", -1, "UPDATE_SUCCESS", "Successfully updated taxpayer ID " + idToUpdate);
        } catch (IOException e) {
            e.printStackTrace();
            logActivity("ADMIN", -1, "UPDATE_ERROR", "Error updating taxpayer ID " + idToUpdate);
        }
    }
   public static void  freq(){
       loadLogFromFile();
       ArrayList <String> f = new ArrayList<>();
       for (LogEntry entry : activityLog) {
           f.add(entry.action);
       }
       System.out.println("THE MOST FREQUENT ACTION:"+ FrequentStringFinder.findMostFrequentString(f));
   }
    public static void userMode(){
        logActivity("USER", -1, "ENTER_USER_MODE", "User mode activated");
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
                    logActivity("USER", id, "VIEW_PROFILE", "Viewed taxpayer profile");
                    break;
                }
            }
            if (!found) {
                System.out.println("TAXPAYER NOT FOUND");
                logActivity("USER", id, "VIEW_FAILED", "Taxpayer not found");
            }
        }
        if(choice == 2){
            Taxpayer.submitTax();
        }
    }

    public static void adminMode() {
        logActivity("ADMIN", -1, "ADMIN_LOGIN_ATTEMPT", "Admin login attempted");
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");
        System.out.println("ENTER A PASSWORD:");
        String password = input.nextLine();
        if (password.equals("4815162342")) {
            logActivity("ADMIN", -1, "ADMIN_LOGIN_SUCCESS", "Admin logged in successfully");
            System.out.println("YOU ARE SUCCESSFULLY LOGGED IN AS AN ADMIN");
            System.out.println("WANT DO YOU WANT TO DO?\nADD A TAXPAYER - 1\nGET AN INFORMAION OF A TAXPAYER - 2\nUPDATE AN INFORMATION OF A TAXPAYER - 3\nREMOVE A TAXPAYER - 4\nVIEW LOG ACTIVITY -5\nSHOW THE MOST FREQUENT OPERATION - 6");
            int ch = input.nextInt();
            if (ch == 1) {
                int maxId = 0;
                for (Taxpayer t : taxpayers) {
                    if (t.id > maxId) {
                        maxId = t.id;
                    }
                }
                int newId = maxId + 1;

                System.out.println("Generated Taxpayer ID: " + newId);
                input.nextLine();
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
                logActivity("ADMIN", -1, "TAXPAYER_ADDED", "Added new taxpayer ID " + newId);
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
                        logActivity("ADMIN", -1, "TAXPAYER_VIEWED", "Viewed taxpayer ID " + id);
                        break;
                    }
                }
                if (!found) {
                    System.out.println("TAXPAYER NOT FOUND");
                    logActivity("ADMIN", -1, "VIEW_FAILED", "Taxpayer ID " + id + " not found");
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
                    logActivity("ADMIN", -1, "DELETE_FAILED", "Taxpayer ID " + id + " not found");
                }
            }
            else if(ch == 5){
            Taxpayer.viewActivityLog();
            }
            else if(ch == 6){
                Taxpayer.freq();
            }
        }
        else if (!password.equals("4815162342")) {
            System.out.println("WRONG PASSWORD.");
            logActivity("ADMIN", -1, "LOGIN_FAILED", "Invalid admin password");
        }
    }

    public static void changeIncome() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine();

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
                logActivity("ADMIN", -1, "INCOME_UPDATED", "Updated income for taxpayer ID " + id);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
            logActivity("ADMIN", -1, "UPDATE_FAILED", "Taxpayer ID " + id + " not found");
        }
    }

    public static void changeName() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine();

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
                logActivity("ADMIN", -1, "NAME_UPDATED", "Updated name for taxpayer ID " + id);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
            logActivity("ADMIN", -1, "UPDATE_FAILED", "Taxpayer ID " + id + " not found");
        }
    }

    public static void changePercent() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine();

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
                logActivity("ADMIN", -1, "PERCENT_UPDATED", "Updated tax percent for taxpayer ID " + id);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
            logActivity("ADMIN", -1, "UPDATE_FAILED", "Taxpayer ID " + id + " not found");
        }
    }

    public static void submitTax() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER YOUR ID:");
        int id = input.nextInt();
        input.nextLine();

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
                logActivity("USER", id, "TAX_SUBMITTED", "Submitted tax payment of " + tax);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
            logActivity("USER", id, "SUBMIT_FAILED", "Taxpayer ID " + id + " not found");
        }
    }

    public static void changeAll() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine();

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
                logActivity("ADMIN", -1, "FULL_UPDATE", "Updated all fields for taxpayer ID " + id);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
            logActivity("ADMIN", -1, "UPDATE_FAILED", "Taxpayer ID " + id + " not found");
        }
    }

    public static void changeTax() {
        Scanner input = new Scanner(System.in);
        ArrayList<Taxpayer> taxpayers = Taxpayer.read("taxpayers.json");

        System.out.println("ENTER ID OF A TAXPAYER WHOSE TAXES YOU WANT TO CHANGE:");
        int id = input.nextInt();
        input.nextLine();

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
                logActivity("ADMIN", -1, "TAX_UPDATED", "Updated tax payment for taxpayer ID " + id);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("TAXPAYER NOT FOUND");
            logActivity("ADMIN", -1, "UPDATE_FAILED", "Taxpayer ID " + id + " not found");
        }
    }
}
 class FrequentStringFinder {
     public static String findMostFrequentString(List<String> list) {
         if (list == null || list.isEmpty()) {
             return null;
         }

         Map<String, Integer> frequencyMap = new HashMap<>();

         for (String item : list) {
             frequencyMap.put(item, frequencyMap.getOrDefault(item, 0) + 1);
         }

         String mostFrequent = null;
         int maxCount = 0;

         for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
             if (entry.getValue() > maxCount) {
                 mostFrequent = entry.getKey();
                 maxCount = entry.getValue();
             }
         }

         return mostFrequent;
     }

     class Main {
         public static void main(String[] args) {
             Taxpayer.loadLogFromFile();
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
             Taxpayer.saveLogToFile();
         }
     }
 }
class Main {
    public static void main(String[] args) {
        Taxpayer.loadLogFromFile();
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
        Taxpayer.saveLogToFile();
    }
}