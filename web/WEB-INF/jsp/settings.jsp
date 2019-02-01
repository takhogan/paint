<%--
  Created by IntelliJ IDEA.
  User: Tak
  Date: 7/27/18
  Time: 3:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <div class = "top-bar" style = "width: 100%; padding:4px !important;">
        <a class = "bar-button" href = "http://localhost:9999/paint/portfolio">Home</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/portfolio">Portfolio</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/currencytrade">Trade</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/market">Currencies</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/bonds">Bonds</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/futures">Futures</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/settings">Settings</a>
    </div>
    <br>
    <div class = "top-bar">
        <a class = "bar-button" href = "http://localhost:9999/paint/currencytrade">Trade Currencies</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/bondtrade">Trade Bonds</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/bondtrade">Trade Futures</a>
    </div>
</head>
<body>
<title>Settings Page</title>
<div class = main-container>
    <form method = "post" action = http://localhost:9999/paint/generate>
        <table>
            <tr>
                <th>Player Name:</th>
                <th>Risk Tolerance: (1-100) (Recommended: 1-50) </th>
            </tr>
            <tr>
                <td><input type = text name = player_name></td>
                <td><input type = text name = mode></td>
            </tr>
            <tr>
                <td></td>
                <td><input type = submit value = Submit></td>
            </tr>
        </table>
        <input hidden type = text name = g_type value = "1"/>
    </form>
    <form method = "post" action = http://localhost:9999/paint/generate>
        <table>
            <tr>
                <th>Player ID:</th>
                <th>Player Name:</th>
                <th>Mode:</th>
                <th>Remove:</th>
            </tr>
            <c:forEach items="${playerlist}" var="p" varStatus="st">
                <tr>
                    <td>${p.user_id}</td>
                    <td>${p.user_name}</td>
                    <td>${p.mode}</td>
                    <td><input type="checkbox" name="tid" value="${p.user_id}"/></td>
                </tr>
            </c:forEach>
        </table>
        <input type = submit value = Submit>
        <input hidden type = text name = g_type value = "2"/>
    </form>
    <form method = "post" action = http://localhost:9999/paint/reset>
        <input type = submit value = reset>
    </form>
</div>


</body>
</html>
