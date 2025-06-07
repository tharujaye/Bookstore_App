/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.model.Customer;
import com.bookstore.storage.DataStorage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @POST
    public Response createCustomer(Customer customer) {
        customer.setId(DataStorage.customerIdCounter++);
        DataStorage.customers.put(customer.getId(), customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @GET
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(DataStorage.customers.values());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = DataStorage.customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
        return Response.ok(customer).build();
    }


    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer existingCustomer = DataStorage.customers.get(id);
        if (existingCustomer == null) {
            throw new NotFoundException("Customer not found with ID: " + id);
        }
        updatedCustomer.setId(id);
        DataStorage.customers.put(id, updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer removed = DataStorage.customers.remove(id);
        if (removed == null) {
            throw new NotFoundException("Customer not found with ID: " + id);
        }
        return Response.noContent().build();
    }
}
