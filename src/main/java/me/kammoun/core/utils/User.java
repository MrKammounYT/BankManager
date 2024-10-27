package me.kammoun.core.utils;

import me.kammoun.core.Enums.Roles;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private String password;
    private int balance;
    private Date accountCreationDate;
    private Roles UserRole;


    public User(int id, String name, String password, int balance, Date accountCreationDate,Roles userRole) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.accountCreationDate = accountCreationDate;
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.name = username;
        this.password = password;
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
