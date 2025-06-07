package com.bookstore.resource;

import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import com.bookstore.model.Customer;
import com.bookstore.storage.DataStorage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @POST
    public Response createOrder(@PathParam("customerId") int customerId) {
        
        Customer customer = DataStorage.customers.get(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        List<CartItem> cart = DataStorage.carts.get(customerId);
        if (cart == null || cart.isEmpty()) {
            throw new NotFoundException("Cart is empty or not found for customer ID: " + customerId);
        }

        double totalAmount = cart.stream()
                .mapToDouble(item -> {
                    Book book = DataStorage.books.get(item.getBookId());
                    if (book == null) {
                        throw new NotFoundException("Book not found with ID: " + item.getBookId());
                    }
                    return book.getPrice() * item.getQuantity();
                }).sum();

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart) {
            Book book = DataStorage.books.get(cartItem.getBookId());
            if (book == null) {
                throw new NotFoundException("Book not found with ID: " + cartItem.getBookId());
            }
            orderItems.add(new OrderItem(cartItem.getBookId(), cartItem.getQuantity(), book.getPrice()));
        }

        Order order = new Order(
            DataStorage.orderIdCounter++, 
            customerId, 
            orderItems, 
            new Date(), 
            totalAmount
        );

        List<Order> customerOrders = DataStorage.orders.getOrDefault(customerId, new ArrayList<>());
        customerOrders.add(order);
        DataStorage.orders.put(customerId, customerOrders);

        
        DataStorage.carts.remove(customerId);

        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @GET
    public Response getAllOrders(@PathParam("customerId") int customerId) {
        Customer customer = DataStorage.customers.get(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        List<Order> orders = DataStorage.orders.getOrDefault(customerId, new ArrayList<>());
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId,
                                 @PathParam("orderId") int orderId) {
        Customer customer = DataStorage.customers.get(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        List<Order> customerOrders = DataStorage.orders.get(customerId);
        if (customerOrders == null) {
            throw new NotFoundException("No orders found for customer ID: " + customerId);
        }

        for (Order order : customerOrders) {
            if (order.getId() == orderId) {
                return Response.ok(order).build();
            }
        }

        throw new NotFoundException("Order not found with ID: " + orderId);
    }
}
