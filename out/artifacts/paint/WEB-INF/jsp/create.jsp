<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <script>
        function togglehidden(elem){
            console.log(elem);
            if(elem.getAttribute("style") === "display:none"){
                elem.setAttribute("style", "display:inline");
            } else {
                elem.setAttribute("style", "display:none")
            }
            console.log(elem)
        }
    </script>
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
        <a class = "bar-button" href = "http://localhost:9999/paint/portfolio">Portfolio</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/market">Market</a>
    </div>
</head>
<body>
<div class = "th-header">
    <h1> Create Page for ${user} </h1>
</div>
<div class = "main-container">
    <div id = "currency_generation">
        <form id = "ff" method = post action="http://localhost:9999/paint/currencygen">
            <table>
                <tr>
                    <th>Currency Name:</th>
                    <th>Starting Supply:</th>
                </tr>
                <tr>
                    <td>
                        <input type = text name = c_name>
                    </td>
                    <td>
                        <input type = text name = c_supply>
                    </td>
                </tr>
            </table>
            <input type = submit value = Submit>
        </form>
    </div>
    <div id = "loan_generation">
        <form method = post action="http://localhost:9999/paint/bondgen">
            <table>
                <tr>
                    <th>Borrow/Loan</th>
                    <th>Base Currency</th>
                    <th>Loan Amount</th>
                    <th>Initial Margin</th>
                    <th>Interest Amount</th>
                    <th>Repayment Frequency</th>
                    <th>Loan Type</th>
                    <th>Loan Term</th>
                </tr>
                <tr>
                    <td>
                        <select name = "create_type">
                            <option value = "1"> lend </option>
                            <option value = "2"> borrow </option>
                        </select>
                    </td>
                    <td>
                        <select name = "c_name">
                            <c:forEach items="${clist}" var="c" varStatus="st">
                                <option value = "${c}"> ${c} </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input type = "text" name = "c_amount" value = "0">
                    </td>
                    <td>
                        <input type = "text" name = "initial_margin" value = "0">
                    </td>
                    <td>
                        <input type = "text" name = "i_amount" value = "0">
                    </td>
                    <td>
                        <input style = "display:none" type="text" name="freq_mult" value = "1">
                        <select style = "display:inline-block; margin:0px; padding:0px;" name = "frequency" onchange="customFrequency(this)">
                            <option value = "1440"> daily </option>
                            <option value = "60"> hourly </option>
                            <option value = "1"> minutely </option>
                        </select>
                    </td>
                    <td>
                        <select name = "loan_type">
                            <option value = "1"> bond </option>
                            <option value = "2"> constant repayment </option>
                            <option value = "3"> custom </option>
                        </select>
                    </td>
                    <td>
                        <input style = "display:inline-block" type="text" name="expiration_value" value = "1">
                        <select style = "display:inline-block;" name = "expiration_units">
                            <option value = "1440"> days </option>
                            <option value = "60"> hours </option>
                            <option value = "1"> minutes </option>
                        </select>
                    </td>
                </tr>
            </table>
            <input type = submit value = Submit>
        </form>
    </div>
</div>
<div class = "top-bar">
    <a class = "bar-button" style = "margin:auto" href = "http://localhost:9999/paint/portfolio">Home</a>
</div>

</body>
</html>