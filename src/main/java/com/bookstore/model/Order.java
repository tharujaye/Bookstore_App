/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.model;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private List<OrderItem> orderItems;
    private Date orderDate;
    private double totalPrice;
    
    public Order() {
    }

    public Order(int id, int customerId, List<OrderItem> orderItems, Date orderDate, double totalPrice) {

        this.id = id;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
