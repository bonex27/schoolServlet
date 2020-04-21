<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <script>
        function call()
        {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "DELETE", "http://localhost:8080/schoolServlet/classi?id=1", true ); // false for synchronous request
        xmlHttp.onload = function() {
            document.getElementById("test").innerHTML = xmlHttp.response;
        }
        xmlHttp.send("{ 'year':1200, 'section':'AAA'}");
        }
    </script>
    <body onload="call()">
        <div id='test'>

        </div>       
    </body>
    
    
</html>