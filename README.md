# MyMarket

MyMarket is a Java-based desktop application for managing a simple marketplace. It features user authentication (login/register), product management, and order processing, with a graphical user interface (GUI).

## Features
- User login and registration (see screenshot below)
- Admin and customer roles
- Product and category management
- Order creation and management
- Data persistence using text files

### Login/Register GUI
![Login/Register GUI](../mymarket-4165-3747/Screenshot%202025-06-06%20173138.png)

## How to Run
1. Ensure you have Java (JDK 8 or later) installed.
2. Compile the project:
   ```bash
   javac -cp lib/junit-4.13.1.jar;lib/hamcrest-core-1.3.jar -d bin src/**/*.java
   ```
3. Run the application:
   ```bash
   java -cp bin Main
   ```

## Folder Structure
```
c:/Users/Mike/MyMarket/MyMarket/
  src/           # Java source code
    api/         # Core logic (User, Product, Order, etc.)
    gui/         # GUI classes (LoginFrame, RegisterFrame, etc.)
  lib/           # External libraries (JUnit, Hamcrest)
  test/          # Unit tests
  users.txt      # User data
  products.txt   # Product data
  categories.txt # Category data
```

## Dependencies
- Java JDK 8 or later
- JUnit 4.13.1 (for testing)
- Hamcrest Core 1.3 (for testing)

## Authors
- Mike (and contributors)

---
Feel free to contribute or open issues for suggestions and bug reports!
