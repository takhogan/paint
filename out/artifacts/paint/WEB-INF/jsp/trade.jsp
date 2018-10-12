<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/main.css">
    <script>
        var counter = 1;
        function attachtable(){
            console.log("---attach table---");
            var table = document.createElement('table');
            var prevblock = document.getElementById('orderblock'+counter);
            var opblock;

            console.log(prevblock);
            if(prevblock != null && prevblock.childNodes != null && prevblock.childNodes.length > 1) {
                console.log("prevblock");
                opblock = prevblock;

            } else {
                console.log("baseblock");
                let mid = document.getElementById('target');
                console.log(mid);
                let midchild = mid.getElementsByTagName('table');
                console.log(midchild.length);
                console.log(midchild[0]);
                opblock = midchild[0];
            }
            table.innerHTML = opblock.innerHTML;
            var inputs = opblock.getElementsByTagName('input');
            var outputs = table.getElementsByTagName('input');
            var cinputs = opblock.getElementsByTagName('select');
            var coutputs = table.getElementsByTagName('select');

            for(var i = 0; i < 2; i++){
                outputs[i].value = inputs[i].value;
                console.log(cinputs[i].selectedIndex);
                coutputs[i].selectedIndex = cinputs[i].selectedIndex;
            }

            counter++;
            table.id = 'orderblock'+counter;
            document.getElementById('target').appendChild(table);
            console.log("---end---");

        }
        function removetable(tableid){
            console.log("--remove table--");
            var superparentp = tableid.parentNode.parentNode.parentNode.parentNode;
            var superparentpp = superparentp.parentNode;
            var superstats = superparentpp.getElementsByTagName('tr').length;
            var superstats2 = superparentpp.getElementsByTagName('button').length;
            console.log(superparentpp);
            console.log(superstats);
            console.log(superstats2);
            console.log();


            var cantremove = (superparentpp.getElementsByTagName('table').length === 1);
            console.log("--end--");

            if(!cantremove){
                superparentpp.removeChild(superparentp);
            }

        }
        function customFrequency(elem){
            //might have a problem if element is unselected
            let input = elem.parentNode.getElementsByTagName("input")[0];
            let selected = elem.parentNode.getElementsByTagName("select")[0];
            let selectedindex = selected.selectedIndex;
            let selectedoption = selected.options[selectedindex];
            if(selectedoption.getAttribute("value") === "1"){
                //HOW TO GET TABLE ELEMENTS TO ALIGN THEMSELVES: INLINE-BLOCK
                input.setAttribute("style", "display:inline-block");
            } else {
                input.setAttribute("style", "display:none");
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
        <a class = "bar-button" href = "http://localhost:9999/paint/currencytrade">Trade Currencies</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/bondtrade">Trade Bonds</a>
        <a class = "bar-button" href = "http://localhost:9999/paint/futurestrade">Trade Futures</a>
    </div>
</head>
<body>
<div class = "th-header">
    <h1> Trade Page for ${user} </h1>
</div>
<div class="main-container">
        <form id = "ff" method = post action=http://localhost:9999/paint${formName}>
            <div class = "top-bar" style = "width: 100%">
                <button type = "button" onclick="attachtable()" class = "bar-button">Add Trade</button>
                <input type = "submit" value = "Submit" class = "bar-button">
            </div>
            <div id = "target" class = "left-item">
                <table id = "orderblock1">
                    <tr>
                        <c:forEach items = "${varNames}" var = "n" varStatus="st">
                            <th>
                                ${n}
                            </th>
                        </c:forEach>
                        <th>
                            Cancel:
                        </th>
                    </tr>
                    <tr>
                        <c:forEach items = "${inputFields}" var = "f" varStatus = "st">
                            <td>
                                <c:choose>
                                    <c:when test = "${f.objectType == 'input'}">
                                        <input type = "text" name = "${f.varName}" value = "${f.defaultValue.value}">
                                    </c:when>
                                    <c:when test = "${f.objectType == 'select'}">
                                        <select name = "${f.varName}">
                                            <c:forEach items = "${f.options}" var = "o" varStatus = "st">
                                                <c:choose>
                                                    <c:when test="${o.value == f.defaultValue.value}">
                                                        <option selected value=${o.value}> ${o.name} </option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value=${o.value}> ${o.name} </option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </c:when>
                                    <c:when test = "${f.objectType == 'input-select'}">
                                        <input type = "text" name = "${f.input.varName}" style="${f.input.style}" value = "${f.input.defaultValue.value}">
                                        <select name = "${f.select.varName}" style="${f.select.style}">
                                            <c:forEach items = "${f.select.options}" var = "o" varStatus = "st">
                                                <c:choose>
                                                    <c:when test="${o.value == f.select.defaultValue.value}">
                                                        <option selected value=${o.value}> ${o.name} </option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value=${o.value}> ${o.name} </option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </c:when>
                                    <c:when test = "${f.objectType == 'input-select-onchange'}">
                                        <input type = "text" name = "${f.input.varName}" style="${f.input.style}" value = "${f.input.defaultValue.value}">
                                        <select onchange="${f.select.onChange}" name = "${f.select.varName}" style="${f.select.style}">
                                            <c:forEach items = "${f.select.options}" var = "o" varStatus = "st">
                                                <c:choose>
                                                    <c:when test="${o.value == f.select.defaultValue.value}">
                                                        <option selected value=${o.value}> ${o.name} </option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value=${o.value}> ${o.name} </option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </c:when>

                                    <c:otherwise>
                                        <p> Rendering Error </p>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:forEach>
                        <td>
                            <button type = "button" onclick=removetable(this)> [X] </button>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
    <%--
    <div class = "right-content">
        <div class = "right-container">
            <div class = th-header-black>
                <h2>
                    User Balances:
                </h2>
            </div>
            <table>
                <tr>
                    <th>
                        Name
                    </th>
                    <th>
                        Balance
                    </th>
                </tr>
                <c:forEach items="${wlist}" var="w" varStatus="st">
                    <tr>
                        <td>${w.c_name}</td>
                        <td>${w.balance}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    --%>

</div>
</body>
</html>