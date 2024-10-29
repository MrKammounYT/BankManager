package me.kammoun.core.utils.Holder;

import me.kammoun.core.Enums.TransactionType;

import java.time.LocalDateTime;

public class UserTransaction {
    private final TransactionType transactionType;
    private  int id;
    private final int userId;
    private final String ReceiverName;
    private String description;
    private final double amount;
    private final LocalDateTime date;

    public UserTransaction(int id, String transactionType, int userId,String ReceiverName ,String description, double amount, LocalDateTime date) {
        this.id = id;
        this.transactionType = TransactionType.valueOf(transactionType);
        this.userId = userId;
        this.ReceiverName = ReceiverName;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
    public UserTransaction(String transactionType, int userId,String ReceiverName ,String description, double amount, LocalDateTime date) {
        this.transactionType = TransactionType.valueOf(transactionType);
        this.userId = userId;
        this.ReceiverName = ReceiverName;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public int getId() { return id; }

    public int getUserId() { return userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }

    public LocalDateTime getDate() { return date; }

    public String getReceiverName() {
        return ReceiverName;
    }

    public TransactionType getTransactionType() {
        if(transactionType == null)return TransactionType.NULL;
        return transactionType;
    }
}
