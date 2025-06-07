/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.storage;

import java.util.*;
import com.bookstore.model.*;

public class DataStorage {
    public static Map<Integer, Book> books = new HashMap<>();
    public static Map<Integer, Author> authors = new HashMap<>();
    public static Map<Integer, Customer> customers = new HashMap<>();
    public static Map<Integer, List<CartItem>> carts = new HashMap<>();
    public static Map<Integer, List<Order>> orders = new HashMap<>();

    public static int bookIdCounter = 1;
    public static int authorIdCounter = 1;
    public static int customerIdCounter = 1;
    public static int orderIdCounter = 1;

    static {
        // Sample Authors
        Author author1 = new Author(authorIdCounter++, "J.R.R. Tolkien", "Author of The Hobbit and The Lord of the Rings.");
        Author author2 = new Author(authorIdCounter++, "George Orwell", "Author of 1984 and Animal Farm.");
        Author author3 = new Author(authorIdCounter++, "Harper Lee", "Author of To Kill a Mockingbird.");

        authors.put(author1.getId(), author1);
        authors.put(author2.getId(), author2);
        authors.put(author3.getId(), author3);

        // Sample Books
        Book book1 = new Book(bookIdCounter++, "The Hobbit", author1.getId(), "978-0-261-10333-4", 1937, 25.99, 50);
        Book book2 = new Book(bookIdCounter++, "1984", author2.getId(), "978-0-452-28423-4", 1949, 19.99, 40);
        Book book3 = new Book(bookIdCounter++, "To Kill a Mockingbird", author3.getId(), "978-0-06-112008-4", 1960, 22.99, 30);

        books.put(book1.getId(), book1);
        books.put(book2.getId(), book2);
        books.put(book3.getId(), book3);

        // Sample Customers
        Customer customer1 = new Customer(customerIdCounter++, "Frodo Baggins", "frodo@example.com", "ringbearer");
        Customer customer2 = new Customer(customerIdCounter++, "Harry Potter", "harry@example.com", "expelliarmus");

        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);

        // Sample Carts
        List<CartItem> cart1 = new ArrayList<>();
        cart1.add(new CartItem(book1.getId(), 2));
        cart1.add(new CartItem(book2.getId(), 1));
        carts.put(customer1.getId(), cart1);

        // Sample Orders
        List<OrderItem> orderItems1 = new ArrayList<>();
        orderItems1.add(new OrderItem(book1.getId(), 2, book1.getPrice()));
        orderItems1.add(new OrderItem(book2.getId(), 1, book2.getPrice()));


        Order order1 = new Order(orderIdCounter++, customer1.getId(), orderItems1, new Date(), 77.97);
        List<Order> ordersList1 = new ArrayList<>();
        ordersList1.add(order1);

        orders.put(customer1.getId(), ordersList1);
    }
}
    