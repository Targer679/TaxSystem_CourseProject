package org.example;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
class Taxpayer {
    int id, totalincome, totaltax, taxpaid, taxdue;
    String fullname;
    String status;
    Taxpayer(int id, String fullname, int totalincome, int totaltax, int taxpaid, int taxdue, String status) {
        this.id = id;
        this.fullname = fullname;
        this.totalincome = totalincome;
        this.totaltax = totaltax;
        this.taxpaid = taxpaid;
        this.taxdue = taxdue;
        this.status = status;
    }
}

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}