IRS Tax Management System
Developed by: Osmonbaev Aibek EEAIR24 id:240106006

1. Project Description
A Java-based tax record management system featuring:

Role-based access control (Admin/User modes with password protection)

Complete CRUD operations for taxpayer records

Automated tax calculations (totals, dues, payment status)

Comprehensive activity logging with frequency analysis

Persistent JSON storage for both taxpayer data and audit logs

2. Objectives
Objective	Implementation Status
Create/Manage Taxpayer Records	✅ Fully Implemented
Prevent Invalid Data Entry	✅ Automatic id generation 
Ensure Data Persistence	✅ JSON Auto-Save/Load
Generate Activity Reports	✅ freq() + viewActivityLog()
Graceful Error Handling	✅ File I/O 

3. Project Requirements :

  
  1. CRUD Operations:

Create: adminMode() → Option 1 (Add taxpayer with auto-ID generation)

Read: userMode() → View profile, adminMode() → Search taxpayer

Update: adminMode() → Option 3 (Modify income/tax/name)

Delete: adminMode() → Option 4 (Remove by ID)

2. User-Friendly CLI


YOU ARE IN ADMIN MODE  
1. ADD TAXPAYER  
2. VIEW TAXPAYER  
3. UPDATE TAXPAYER  
4. DELETE TAXPAYER  
5. VIEW ACTIVITY LOG  
6. SHOW FREQUENT ACTIONS  




  3. Input Validation
Validates taxpayer ID existence


  4. Data Persistence

taxpayers.json: Stores all taxpayer records

activity_log.json: Immutable audit trail with timestamps

5. Modular Design

mermaid
Copy
classDiagram
    class Taxpayer{
        +id, income, tax...
        +display()
    }
    class LogEntry{
        -timestamp
        -action
        +toString()
    }
    class Main{
        +main()
    }

6. Report Generation

freq(): Identifies top actions (e.g., "LOGIN_ATTEMPT: 12 occurrences")

viewActivityLog(): Displays full audit history

7. Documentation

This README

Presentation

8. Error Handling

Try-catch blocks for file operations
I/O 


9. Authentication and User Roles:

Includes basic authorization

Has different available function for admin and user

10. Project supports Data Export and Import

    provides efficient data export and import using .json files


Documentation:
Algorithms:

Tax Calculation Algorithm:
Automatically computes
Total tax owed (income × tax percentage)
Tax due (total tax - amount paid)
Payment status (Paid/Not Paid/Partially Paid/Overpaid)
Runs whenever taxpayer data is created or updated


Frequency Analysis Algorithm:
Counts how often each system action occurs
Uses a "count and compare" approach to find the most frequent actions
Processes all logged activities to generate reports


Search Algorithm:
Finds taxpayers by scanning through records one by one
Matches either by exact ID or partial name matches

Data Structures:
ArrayLists:
Stores all taxpayer records in order
Holds the complete activity log history
Allows easy addition/removal of items

HashMaps:
Used temporarily to count action frequencies
Provides fast lookups when analyzing logs

LogEntry Objects:
Special containers that hold:
Action descriptions
Timestamps
User information
Keeps all log details organized


Key Functions/Modules:
Taxpayer Management Module:
Create/Read/Update/Delete taxpayer records
Save and load from JSON files
Display taxpayer information
User Interface Module
Admin menu system
User access screens
Input handling


Logging Module:
Records all system activities
Provides log viewing
Generates frequency reports


Calculation Module:
Handles all tax math
Updates payment statuses
Verifies calculations

IMPLEMENTATION:

