# 🌸 Online Quiz Application (Java Socket + GUI) 🌸

[![Java](https://img.shields.io/badge/Language-Java-orange?style=for-the-badge)](https://www.java.com/)
[![GitHub Repo](https://img.shields.io/badge/Repo-Online_Quiz_App-blue?style=for-the-badge)](https://github.com/laviee143/SocketQuizApp)
[![License](https://img.shields.io/badge/License-Educational-brightgreen?style=for-the-badge)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Ready-brightgreen?style=for-the-badge)](#)

---

## **Project Overview**
This is a **socket-based client-server quiz application** developed in **Java**.  
It allows multiple students to connect to a server and take a **5-question multiple-choice quiz**.  

The client features a **beautiful Swing GUI** with a **pink theme**, large input fields, and **animated pink confetti** for high scores.  
Each student can attempt the quiz **only once**, ensuring fair play.

---

## **Features**
- ✅ Socket-based **client-server communication**  
- ✅ GUI Client using **Java Swing**  
- ✅ **5 multiple-choice questions** with scoring  
- ✅ Large, pink-themed **student name input box**  
- ✅ **Submit answers** via GUI buttons  
- ✅ **Score calculation and display**  
- ✅ **Fancy pink result display with confetti** for high scores  
- ✅ **Single attempt per student**  
- ✅ **Multithreaded server** to handle multiple clients  

---

## **Technologies Used**
- **Java** (JDK 8+)  
- **Sockets** (`Socket`, `ServerSocket`)  
- **Swing GUI** (`JFrame`, `JPanel`, `JLabel`, `JButton`, `JRadioButton`)  
- **Multithreading** (`Thread`)  
- **Collections** (`HashSet`) for attempt tracking  
- **Animation** (`javax.swing.Timer`) for confetti effect  

---

## **Project Structure**

SocketQuizApp/
│
├── QuizServer.java # Server-side code
├── ClientHandler.java # Handles individual client connections
├── QuizClient.java # Client-side GUI code
├── README.md # Project documentation
└── screenshots/ # Optional folder for GUI screenshots


---

## **Setup Instructions**

 ## 1. Clone the repository
    ```bash
git clone https://github.com/laviee143/SocketQuizApp.git
cd SocketQuizApp
 ## 2. Compile the Java files

javac QuizServer.java ClientHandler.java QuizClient.java

### 3. Run the Server
java QuizServer

### 4. Run the Client

Open a new terminal for each client:
java QuizClient

### 5. Using the App

Enter your name in the large pink input box.

Answer the 5 multiple-choice questions.

Click Submit Answer for each question.

At the end, view your score in a pink-themed result box.

High scorers (≥4) see animated pink confetti.

Note: Each student can attempt the quiz only once.

### Screenshots

Name Input:

<img width="557" height="274" alt="image" src="https://github.com/user-attachments/assets/e387dbc8-e80a-49ff-900a-2b461d99bf00" />

Quiz Question:

<img width="1199" height="836" alt="image" src="https://github.com/user-attachments/assets/4e9fad3b-009b-4a57-94ea-6f286c6ee870" />

High Score Result:

<img width="1191" height="826" alt="image" src="https://github.com/user-attachments/assets/3800b55a-ac3c-4e3e-a918-6ec7cb09938c" />


### Future Improvements

  - Load questions from a database or external file

  - Add more questions dynamically

  - Persist student attempts to a file or database

  - Enhance GUI with icons, images, and animations

### Author

Jerusalem Girma

📧 Email: laviee1434@gmail.com

💼 LinkedIn:[ Jerusalem Girma](http://linkedin.com/in/jerusalem-girma-a3771b375)

🐱 GitHub:[ @laviee143](https://github.com/laviee143)
