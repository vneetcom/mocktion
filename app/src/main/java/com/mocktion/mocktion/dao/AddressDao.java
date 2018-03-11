package com.mocktion.mocktion.dao;

/**
 * Created by user on 2018/03/11.
 */

public class AddressDao {

    private static AddressDao singleton = new AddressDao();

    private AddressDao(){
    }

    public static AddressDao getInstance(){
        return singleton;
    }

    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

}
