/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.storage.DataStorage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @POST
    public Response createAuthor(Author author) {
        author.setId(DataStorage.authorIdCounter++);
        DataStorage.authors.put(author.getId(), author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    public List<Author> getAllAuthors() {
        return new ArrayList<>(DataStorage.authors.values());
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = DataStorage.authors.get(id);
        if (author == null) {
            throw new NotFoundException("Author not found with ID: " + id);
        }
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        Author existingAuthor = DataStorage.authors.get(id);
        if (existingAuthor == null) {
            throw new NotFoundException("Author not found with ID: " + id);
        }
        updatedAuthor.setId(id);
        DataStorage.authors.put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author removed = DataStorage.authors.remove(id);
        if (removed == null) {
            throw new NotFoundException("Author not found with ID: " + id);
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthor(@PathParam("id") int authorId) {
        return DataStorage.books.values()
                .stream()
                .filter(book -> book.getAuthorId() == authorId)
                .collect(Collectors.toList());
    }
}
