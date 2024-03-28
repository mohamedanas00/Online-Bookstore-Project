# Online Bookstore Application

- This is an online bookstore application implemented using Java SE sockets and multithreading for server-client communication. The application allows users to browse, search, borrow, and lend books. It also includes features like user authentication, book inventory management, request handling, and error handling. MySQL is used as the database to store book and user information.

## Features

- Server-Client Communication: Implements communication between the server and clients using Java SE sockets to handle user requests and responses.

- Book Inventory Management: Allows the server to maintain a MySQL database of available books, including details such as title, author, genre, price, quantity, and list of clients who have this book.

- User Authentication: Implements user authentication mechanisms for secure access to the bookstore's features, including login and registration.

- Browse and Search Books: Enables users to browse through the bookstore's catalog, search for specific books by title, author, genre, and view detailed information about each book.

- Add and Remove Books: Users can add books they wish to lend and specify details. They can also remove books they no longer want to lend.

- Submit a Request: Users can submit borrowing requests to other users. Once accepted, they can start chatting to finalize the borrowing process.

- Accept / Reject a Request: Users can check and respond to incoming borrowing requests, either accepting or rejecting them.

- Request History: Provides users with access to their request history, allowing them to view and track statuses (accepted, rejected, or pending).

- Library Overall Statistics: Admin can view the overall status of the library, including current borrowed books, available books, and request statuses.

- Error Handling: Implements error handling mechanisms to deal with various scenarios, such as invalid user inputs.

- Review Book Feature: Users can review books based on a specific user-defined format. When reviewing a book, users can provide various data such as rating, comments.

- Accumulated Rating Calculation: Upon receiving a review for a book, the server calculates the overall accumulated rating for that book based on all the reviews received. Additionally, the server may store additional review information as per the user-defined review format.

- Genre-based Book Recommendations: Users can now view a list of books from each genre, sorted by their current reviews' calculation. This feature provides users with recommendations based on the accumulated ratings of books in each genre. Other recommendations may also be based on the user's personal genre preferences or the preferences of those whom they mostly borrow from.

## Setup

- Java SE Development Kit (JDK).
- MySQL Connector/J library.
  [MySQL Connector Java 8.0.30](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.30)
- not need to download MySQL Server (project using Cloud DB).
  [console.clever-cloud](https://console.clever-cloud.com/)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/mohamedanas00/Online-Bookstore-Project.git
```

2. Open the project in your preferred Java IDE.
3. Compile and run the server application (`Server.java`).
4. Compile and run the client application (`MainClient.java`).
