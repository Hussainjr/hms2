package com.hms2.payload;

import com.hms2.Entity.City;
import com.hms2.Entity.Country;

public class PropertyDto {

    private Long id;

    private Integer noOfBathroom;

    private Integer noOfBedroom;

    private Integer noOfGuest;

    private String name;

    private City city;

    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoOfBathroom() {
        return noOfBathroom;
    }

    public void setNoOfBathroom(Integer noOfBathroom) {
        this.noOfBathroom = noOfBathroom;
    }

    public Integer getNoOfBedroom() {
        return noOfBedroom;
    }

    public void setNoOfBedroom(Integer noOfBedroom) {
        this.noOfBedroom = noOfBedroom;
    }

    public Integer getNoOfGuest() {
        return noOfGuest;
    }

    public void setNoOfGuest(Integer noOfGuest) {
        this.noOfGuest = noOfGuest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
