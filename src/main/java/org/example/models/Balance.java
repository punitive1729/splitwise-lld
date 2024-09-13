package org.example.models;

public class Balance {
    private String senderId;
    private String receiverId;
    public Balance(String senderId, String receiverId, double amount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }
    public String getSenderId() {
        return senderId;
    }
    public String getReceiverId() {
        return receiverId;
    }
    public double getAmount() {
        return amount;
    }
    private double amount;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void printStatement() {
        System.out.println(receiverId+" owes "+senderId+" "+amount);
    }
}
