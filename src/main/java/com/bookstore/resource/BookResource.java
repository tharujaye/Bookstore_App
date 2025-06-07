/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.storage.DataStorage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @POST
    public Response createBook(Book book) {
        book.setId(DataStorage.bookIdCounter++);
        DataStorage.books.put(book.getId(), book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    public List<Book> getAllBooks() {
        return new ArrayList<>(DataStorage.books.values());
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Book book = DataStorage.books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book existingBook = DataStorage.books.get(id);
        if (existingBook == null) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        updatedBook.setId(id);
        DataStorage.books.put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = DataStorage.books.remove(id);
        if (removed == null) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        return Response.noContent().build();
    }
}
