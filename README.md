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

Suggested Addition: JavaDoc comments for all methods

8. Error Handling

Try-catch blocks for file operations


9. Authentication and User Roles:

Includes basic authorization

Has different available function for admin and user

10. Project supports Data Export and Import

    provides efficient data export and import using .json files


Data Structures

Structure	Purpose	Location

ArrayList	Store taxpayers/log entries	activityLog, taxpayers

HashMap	Count action frequencies	freq() method


Key Methods:
Method	Functionality
logActivity()	Records actions with timestamps
save()/read()	JSON serialization via ObjectMapper
submitTax()	Processes payments + updates status
