package com.naloga.nooemavatchecker.servlet;

import com.naloga.nooemavatchecker.implementation.CheckVatNumber;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;


@WebServlet("/checkVat")
public class CheckVatServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Uporabi POST.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String countryCode = request.getParameter("countryCode");
        String vatNumber = request.getParameter("vatNumber");

        String result;
        try {
            result = CheckVatNumber.checkVatDetails(countryCode, vatNumber);
            response.setContentType("application/json");
            response.getWriter().write(result);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }


}
