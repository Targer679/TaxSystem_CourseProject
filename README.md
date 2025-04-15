IRS Tax Management System
Developed by: Osmonbaev Aibek EEAIR24

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


Data Structures:

Structure	Purpose	Location

ArrayList:	Store taxpayers/log entries	activityLog, taxpayers

HashMap:	Count action frequencies	freq() method


Key Methods:
Method	                Functionality
logActivity()	          Records actions with timestamps
save()/read()	          JSON serialization via ObjectMapper
submitTax()  	          Processes payments + updates status
adminMode()             Menu-driven CRUD operations with authorization
freq()                  Analyzes action frequency
viewActivityLog()       Displays all logged actions

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



