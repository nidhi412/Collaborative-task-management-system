#  Collaborative Task Management System (CTMS)

##  Overview
This project is a **Collaborative Task Management System (CTMS)** developed using Java, JavaFX, and MySQL. It allows users to manage projects and tasks through a graphical interface, while supporting a **client-server architecture** for communication and data handling.

The system enables users to log in, create and manage projects, add tasks, and view team-related information in an organized and interactive environment.

---

##  Features
- User authentication (Login & Signup)  
- Dashboard for viewing tasks  
- Project creation and management  
- Task creation and tracking  
- Team member viewing  
- Multi-screen JavaFX user interface  
- MySQL database integration  
- Client-server communication using sockets  

---

##  Technologies Used

###  Software
- Java  
- JavaFX (UI)  
- MySQL  
- JDBC (Database Connectivity)  
- Eclipse IDE  

###  Concepts
- Object-Oriented Programming (OOP)  
- Client-Server Architecture  
- DAO (Data Access Object) Pattern  
- MVC-style structure  

---

## ⚙️ System Architecture
-JavaFX Client → Controllers → DAO Layer → MySQL Data

---

## ⚙️ How It Works

### 1. User Authentication
- Users can sign up and log in  
- Credentials are validated using the database  

---

### 2. Dashboard
- Displays tasks dynamically  
- Allows navigation to different sections  

---

### 3. Project Management
- Users can create and manage projects  
- Projects are stored in the database  

---

### 4. Task Management
- Users can create tasks within projects  
- Tasks are retrieved and displayed using database queries  

---

### 5. Database Layer (DAO)
- `UserDAO` → handles user data  
- `ProjectDAO` → manages projects  
- `TaskDAO` → manages tasks  

---

### 6. Client-Server Communication
- The client sends commands to the server  
- The server processes requests and returns responses  
- Supports operations like:
  - Login  
  - Add project  
  - Get tasks  

---

##  Repository Structure
src/
├── controllers/ # JavaFX controllers (UI logic)
├── models/ # Data models (User, Task, Project)
├── dao/ # Database access layer (DAO classes)
├── util/ # Utility classes (DB connection)
├── client_server/ # Client-server communication classes
├── *.fxml # UI layout files

##  Notes
- JavaFX setup is required to run the UI  
- MySQL database must be created manually  
- Server must be started before running the client  
- Some features may be partially implemented  

---

##  Future Improvements
- Improve UI design and user experience  
- Add real-time collaboration features  
- Implement notifications  
- Add file sharing and comments  
- Improve security and validation  

---


##  Applications
- Team collaboration tools  
- Project management systems  
- Task tracking applications  
- Software engineering learning projects  
