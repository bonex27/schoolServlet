<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <script>
        function call()
        {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "PATCH", "http://localhost:8080/schoolServlet/classi?id=2", true );
        xmlHttp.onload = function() {
            document.getElementById("test").innerHTML = xmlHttp.response;
        }
        //"{ 'idStudent':64, 'idClass':1}"
        xmlHttp.send("{'section':'AAA'}");
        }
    </script>
    <body onload="call()">
        <div id='test'>

        </div>       
    </body>
    
    
</html>