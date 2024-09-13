package org.example.models;

public class Share {
    private String id;
    private String billId;

    public String getId() {
        return id;
    }

    public String getBillId() {
        return billId;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public Share(String id, String billId, String userId, double amount) {
        this.id = id;
        this.billId = billId;
        this.userId = userId;
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private String userId;
    private double amount;
}
