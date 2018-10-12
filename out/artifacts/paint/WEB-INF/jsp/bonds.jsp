<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <script>
        function toggle(ele) {
            let buy = document.getElementById(ele + "-buy");
            let sell = document.getElementById(ele + "-sell");
            //if one is none both should be none
            if(buy.style.display==='none'){
                buy.style.display = 'table';
                sell.style.display = 'table';
            } else{
                buy.style.display = 'none';
                sell.style.display = 'none'
            }
        }
        function toggle_select(ele){
            let checkmarks = document.getElementById(ele).getElementsByTagName('input');
            let mainbox = document.getElementById(ele+"-main");
            console.log(checkmarks.length);
            for(i = 0; i < checkmarks.length; i++){
                if(checkmarks[i].getAttribute('name') === 'tid') {
                    checkmarks[i].checked = mainbox.checked;
                }
            }
        }
        function redirectForm(url){
            var form = document.getElementById("orderform") || null;
            if(form) {
                form.action = ("http://localhost:9999/paint/"+url);
            }
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
        <a class = "bar-button" href = "http://localhost:9999/paint/bondtrade">Add Trade</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/bonds">Market</a>
    </div>
</head>

<body>
    <h1> Bond Market Page for ${user} </h1>

    <table>
        <th style = "text-align: center;"> Order Table: </th>
    </table>
        <form method = "post" action="http://localhost:9999/paint/bondexec">
            <table>
                <tr>
                    <th> Loan ID </th>
                    <th> Owner Name </th>
                    <th> Borrower Name </th>
                    <th> Base Currency </th>
                    <th> Loan Amount </th>
                    <th> Initial Margin </th>
                    <th> Interest Frequency (minutes) </th>
                    <th> Interest Payment (last) </th>
                    <th> Loan Type </th>
                    <th> Expiration </th>
                    <th> Last Payment </th>
                    <th> Active? </th>
                    <th> Execute: </th>
                </tr>
                <c:forEach items="${inactiveblist}" var="b" varStatus="st">
                    <tr>
                        <td> ${b.loan_id} </td>
                        <td> ${b.owner_name} </td>
                        <td> ${b.borrower} </td>
                        <td> ${b.loan_currency} </td>
                        <td> ${b.loan_amount} </td>
                        <td> ${b.initial_margin} </td>
                        <td> ${b.interest_frequency} </td>
                        <td> ${b.interest_amount} </td>
                        <td> ${b.loan_type} </td>
                        <td> ${b.expiration} </td>
                        <td> ${b.last_payment} </td>
                        <td> ${b.active}</td>
                        <c:choose>
                            <c:when test="${b.owner_name == null}">
                                <td> <input type="checkbox" name="bond_id" value=${b.loan_id}></td>
                            </c:when>
                            <c:when test="${b.borrower == null}">
                                <td> <input type="checkbox" name="bond_id" value=${b.loan_id}></td>
                            </c:when>
                            <c:otherwise>
                                <td>  </td>
                            </c:otherwise>
                        </c:choose>

                    </tr>
                </c:forEach>
            </table>
            <input type = "submit" value = "Submit" class = "bar-button">
        </form>




<h2> Active Bonds: </h2>
<table>
    <caption> Trade Table </caption>
    <tr>
        <th> Loan ID </th>
        <th> Owner Name </th>
        <th> Borrower Name </th>
        <th> Base Currency </th>
        <th> Loan Amount </th>
        <th> Initial Margin </th>
        <th> Interest Frequency (minutes) </th>
        <th> Interest Payment (last) </th>
        <th> Loan Type </th>
        <th> Expiration </th>
        <th> Last Payment </th>
        <th> Active? </th>
    </tr>

    <c:forEach items="${activeblist}" var="b" varStatus="st">
    <tr>
        <td> ${b.loan_id} </td>
        <td> ${b.owner_name} </td>
        <td> ${b.borrower} </td>
        <td> ${b.loan_currency} </td>
        <td> ${b.loan_amount} </td>
        <td> ${b.initial_margin} </td>
        <td> ${b.interest_frequency} </td>
        <td> ${b.interest_amount} </td>
        <td> ${b.loan_type} </td>
        <td> ${b.expiration} </td>
        <td> ${b.last_payment} </td>
        <td> ${b.active}</td>
    </tr>
    </c:forEach>

</table>




</body>
</html>



