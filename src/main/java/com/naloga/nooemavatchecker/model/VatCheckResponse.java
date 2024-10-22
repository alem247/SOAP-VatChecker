package com.naloga.nooemavatchecker.model;

public class VatCheckResponse {

    private String companyName;
    private String address;

    public VatCheckResponse(String companyName, String address) {
            this.companyName = companyName;
            this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }
}
