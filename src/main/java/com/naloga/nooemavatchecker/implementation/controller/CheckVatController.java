package com.naloga.nooemavatchecker.implementation.controller;

import com.naloga.nooemavatchecker.implementation.CheckVatNumber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.datatype.DatatypeConfigurationException;

@RestController
public class CheckVatController {

    @GetMapping("/checkVat")
    public String checkVat(@RequestParam String countryCode, @RequestParam String vatNumber) {
        try {
            return CheckVatNumber.checkVatDetails(countryCode, vatNumber);
        } catch (DatatypeConfigurationException e) {
            return "Error processing VAT number: " + e.getMessage();
        }
    }
}
