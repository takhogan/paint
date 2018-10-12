<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <script>
        function loadPlayers(){
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "operate", true);
            xhttp.send();
        }
    </script>
</head>

<body onload="loadPlayers()">
<div class = "th-header-black">
    <h1>Trade Execution Page</h1>
</div>
<div class = "th-header">
    <h1> Execution Page for ${user} </h1>
</div>
    <div class = "main-container">
    <h2> Orders Attempted: </h2>
        <c:forEach items="${flist}" var = "o" varStatus = "st">
            <c:choose>
                <c:when test = "${o.status=='invalid order'}">
                    <table style="background-color:red;">
                </c:when>
                <c:when test = "${o.status=='insufficient funds'}">
                    <table style="background-color:yellow;">
                </c:when>
                <c:when test = "${o.status=='final balance'}">
                    <table style="background-color:lightgreen;">
                </c:when>
                <c:otherwise>
                    <table>
                </c:otherwise>
            </c:choose>
                <tr><th colspan = ${nNames}>Status: ${o.status}</th></tr>
                <tr>
                    <c:forEach items="${varNames}" var="n" varStatus="st">
                        <th>${n}</th>
                    </c:forEach>
                </tr>

                <tr>
                    <c:forEach items="${o.varVals}" var="v" varStatus="st">
                        <td>${v}</td>
                    </c:forEach>
                </tr>

            </table>
        </c:forEach>

        <h1> Click Button to Return Home</h1>
        <a class = "bar-button" href = "http://localhost:9999/paint/portfolio"> Home </a>
    </div>

</body>

</html>
