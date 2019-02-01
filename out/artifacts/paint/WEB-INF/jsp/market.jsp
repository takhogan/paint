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
                if(checkmarks[i].getAttribute('name') === 'id') {
                    checkmarks[i].checked = mainbox.checked;
                }
            }
        }
    </script>
    <div class = "top-bar" style = "width: 100%; padding:4px !important;">
        <a class = "bar-button" href = "http://localhost:9999/paint/portfolio">Home</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/portfolio">Portfolio</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/currencytrade">Trade</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/currencymarket">Currencies</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/bondmarket">Bonds</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/futuresmarket">Futures</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/settings">Settings</a>
    </div>
    <br>
    <div class = "top-bar">
        <a class = "bar-button" href = "http://localhost:9999/paint/${typeName}trade">Add Trade</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/${typeName}market">Market</a>
    </div>
</head>

<body>
<div class = "main-container">
    <h1 class = "th-header" style = "text-transform: capitalize"> ${typeName} Market Page for ${user} </h1>


    <div class = "left-content">
        <div class = "left-item">
            <div class = "th-header"> Order Table: </div>
            <c:forEach items="${mlist}" var = "m" varStatus="stst">
                <button class="market-button" onclick=toggle("${m.c_name}")>
                        ${m.c_name}
                </button>
                <form method = "post" action="http://localhost:9999/paint/${execName}">
                    <table id = "${m.c_name}-buy" style = "display:none">
                        <tr>
                            <th colspan ="${nBuyVars}" class = "th-header" style = "border-right: none;">
                                Buy Orders
                            </th>
                            <th class="th-header" style = "border-left: none;">
                                Select All:
                                <input id = "${m.c_name}-buy-main" type="checkbox" onclick=toggle_select("${m.c_name}-buy")>
                            </th>
                        </tr>
                        <tr>
                            <c:forEach items="${buyVarNames}" var="v" varStatus="vs">
                                <th> ${v} </th>
                            </c:forEach>
                            <th> Execute: </th>
                        </tr>
                        <c:choose>
                            <c:when test="${m.buyorders == null}">
                                <tr>
                                    <td colspan=${nBuyVars}> No Orders </td>
                                </tr>

                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${m.buyorders}" var="o" varStatus="st">
                                    <tr>
                                        <c:forEach items="${o.varVals}" var = "vv" varStatus="st">
                                            <td> ${vv} </td>
                                        </c:forEach>
                                        <td> <input type="checkbox" name="id" value=${o.submitVal} > </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </table>
                    <table id = "${m.c_name}-sell" style = "display:none">
                        <tr>
                            <th colspan ="${nSellVars}" class = "th-header" style = "border-right: none;">
                                Sell Orders
                            </th>
                            <th class="th-header" style = "border-left: none;">
                                Select All:
                                <input id = "${m.c_name}-sell-main" type="checkbox" onclick=toggle_select("${m.c_name}-sell")>
                            </th>
                        </tr>
                        <tr>
                            <c:forEach items="${sellVarNames}" var="v" varStatus="vs">
                                <th> ${v} </th>
                            </c:forEach>
                            <th> Execute: </th>
                        </tr>
                        <c:choose>
                            <c:when test="${m.buyorders == null}">
                                <tr>
                                    <td colspan=${nSellVars}> No Orders </td>
                                </tr>

                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${m.sellorders}" var="o" varStatus="st">
                                    <tr>
                                        <c:forEach items="${o.varVals}" var = "vv" varStatus="st">
                                            <td> ${vv} </td>
                                        </c:forEach>
                                        <td> <input type="checkbox" name="id" value=${o.submitVal} > </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <tr>
                            <td colspan="${nSellVars}"></td>
                            <td>
                                <input type = "submit" value = "Submit">
                            </td>
                        </tr>
                    </table>
                </form>
            </c:forEach>
        </div>
    </div>
    <div class = "right-content">
        <div class = "right-container">
            <div class = "th-header"> Recent Trades: </div>
            <c:forEach items="${otherlist}" var="t" varStatus="st">
                <div class = "news-item">
                        ${t}
                </div>
            </c:forEach>
        </div>
    </div>
</div>




</body>
</html>



