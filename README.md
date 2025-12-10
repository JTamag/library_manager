# library_manager
Object-Oriented Programming class project. A text-based Library Management System written in Java.

## Features

### File Management
- Load and save library data  
- Support for persistent storage  

### Time Management
- Internal clock to simulate library days  
- Time advancement affects requests, fines, and returns  

### Request System
- Create new requests for works  
- Return borrowed works  
- Automatic fine calculation based on delay  

### User Management
- Add, edit, list, and remove users  
- Track borrowed works and penalties  

### Work Management
- Register new works (books, DVDs, etc.)  
- Track availability and request history  

### Fine System
- Automatic fine assignment for late returns  
- Integrated into time + request management  

---
## Main Menu
Even though there's a total of 4 menus for this project i will present the main one that lets you access the rest:
An example of the main menu of the Management System:
<pre>
 Main Menu
  Open File
  Save File
  Display Date
  Advance Date
  Open User Menu
  Open Creations Menu
  Open Requests Menu
  Exit
 Choose an option: 
</pre>

---
The system contains four menus:  
- Main Menu  
- User Management Menu  
- Creations (Works) Menu  
- Requests Menu  

---

## Compilation and Execution

### Compilation

Assuming you are in the directory containing the project root folder `bci` and the `po-uilib.jar` file, the project can be compiled in two ways:

```bash
# Option 1
javac -cp po-uilib.jar:. `find bci -name "*.java"`

# Option 2
find bci -name "*.java" -print | xargs javac -cp po-uilib.jar:.
```
### Execution
```bash
To run the project:
java -cp po-uilib.jar:. bci.app.App

To start the application with an initial state file like ex.im
java -Dimport=ex.im -cp po-uilib.jar:. bci.app.App
```
---
## Contributors

[![Felipe Santana](https://github.com/felipesantana123123.png?size=50)](https://github.com/felipesantana123123)               [Felipe Santana](https://github.com/felipesantana123123)



