package com.coba.jstore_android_reja.Models;

import java.util.ArrayList;

public class Invoice {
    private int id;
    private ArrayList<String> item;
    private String date;
    private int totalPrice;
    private Boolean isActive;
    private Customer customer;
    private int installLmentPeriod, installmentPrice;
    private String invoiceStatus;
    private String invoiceType;
    private String dueDate;

    public Invoice(int id, ArrayList<String> item, String date, int totalPrice, String invoiceStatus) {
        this.id = id;
        this.item = item;
        this.date = date;
        this.totalPrice = totalPrice;
        this.invoiceStatus = invoiceStatus;
    }

    public Invoice(int id, ArrayList<String> item, String date, int totalPrice, String dueDate, String invoiceStatus) {
        this.id = id;
        this.item = item;
        this.date = date;
        this.totalPrice = totalPrice;
        this.dueDate = dueDate;
        this.invoiceStatus = invoiceStatus;
    }

    public Invoice(int id, ArrayList<String> item, String date, int totalPrice, int installLmentPeriod, int installmentPrice, String invoiceStatus) {
        this.id = id;
        this.item = item;
        this.date = date;
        this.totalPrice = totalPrice;
        this.installLmentPeriod = installLmentPeriod;
        this.installmentPrice = installmentPrice;
        this.invoiceStatus = invoiceStatus;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getItem() {
        return item;
    }

    public String getDate() {
        return date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public int getInstallLmentPeriod() {
        return installLmentPeriod;
    }

    public int getInstallmentPrice() {
        return installmentPrice;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public String getDueDate() {
        return dueDate;
    }
}
