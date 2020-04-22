<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <script>
        function call()
        {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "POST", "http://localhost:8080/schoolServlet/StudentClasses", true ); // false for synchronous request
        xmlHttp.onload = function() {
            document.getElementById("test").innerHTML = xmlHttp.response;
        }
        //"{ 'name':'Pietro', 'surname':'Bonechi' , 'sidiCode':12341241 , 'taxCode':'Abcd312'}"
        xmlHttp.send("{ 'idStudent':64, 'idClass':1}");
        }
    </script>
    <body onload="call()">
        <div id='test'>

        </div>       
    </body>
    
    
</html>