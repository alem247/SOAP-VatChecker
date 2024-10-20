package com.naloga.nooemavatchecker.controller;

import com.naloga.nooemavatchecker.implementation.CheckVatNumber;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.datatype.DatatypeConfigurationException;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CheckVatController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/index.html");
        return modelAndView;
    }

    @RequestMapping(value = "/checkVat", method = RequestMethod.GET)
    @ResponseBody
    public String checkVat(@RequestParam("countryCode") String countryCode,
                           @RequestParam("vatNumber") String vatNumber) {
        try {
            return CheckVatNumber.checkVatDetails(countryCode, vatNumber);
        } catch (DatatypeConfigurationException e) {
            return "Error processing VAT number: " + e.getMessage();
        }
    }
}
