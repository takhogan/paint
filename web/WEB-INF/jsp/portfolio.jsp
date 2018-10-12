<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <style type="text/css">
        form {
            display:inline;
            margin:0px;
            padding:0px;
        }
    </style>
    <script>
        document.cookie="username=${user}";
    </script>
    <script>
        function redirectExecutionPage(tidval){
            var checkbox = document.getElementById("?"+tidval);
            var input = document.createElement("input");
            input.hidden = true;
            input.type = "text";
            input.name = "tidvalue";
            input.setAttribute("value", tidval); //just a reminder that input.value doesn't work for some reason

            if(checkbox) {
                checkbox.parentNode.appendChild(input);
            }
            console.log(checkbox.parentNode);

            var form = document.getElementById("orderform") || null;
            if(form) {
                form.action = "http://localhost:9999/paint/orderedit";
            }
            form.submit();
        }

    </script>
    <!-- Home should go to login page on the portfolio page so potentially (INCOMPLETE)-->
    <div class = "top-bar" style = "width: 100%; padding:4px !important;">
        <a class = "bar-button" href = "http://localhost:9999/paint">Home</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/portfolio">Portfolio</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/currencytrade">Trade</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/currencymarket">Currencies</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/bondmarket">Bonds</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/futuresmarket">Futures</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/settings">Settings</a>
    </div>
    <br>
    <div class = "top-bar">
        <a class = "bar-button" href = "http://localhost:9999/paint/currencytrade">Trade Currencies</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/bondtrade">Trade Bonds</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/futurestrade">Trade Futures</a>
    </div>
</head>


<body>
<div class = "main-container">
    <h1 class = "th-header"> Welcome ${user} </h1>
    <div class = "left-content">
        <div class = "left-container">
            <form method = "post" action="http://localhost:9999/paint/walletcombo">
                <table style=width:100%>
                    <tr><th colspan = "4" class = "th-header-black">Balances Table</th></tr>
                    <tr>
                        <th> Wallet ID </th>
                        <th> Currency Name </th>
                        <th> Supply </th>
                        <th> Merge: </th>
                    </tr>

                    <c:forEach items="${wallets}" var="w" varStatus="st">
                        <tr>
                            <td> ${w.wallet_id}</td>
                            <td> ${w.c_name} </td>
                            <td>${w.balance}</td>
                            <td> <input type="checkbox" name="wallet_id" value="${w.wallet_id}"/> </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan = "3"></td>
                        <td><input type = submit value = Submit></td>
                    </tr>
                </table>

                <input hidden type = text name = e_type value = "5"/>
                <input hidden type = text name = username value = "${user}"/>
            </form>
        </div>
        <br>
        <form method = post action="http://localhost:9999/paint/ordercancel" id = "orderform">
            <table style="width:100%">
                <th colspan = "8" class = "th-header-black">Order Table</th>
                <tr>
                    <th> order_id </th>
                    <th> owner_name </th>
                    <th> wantname </th>
                    <th> wantamount </th>
                    <th> givename </th>
                    <th> giveamount </th>
                    <th> Cancel: </th>
                    <th> Edit: </th>
                </tr>

                <c:forEach items = "${orders}" var="o" varStatus="st">
                    <tr>
                        <td> ${o.order_id} </td>
                        <td> ${o.owner_name} </td>
                        <td> ${o.wantname} </td>
                        <td> ${o.wantamount} </td>
                        <td> ${o.givename} </td>
                        <td> ${o.giveamount} </td>
                        <td> <input id = "${o.order_id}" type="checkbox" name="tid" value="${o.order_id}"/> </td>
                        <td>
                            <input id="?${o.order_id}" type="button" value="edit" onclick="redirectExecutionPage(${o.order_id})"/>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan = 6></td>
                    <td> <input type = submit name = "Submit"> </td>
                    <td></td>
                </tr>



            </table>
            <%--<input hidden type = text name = username value = "${user}"/>--%>
        </form>
        <br>
    </div>
    <div class = "right-content">
        <div class = "right-container">
            <div class = "th-header-black">
                Headlines:
            </div>
            <c:forEach items = "${news}" var="n" varStatus="st">
                <div class = "news-item">
                    ${n}
                </div>
            </c:forEach>
        <br>
        </div>
    </div>
</div>

</body></html>


