/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

import com.bookstore.model.CartItem;
import com.bookstore.model.Book;
import com.bookstore.storage.DataStorage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") int customerId, CartItem cartItem) {
        List<CartItem> cart = DataStorage.carts.getOrDefault(customerId, new ArrayList<>());
        cart.add(cartItem);
        DataStorage.carts.put(customerId, cart);
        return Response.status(Response.Status.CREATED).entity(cartItem).build();
    }

    @GET
    public List<CartItem> getCartItems(@PathParam("customerId") int customerId) {
        return DataStorage.carts.getOrDefault(customerId, new ArrayList<>());
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") int customerId,
                                   @PathParam("bookId") int bookId,
                                   CartItem updatedItem) {
        List<CartItem> cart = DataStorage.carts.get(customerId);
        if (cart == null) {
            throw new NotFoundException("Cart not found for customer ID: " + customerId);
        }

        for (CartItem item : cart) {
            if (item.getBookId() == bookId) {
                item.setQuantity(updatedItem.getQuantity());
                return Response.ok(item).build();
            }
        }
        throw new NotFoundException("Book ID not found in cart: " + bookId);
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(@PathParam("customerId") int customerId,
                                   @PathParam("bookId") int bookId) {
        List<CartItem> cart = DataStorage.carts.get(customerId);
        if (cart == null) {
            throw new NotFoundException("Cart not found for customer ID: " + customerId);
        }

        boolean removed = cart.removeIf(item -> item.getBookId() == bookId);
        if (!removed) {
            throw new NotFoundException("Book ID not found in cart: " + bookId);
        }
        return Response.noContent().build();
    }
}
