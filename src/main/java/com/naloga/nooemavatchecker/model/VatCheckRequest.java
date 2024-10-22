package com.naloga.nooemavatchecker.model;


public class VatCheckRequest {
    private String countryCode;
    private String vatNumber;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getVatNumber(){
        return vatNumber;
    }

    public void setVatNumber(String vatNumber){
        this.vatNumber = vatNumber;
    }
}
