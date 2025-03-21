Bank App with Swing and MySQL

A simple bank application featuring a graphical user interface (GUI) developed in Java using the Swing framework. This application leverages MySQL for secure data storage and facilitates essential banking operations. It’s a hands-on project designed to enhance your Java skills while offering practical experience in GUI development and database connectivity.

Features
User Authentication:

Logging into an account with valid credentials.
Registering a new account.
Account Operations:

Depositing funds into your account.
Withdrawing funds from your account.
Checking your current account balance.
Securely logging out.
Transaction Management:

Viewing a history of past transactions.
Transferring funds to other registered users within the MySQL database.
Technologies Used
Java Development Kit (JDK 18): The foundation for building and running the application.
Java Swing: Provides a user-friendly, interactive GUI.
MySQL Database: Manages and persists user account information securely.
MySQL Connector/J: Enables seamless communication between the Java application and the MySQL database.
Getting Started
Prerequisites
JDK 18: Ensure you have JDK 18 or later installed.
MySQL Server: A running MySQL database instance.
MySQL Connector/J: Add the JDBC driver to your project’s classpath.
IDE: Use your favorite IDE (e.g., IntelliJ IDEA, Eclipse) for development.
Installation
Clone the Repository:
Copy code
```
git clone https://github.com/your-username/bank-app.git
```
Configure the Database:

Create a MySQL database named bankingapp.
Create a dbconfig.properties file (ensure it’s added to your .gitignore) with the following content:
properties
Copy code
```
db.url=jdbc:mysql://127.0.0.1:3306/yourDatabase
db.username=username
db.password=yourpassword
```
Build and Run:

Open the project in your IDE or build it with Maven/Gradle.
Compile and run the application to see the bank app in action.