example 1:
THIS IS IRS TAX MANAGEMENT SYSTEM
ARE YOU AN ADMIN?
YES/NO
yes
ENTER A PASSWORD:
4815162342
YOU ARE SUCCESSFULLY LOGGED IN AS AN ADMIN
WANT DO YOU WANT TO DO?
ADD A TAXPAYER - 1
GET AN INFORMAION OF A TAXPAYER - 2
UPDATE AN INFORMATION OF A TAXPAYER - 3
REMOVE A TAXPAYER - 4
VIEW LOG ACTIVITY -5
SHOW THE MOST FREQUENT OPERATION - 6
3
WHAT DO YOU WANT TO CHANGE?
NAME - 1
TOTAL YEARLY INCOME - 2
TAX PERCENTAGE - 3
SUBMIT TAXES - 4
CHANGE ALL - 5
2
ENTER ID OF A TAXPAYER WHOSE PERSONAL INFO YOU WANT TO CHANGE:
3
ORIGINAL INFORMATION:
Taxpayer ID: 3
Taxpayer Name: Albert Einstein
Taxpayer yearly Total Income: 120000$
Taxpayer TaxPercent: 12%
Taxpayer Total Tax: 14400$
Taxpayer Taxpaid: 0$
Taxpayer Taxdue: 14400$
Taxpayer Status: PARTIALLY PAID
ENTER UPDATED TOTAL INCOME:
60000
Taxpayer with ID 3 updated successfully.
UPDATED INFORMATION:
Taxpayer ID: 3
Taxpayer Name: Albert Einstein
Taxpayer yearly Total Income: 60000$
Taxpayer TaxPercent: 12%
Taxpayer Total Tax: 7200$
Taxpayer Taxpaid: 0$
Taxpayer Taxdue: 7200$
Taxpayer Status: NOT PAID
Taxpayer with ID 3 updated successfully.

Process finished with exit code 0
 
example 2:
THIS IS IRS TAX MANAGEMENT SYSTEM
ARE YOU AN ADMIN?
YES/NO
yes
ENTER A PASSWORD:
4815162342
YOU ARE SUCCESSFULLY LOGGED IN AS AN ADMIN
WANT DO YOU WANT TO DO?
ADD A TAXPAYER - 1
GET AN INFORMAION OF A TAXPAYER - 2
UPDATE AN INFORMATION OF A TAXPAYER - 3
REMOVE A TAXPAYER - 4
VIEW LOG ACTIVITY -5
SHOW THE MOST FREQUENT OPERATION - 6
1
Generated Taxpayer ID: 5
ENTER FULL NAME:
Michael Jackson
ENTER TOTAL YEARLY INCOME:
350000
ENTER TAX  PERCENT:
35
ENTER PAID AMOUNT OF TAX:
50000
JSON file created successfully.
Taxpayer added successfully!
Added Info:
Taxpayer ID: 5
Taxpayer Name: Michael Jackson
Taxpayer yearly Total Income: 350000$
Taxpayer TaxPercent: 35%
Taxpayer Total Tax: 122500$
Taxpayer Taxpaid: 50000$
Taxpayer Taxdue: 72500$
Taxpayer Status: PARTIALLY PAID

Process finished with exit code 0

example 3:
THIS IS IRS TAX MANAGEMENT SYSTEM
ARE YOU AN ADMIN?
YES/NO
no
YOU ARE IN A USER MODE ONLY VIEWING DATA AND TAX SUBMISSION IS ALLOWED
WHAT DO YOU WANT TO DO?
VIEW MY PERSONAL INFO - 1
SUBMIT MY TAXES - 2
2
ENTER YOUR ID:
5
ORIGINAL INFORMATION:
Taxpayer ID: 5
Taxpayer Name: Michael Jackson
Taxpayer yearly Total Income: 350000$
Taxpayer TaxPercent: 35%
Taxpayer Total Tax: 122500$
Taxpayer Taxpaid: 50000$
Taxpayer Taxdue: 72500$
Taxpayer Status: PARTIALLY PAID
ENTER AMOUNT OF USD YOU WANT TO SUBMIT:
72500
Taxpayer with ID 5 updated successfully.
UPDATED INFORMATION:
Taxpayer ID: 5
Taxpayer Name: Michael Jackson
Taxpayer yearly Total Income: 350000$
Taxpayer TaxPercent: 35%
Taxpayer Total Tax: 122500$
Taxpayer Taxpaid: 122500$
Taxpayer Taxdue: 0$
Taxpayer Status: PAID
Taxpayer with ID 5 updated successfully.

Process finished with exit code 0

Files used to store data in the project:
taxpayers.json
activity_log.json



