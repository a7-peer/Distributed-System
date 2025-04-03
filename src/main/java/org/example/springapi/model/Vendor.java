package org.example.springapi.model;


public class Vendor {

    private String name;
    private String address;
    private String phone;
    private int id;
    public Vendor() {
    }


    public Vendor(String name, int id, String address, String phone) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }







}
