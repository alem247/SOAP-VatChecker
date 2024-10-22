package com.naloga.nooemavatchecker.controller;

import com.naloga.nooemavatchecker.model.VatCheckRequest;
import com.naloga.nooemavatchecker.model.VatCheckResponse;
import eu.vies.vat.CheckVatPortType;
import eu.vies.vat.CheckVatService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CheckVatController {


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("VatCheckRequest", new VatCheckRequest());
        return "index";
    }

    @PostMapping("/checkVat")
    public String checkVat(@ModelAttribute VatCheckRequest vatCheckRequest, Model model)  {
        try {
            VatCheckResponse vatCheckResponse = checkVatDetails(vatCheckRequest);
            model.addAttribute("VatCheckResponse", vatCheckResponse);
            model.addAttribute("VatCheckRequest", vatCheckRequest);
            System.out.println(vatCheckResponse.getAddress() + vatCheckResponse.getCompanyName());
            return "index";
        } catch (DatatypeConfigurationException d){
            d.printStackTrace();
            return HttpStatus.BAD_REQUEST.toString();
        }
    }

    public static VatCheckResponse checkVatDetails(VatCheckRequest vatCheckRequest)
            throws WebServiceException, DatatypeConfigurationException {
        String countryCode = vatCheckRequest.getCountryCode();
        String vatNumber = vatCheckRequest.getVatNumber();
        CheckVatService service = new CheckVatService();
        CheckVatPortType port = service.getCheckVatPort();

        Holder<String> countryCodeHolder = new Holder<>(countryCode);
        Holder<String> vatNumberHolder = new Holder<>(vatNumber);
        Holder<XMLGregorianCalendar> requestDateHolder = new Holder<>(
                DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.now().toString()));
        Holder<Boolean> validHolder = new Holder<>(false);
        Holder<String> nameHolder = new Holder<>("?");
        Holder<String> addressHolder = new Holder<>("?");

        port.checkVat(countryCodeHolder, vatNumberHolder, requestDateHolder, validHolder, nameHolder, addressHolder);
        if (validHolder.value) {
            return new VatCheckResponse(nameHolder.value, addressHolder.value);
        } else return new VatCheckResponse(null, null);
    }
}
