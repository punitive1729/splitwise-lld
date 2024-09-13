package org.example.models;

import java.util.List;

public class Bill {
    private String id;
    public Bill(String id, String spenderId, double totalAmount, List<Share> shares, long createdAt) {
        this.id = id;
        this.spenderId = spenderId;
        this.totalAmount = totalAmount;
        this.shares = shares;
        this.createdAt = createdAt;
    }
    public String getId() {
        return id;
    }
    public String getSpenderId() {
        return spenderId;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public List<Share> getShares() {
        return shares;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    private String spenderId;
    private double totalAmount;
    private List<Share> shares;
    private long createdAt;
}
