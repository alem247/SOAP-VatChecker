package com.naloga.nooemavatchecker.implementation;

import eu.vies.vat.CheckVatPortType;
import eu.vies.vat.CheckVatService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;
import java.time.LocalDate;

public class CheckVatNumber {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uporaba: java -jar build/libs/CheckVatNumber-version.jar <CountryCode> <VatNumber>");
            return;
        }

        String countryCode = args[0];
        String vatNumber = args[1];

        try {
            checkVatDetails(countryCode, vatNumber);
        } catch (WebServiceException e) {
            System.out.println("Napaka pri klicu storitve: " + e.getMessage());
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static String checkVatDetails(String countryCode, String vatNumber)
            throws WebServiceException, DatatypeConfigurationException {
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

        StringBuilder response = new StringBuilder();

        if (validHolder.value) {
            response.append("Podatki zavezanca za DDV:\n");
            response.append("Ime podjetja: ").append(nameHolder.value).append("\n");
            response.append("Naslov: ").append(addressHolder.value).append("\n");
            response.append("DDV stevilka je veljavna.");
        } else {
            response.append("Davcna stevilka ne obstaja v VIES.");
        }

        return response.toString();
    }

}
