<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Preverjanje davčne številke</title>
    <style>
        body {
            background-color: #f4f4f9;
            font-family: Arial, sans-serif;
            color: #333;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        label {
            font-weight: bold;
            margin-bottom: 5px;
        }

        input {
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
            width: 100%;
        }

        button {
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background-color: #218838;
        }

        #result {
            margin-top: 20px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        .error {
            color: red;
        }

        .success {
            color: green;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Preverjanje davčne številke</h1>
    <form action="${pageContext.request.contextPath}/checkVat" method="POST">
        <div>
            <label for="countryCode">Koda države (npr. SI):</label>
            <input type="text" id="countryCode" name="countryCode" required>
        </div>

        <div>
            <label for="vatNumber">Davčna številka:</label>
            <input type="text" id="vatNumber" name="vatNumber" required>
        </div>

        <button type="submit">Preveri</button>
    </form>

    <div id="result">
        <%
            String companyName = request.getParameter("companyName");
            String address = request.getParameter("address");
            String vatStatus = request.getParameter("vatStatus");

            PrintWriter printWriter = response.getWriter();

            if (companyName != null && address != null) {
                printWriter.println("<h2>Podatki zavezanca za DDV:</h2>");
                printWriter.println("<p><strong>Ime podjetja:</strong> " + companyName + "</p>");
                printWriter.println("<p><strong>Naslov:</strong> " + address + "</p>");
                printWriter.println("<p>DDV številka je veljavna.</p>");
            } else if ("invalid".equals(vatStatus)) {
                printWriter.println("<p class='error'>Davčna številka ne obstaja v VIES.</p>");
            }
        %>
    </div>
</div>
</body>
</html>
