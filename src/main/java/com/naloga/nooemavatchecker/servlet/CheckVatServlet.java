package com.naloga.nooemavatchecker.servlet;

import eu.vies.vat.CheckVatPortType;
import eu.vies.vat.CheckVatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;

import java.io.IOException;
import java.time.LocalDate;


@WebServlet("/checkVat")
public class CheckVatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Uporabi POST.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String countryCode = request.getParameter("countryCode");
        String vatNumber = request.getParameter("vatNumber");

        String result;
        try {
            result = checkVatDetails(countryCode, vatNumber);
            response.setContentType("application/json");
            response.getWriter().write(result);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
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
