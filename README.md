This project is a Bookstore Application designed to simulate a simple e-commerce platform for purchasing books. It involves user registration, book management, shopping cart functionality, and order processing. The system uses several classes, each with distinct responsibilities, and stores data in a relational database with tables like 'users', 'books', 'shopping_cart', 'orders', and 'order_details'.It implements advanced caching, asynchronous processing, and concurrency management using Guava caching, Java Futures, and ExecutorService, making it efficient and scalable.

Key Classes:

1. UserService: Manages user registration and authentication. It allows users to register, login, and handles their credentials securely.
  
2. BookService: Responsible for book management. It allows the addition of books to the catalog and provides search functionality for finding books by title.

3. ShoppingCartService: Handles the shopping cart functionality. Users can add books to their cart, update quantities, remove items, view cart contents, and calculate the total price.

4. OrderService: Manages the order process. It handles order creation, calculates the total cost of items in the cart, and stores the details in the database tables ('orders' and 'order_details').

5. App: The main class that brings everything together. It provides the user interface where users can register, login, add books, view the shopping cart, and checkout. It integrates services like 'UserService', 'BookService', 'ShoppingCartService', and 'OrderService' to offer a cohesive user experience.

In the database, the 'users' table stores user information, the 'books' table maintains the available books, the 'shopping_cart' table tracks items users add to their carts, the 'orders' table records completed orders, and 'order_details' stores the specifics of each book ordered.


Database Architecture:
The database architecture for the Bookstore Application is designed to handle various aspects of an online bookstore, such as user management, book inventory, shopping cart functionality, and order processing. The architecture is relational, using MySQL to store and manage data across several interrelated tables.
