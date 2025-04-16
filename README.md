# IRS Tax Management System

**Developed by:** Osmonbaev Aibek  
**EEAIR24** | **ID: 240106006**

---

## 📘 Project Description

A **Java-based tax record management system** with:

- Role-based access (Admin/User modes with password)
- Full **CRUD** operations on taxpayer records
- **Automated tax calculations** (total, dues, payment status)
- Comprehensive **activity logging** with frequency analysis
- Persistent **JSON storage** for data and logs

---

## 🎯 Objectives

| Objective                     | Status                      |
|------------------------------|-----------------------------|
| Create/Manage Records         | ✅ Fully Implemented         |
| Prevent Invalid Data Entry    | ✅ Auto ID Generation        |
| Ensure Data Persistence       | ✅ JSON Auto-Save/Load       |
| Generate Activity Reports     | ✅ `freq()` + `viewActivityLog()` |
| Graceful Error Handling       | ✅ Try-Catch on I/O          |

---

## ⚙️ Features

### 🔄 CRUD Operations

- **Create**: `adminMode()` → Option 1
- **Read**: `userMode()` / `adminMode()` → Option 2
- **Update**: `adminMode()` → Option 3
- **Delete**: `adminMode()` → Option 4

### 🖥️ Command Line Interface (CLI)

```
YOU ARE IN ADMIN MODE

1. ADD TAXPAYER  
2. VIEW TAXPAYER  
3. UPDATE TAXPAYER  
4. DELETE TAXPAYER  
5. VIEW ACTIVITY LOG  
6. SHOW FREQUENT ACTIONS  
```

### 🧪 Input Validation

- Verifies taxpayer ID exists
- Prevents invalid formats

### 💾 Persistent Data

- `taxpayers.json`: Stores all taxpayer records
- `activity_log.json`: Immutable audit trail with timestamps

### 🧩 Modular Design

```
classDiagram
    class Taxpayer {
        +id
        +income
        +tax
        +display()
    }

    class LogEntry {
        -timestamp
        -action
        +toString()
    }

    class Main {
        +main()
    }
```

### 📊 Report Generation

- `freq()`: Displays most frequent operations
- `viewActivityLog()`: Full audit history

### ⚠️ Error Handling

- Try-catch blocks for file I/O and user input

### 🔐 Authentication

- Admin login with password
- Role-based functionality:
  - **Admin**: Full access
  - **User**: View info + submit taxes

### 🔄 JSON Export/Import

- Efficient `.json` data handling

---

## 🧠 Algorithms

### 📈 Tax Calculation

- `totalTax = income × taxPercent`
- `taxDue = totalTax - taxPaid`
- Status: **PAID**, **PARTIALLY PAID**, **NOT PAID**, **OVERPAID**

### 📊 Frequency Analysis

- Uses HashMap to count system actions
- Identifies top-used commands

### 🔍 Search

- Searches by ID or partial name match

---

## 💾 Data Structures

- **ArrayList**: For taxpayer records and log entries
- **HashMap**: For log frequency analysis
- **LogEntry**: Stores action + timestamp + user info

---

## 🧩 Modules Overview

| Module                | Description                                    |
|-----------------------|------------------------------------------------|
| Taxpayer Management   | CRUD, file I/O, display                        |
| UI Module             | Admin/User CLI navigation                     |
| Logging Module        | Tracks activity + generates frequency reports |
| Calculation Module    | Tax math + payment status update              |

---

## 🔍 Example Use Cases

### 🧪 Update Taxpayer (Admin)

```
→ UPDATE A TAXPAYER - 3  
→ CHANGE TOTAL INCOME - 2  
ID: 3  
Income: 120000 → 60000  
Tax: 12% → Total: $7200  
→ Status: NOT PAID
```

---

### 🧪 Add Taxpayer (Admin)

```
→ ADD A TAXPAYER - 1  
ID: 5  
Name: Michael Jackson  
Income: 350000 | Tax %: 35 | Paid: 50000  
→ Status: PARTIALLY PAID
```

---

### 🧪 Submit Tax (User)

```
→ USER MODE  
→ SUBMIT MY TAXES - 2  
ID: 5  
Submit: 72500  
→ Status: PAID
```

---

## 📁 Files Used

- `taxpayers.json` — All taxpayer records  
- `activity_log.json` — Action logs with timestamps  

---

## ✅ Run Instructions

1. Compile the project in your IDE (e.g., IntelliJ, Eclipse)
2. Run `Main.java`
3. Interact via CLI:
   - Choose **Admin** or **User** mode
   - Follow on-screen options

---

## 📌 Notes

- Ensure both JSON files are in the same directory as the program
- Preloaded passwords (e.g., `4815162342`) can be modified in code

---

Feel free to fork, contribute, or suggest improvements!  
📬 For questions, contact: [your-email@example.com]
![image](https://github.com/user-attachments/assets/9755ade3-89af-433e-b4df-270550a02fbe)
![image](https://github.com/user-attachments/assets/255b155e-b9a1-4f4d-80df-38a49106fbb3)
![image](https://github.com/user-attachments/assets/f5774cfb-6b9b-47fe-ad78-f52379da9985)
![image](https://github.com/user-attachments/assets/3e44facd-9dda-443e-a089-e233ff701aa5)
![image](https://github.com/user-attachments/assets/b9bbcf05-3c9f-4a2d-b08b-ee2a0d87768c)
![image](https://github.com/user-attachments/assets/0daeca0b-8397-4a74-bf52-e06da715da34)
![image](https://github.com/user-attachments/assets/85d72c87-a0ef-42b7-9d5a-899e2270b3b0)







