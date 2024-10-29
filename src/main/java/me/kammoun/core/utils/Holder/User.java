package me.kammoun.core.utils.Holder;

import me.kammoun.core.Enums.Roles;

import java.util.Date;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String password;
    private int balance;
    private Date accountCreationDate;
    private Roles UserRole;
    private List<UserTransaction> Transactions;


    public User(int id, String name, String password, int balance, Date accountCreationDate,Roles userRole,List<UserTransaction> transactions) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.accountCreationDate = accountCreationDate;
        this.UserRole = userRole;
        this.Transactions = transactions;
    }

    public User(int id, String username, String password,Roles userRole) {
        this.id = id;
        this.name = username;
        this.password = password;
        this.UserRole = userRole;
    }

    public User(int id, String userName, String password, int balance, Date creationDate, Roles roles) {
        this.id = id;
        this.name = userName;
        this.password = password;
        this.balance = balance;
        this.accountCreationDate = creationDate;

    }


    public void setTransactions(List<UserTransaction> transactions) {
        Transactions = transactions;
    }

    public List<UserTransaction> getTransactions() {
        return Transactions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }

    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public Roles getUserRole() {
        if(UserRole == null)return Roles.USER;
        return UserRole;
    }
}
