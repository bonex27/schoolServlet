/*
###APP LOAD###
*/

let levelsToApp = 0; //DEBUG ONLY. Use if app.html is not at the root of the website (eg: 192.168.1.27/somefolder/app.html)
//Specify how many levels there are to reach it (1 for the previous example)
let appTitle;
let appContainer;
let appNavbar;
let appResult;


window.addEventListener("load", loadIndex);

function loadIndex() {
    appTitle = document.getElementById("appTitle");

    appContainer = document.getElementById("button");


    appResult = document.getElementById("button");

    appNavbar = document.getElementById("appNavbar");

    appTitle.innerHTML = "<a  text-black'>Menu</a>";
    appContainer.innerHTML = '<table class="table"><tbody><tr><td><button class="btn btn-primary" onclick="loadPage(1)" type="button">Alunni</button></td><td><button class="btn btn-success" onclick="loadPage(2)" type="button">classi</button></td></tr></tbody></table>';
}

function Delete(id, call) {
    switch (call) {
        case 1:
            getUrl = 'http://localhost:8080/schoolServlet/Students?id=' + id;
            break;
        case 2:
            getUrl = 'http://localhost:8080/schoolServlet/classi?id=' + id;
            break;

    }
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", getUrl, true);
    xhr.onload = function() {
        loadPage(call);
    };
    xhr.onerror = function() {
        alert("Errore");
    };
    xhr.send();
}

function loadPage(call) {
    let title, button;
    appTitle.innerHTML = "";

    appTitle.innerHTML = "<- Back";
    appTitle.innerHTML = '<a class="clickable text-black" onclick="loadIndex()"> Back</a>';

    appTitle.innerHTML += "<br>"
    title = document.createElement("a");
    title.addEventListener("click",
        function() {
            loadPage(call);
        });
    title.className = "clickable text-black";
    title.innerHTML = "Refresh";
    appTitle.appendChild(title);

    switch (call) {
        case 1:

            appContainer.innerHTML = "";

            var table = document.createElement("table");
            var thead = document.createElement("thead");
            table.setAttribute("class", "table");
            thead.className = "thead-dark";
            table.appendChild(thead);
            appContainer.appendChild(table);

            var tr = document.createElement('tr');
            tr.innerHTML =
                '<th>id</th>' +
                '<th>Nome</th>' +
                '<th>Cognome</th>' +
                '<th>Sidi Code</th>' +
                '<th>Tax Code</th>' +
                '<th>Delete</th>'+
                '<th>Edit</th>';
            thead.appendChild(tr);

            var xhr = new XMLHttpRequest();
            xhr.open("GET", 'http://localhost:8080/schoolServlet/Students', true);
            xhr.onload = function() {
                var data = JSON.parse(xhr.response);
                let tr, td, button;

                for (var i = 0; i < data.length; i++) {
                    tr = document.createElement('tr');
                    tr.innerHTML =
                        '<td>' + data[i].id + '</td>' +
                        '<td>' + data[i].name + '</td>' +
                        '<td>' + data[i].surname + '</td>' +
                        '<td>' + data[i].sidiCode + '</td>' +
                        '<td>' + data[i].taxCode + '</td>';
                
                        td = document.createElement("td");
                        button = document.createElement("button");
                    let id = data[i].id;


                    button.className = "btn btn-danger";
                    button.addEventListener("click",
                        function() {
                            Delete(id, call);
                        });
                    button.innerHTML = "x";
                    td.appendChild(button);
                    tr.appendChild(td);

                    td = document.createElement("td");
                    button = document.createElement("button");
                    
                    button.className = "btn btn-success";                 
                    button.addEventListener("click",
                                            function()
                                            {
                                                edit(id,call);
                                            });
                    
                    button.innerHTML="Edit";                       
                    td.appendChild(button);
                    tr.appendChild(td);
                    table.appendChild(tr);
                }
                button = document.createElement("button");
            button.innerHTML="Add +";
            button.className="btn btn-success";
            button.type ="button";
            button.id="btnAdd";
            button.addEventListener("click", function()
            {
                add(call);
            });
            appContainer.appendChild(button);

            };
            xhr.onerror = function() {
                alert("Errore");
            };
            xhr.send();
            break;

        case 2:
            appContainer.innerHTML = "";

            var table = document.createElement("table");
            var thead = document.createElement("thead");
            table.setAttribute("class", "table");
            thead.className = "thead-dark";
            table.appendChild(thead);
            appContainer.appendChild(table);

            var tr = document.createElement('tr');
            tr.innerHTML =
                '<th>id</th>' +
                '<th>year</th>' +
                '<th>Section</th>' +
                '<th>Delete</th>'+
                '<th>Edit</th>'+
                '<th>Student</th>';
            thead.appendChild(tr);

            var xhr = new XMLHttpRequest();
            xhr.open("GET", 'http://localhost:8080/schoolServlet/classi', true);
            xhr.onload = function() {
                var data = JSON.parse(xhr.response);
                let tr, td, button;

                for (var i = 0; i < data.length; i++) {
                    tr = document.createElement('tr');
                    tr.innerHTML =
                        '<td>' + data[i].id + '</td>' +
                        '<td>' + data[i].year + '</td>' +
                        '<td>' + data[i].section + '</td>';
                        td = document.createElement("td");
                        button = document.createElement("button");
                    let id = data[i].id;


                    button.className = "btn btn-danger";
                    button.addEventListener("click",
                        function() {
                            Delete(id, call);
                        });
                    button.innerHTML = "x";
                    td.appendChild(button);
                    tr.appendChild(td);

                    td = document.createElement("td");
                    button = document.createElement("button");
                    
                    button.className = "btn btn-success";                 
                    button.addEventListener("click",
                                            function()
                                            {
                                                edit(id, call);
                                            });
                    
                    button.innerHTML="Edit";                       
                    td.appendChild(button);
                    tr.appendChild(td);

                    td = document.createElement("td");
                    button = document.createElement("button");
                    
                    button.className = "btn btn-warning";                 
                    button.addEventListener("click",
                                            function()
                                            {
                                                seeStudent(id);
                                            });
                    
                    button.innerHTML="Edit";                       
                    td.appendChild(button);
                    tr.appendChild(td);
                    table.appendChild(tr);
                }
            button = document.createElement("button");
            button.innerHTML="Add +";
            button.className="btn btn-success";
            button.type ="button";
            button.id="btnAdd";
            button.addEventListener("click", function()
            {
                add(call);
            });
            appContainer.appendChild(button);

            };
            xhr.onerror = function() {
                alert("Errore");
            };
            xhr.send();
            break;
    }
}

function seeStudent(idClass)
{
    document.getElementById('modalTitle').innerHTML ="Students";
    let modal =  document.getElementById('modalBody');
    modal.innerHTML = "";
            var table = document.createElement("table");
            var thead = document.createElement("thead");
            table.setAttribute("class", "table");
            thead.className = "thead-dark";
            table.appendChild(thead);
            modal.appendChild(table);

            var tr = document.createElement('tr');
            tr.innerHTML =
                '<th>Nome</th>' +
                '<th>Cognome</th>' +
                '<th>Delete</th>';
            thead.appendChild(tr);

            var xhr = new XMLHttpRequest();
            xhr.open("GET", 'http://localhost:8080/schoolServlet/StudentClasses?id='+idClass, true);
            xhr.onload = function() {
                var data = JSON.parse(xhr.response);
                let tr, td, button;

                for (var i = 0; i < data.length; i++) {
                    tr = document.createElement('tr');
                    tr.innerHTML =
                        '<td style="color: black;">' + data[i].name + '</td>' +
                        '<td style="color: black;">' + data[i].surname + '</td>' ;
                
                        td = document.createElement("td");
                        td
                        button = document.createElement("button");
                        let idStudent = data[i].id;


                    button.className = "btn btn-danger";
                    button.addEventListener("click",
                        function() {
                            $('#modalAll').modal('hide');
                            remStudent(idStudent ,idClass);
                            
                        });
                    button.innerHTML = "x";

                    td.appendChild(button);
                    tr.appendChild(td);

                    table.appendChild(tr);
                }
                button = document.createElement("button");
            button.innerHTML="Add +";
            button.className="btn btn-success";
            button.type ="button";
            button.id="btnAdd";
            button.addEventListener("click", function()
            {
                addInClass(idClass);
            });
            modal.appendChild(button);
            $('#modalAll').on('hidden.bs.modal', function (e) {
                modal.innerHTML = "";
            });
            $('#modalAll').modal('show');
            };
            xhr.onerror = function() {
                alert("Errore");
            };
            xhr.send();
}
function addInClass(idClass)
{
    
    document.getElementById('modalBody').innerHTML ='<form class="form-signin" id="form">'+
            '    <select type="text" id="inputStudent" class="form-control" >'+
        '</form>';
        var xhr = new XMLHttpRequest();
        xhr.open("GET", 'http://localhost:8080/schoolServlet/Students', true);
        xhr.onload = function() {
            var data = JSON.parse(xhr.response);
            let option;

            for (var i = 0; i < data.length; i++)
            {
                option = document.createElement('option');
                option.text = data[i].name +" "+ data[i].surname;
                option.value = data[i].id;
                document.getElementById("inputStudent").add(option);
            }
            
            document.getElementById('modalBtn').innerHTML ="No";   
             button = document.createElement("button");
            button.innerHTML="Si";
            button.className="btn btn-success";
            button.type ="button";
            button.id="modalBtnOk";
            button.addEventListener("click", function()
            {
                var student =  document.getElementById("inputStudent").value;
                var _class = idClass;
                var obj = { student: student, _class: _class};
                var myJSON = JSON.stringify(obj);
                $('#modalAll').modal('hide');
                addInClassCall(myJSON);
                

            });
            $('#modalAll').on('hidden.bs.modal', function (e) {
                $("#modalBtnOk" ).remove();
                //document.getElementById('modalBtn').removeEventListener('click',list());
            })
            document.getElementById("modalFooter").appendChild(button);

            $('#modalAll').modal('show');

        };
        xhr.onerror = function() {
            alert("Errore");
        };
        xhr.send();
        
        
            
            
}
function addInClassCall(json)
{
    var xhr = new XMLHttpRequest();

    xhr.open("POST", 'http://localhost:8080/schoolServlet/StudentClasses', true);
    xhr.onload =loadPage(2);
    xhr.onerror = function()
    {
        alert("Errore");
    };

    xhr.send(json);
}
function remStudent(id,idClass)
{
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "http://localhost:8080/schoolServlet/StudentClasses?id="+id, true);
    xhr.onload = function() {
        alert('ok');
        //seeStudent(idClass);
    };
    xhr.onerror = function() {
        alert("Errore");
    };
    xhr.send();
}

function edit(id, call) {
    document.getElementById('modalTitle').innerHTML ="Modifica";
    let button

    switch (call) {
        
        case 1:
            
            getUrl = 'http://localhost:8080/schoolServlet/Students?id=' + id;
            
            
            document.getElementById('modalBody').innerHTML ='<form class="form-signin" method="GET" id="form">'+
            '<h1 class="h3 mb-3 font-weight-normal">Edit studets</h1>'+
            '<label for="inputName" >Name</label>'+
            '    <input type="text" id="inputName" class="form-control"  name="email">'+
            '<label for="inputSurname" >Surname</label>'+
            '    <input type="text" id="inpuSurname" class="form-control" >'+
            '<label for="inputTaxcode" >Sidicode</label>'+
            '    <input type="text" id="inputSidicode" class="form-control"  >'+
            '<label for="inputSidicode" >Taxcode</label>'+
            '    <input type="text" id="inputTaxcode" class="form-control" >'+
        '</form>';
            document.getElementById('modalBtn').innerHTML ="No";
    
             button = document.createElement("button");
            button.innerHTML="Si";
            button.className="btn btn-success";
            button.type ="button";
            button.id="modalBtnOk";
            button.addEventListener("click", function()
            {
                var name = document.getElementById("inputName").value;
                var surname = document.getElementById("inpuSurname").value;
                var sidiCode = document.getElementById("inputSidicode").value;
                var taxCode = document.getElementById("inputTaxcode").value;
                var obj = { name: name, surname: surname, sidiCode: sidiCode, taxCode: taxCode};
                var myJSON = JSON.stringify(obj);
                $('#modalAll').modal('hide');
                editCall(id,call,myJSON);
                

            });
            $('#modalAll').on('hidden.bs.modal', function (e) {
                $("#modalBtnOk" ).remove();
                //document.getElementById('modalBtn').removeEventListener('click',list());
            })
            document.getElementById("modalFooter").appendChild(button);
            $('#modalAll').modal('show');
            
            
            break;
        case 2:
            getUrl = 'http://localhost:8080/schoolServlet/Students?id=' + id;
            
            document.getElementById('modalBody').innerHTML ='<form class="form-signin" method="GET" id="form">'+
            '<h1 class="h3 mb-3 font-weight-normal">Edit class</h1>'+
            '<label for="inputYear" >Year</label>'+
            '    <input type="text" id="inputYear" class="form-control"  name="email">'+
            '<label for="inputSection" >Section</label>'+
            '    <input type="text" id="inputSection" class="form-control" >'+
        '</form>';
            document.getElementById('modalBtn').innerHTML ="No";
    
             button = document.createElement("button");
            button.innerHTML="Si";
            button.className="btn btn-success";
            button.type ="button";
            button.id="modalBtnOk";
            button.addEventListener("click", function()
            {
                var year = document.getElementById("inputYear").value;
                var section = document.getElementById("inputSection").value;
                var obj = { year: year, section: section};
                var myJSON = JSON.stringify(obj);
                $('#modalAll').modal('hide');
                editCall(id,call,myJSON);
                

            });
            $('#modalAll').on('hidden.bs.modal', function (e) {
                $("#modalBtnOk" ).remove();
                //document.getElementById('modalBtn').removeEventListener('click',list());
            })
            document.getElementById("modalFooter").appendChild(button);
            $('#modalAll').modal('show');
            break;
        case 3:
            getUrl = 'http://localhost:8080/schoolServlet/StudentClasses?id=' + id;
            break;
    }
}
function editCall(id,call,json)
{
    switch (call) {
        case 1:
            getUrl = 'http://localhost:8080/schoolServlet/Students?id=' + id;
            break;
        case 2:
            getUrl = 'http://localhost:8080/schoolServlet/classi?id=' + id;
            break;
        case 3:
            getUrl = 'http://localhost:8080/schoolServlet/StudentClasses?id=' + id;
            break;
    }
    var xhr = new XMLHttpRequest();

    xhr.open("PATCH", getUrl, false);
    xhr.onload =loadPage(call);
    xhr.onerror = function()
    {
        alert("Errore");
    };

    xhr.send(json);
}

function add(call) {
    document.getElementById('modalTitle').innerHTML ="Modifica";
    let button

    switch (call) {
        
        case 1:
            
            getUrl = 'http://localhost:8080/schoolServlet/Students';
            
            
            document.getElementById('modalBody').innerHTML ='<form class="form-signin" id="form">'+
            '<h1 class="h3 mb-3 font-weight-normal">Add studets</h1>'+
            '<label for="inputName" >Name</label>'+
            '    <input type="text" id="inputName" class="form-control"  name="email">'+
            '<label for="inputSurname" >Surname</label>'+
            '    <input type="text" id="inpuSurname" class="form-control" >'+
            '<label for="inputTaxcode" >Sidicode</label>'+
            '    <input type="text" id="inputSidicode" class="form-control"  >'+
            '<label for="inputSidicode" >Taxcode</label>'+
            '    <input type="text" id="inputTaxcode" class="form-control" >'+
        '</form>';
            document.getElementById('modalBtn').innerHTML ="No";
    
             button = document.createElement("button");
            button.innerHTML="Si";
            button.className="btn btn-success";
            button.type ="button";
            button.id="modalBtnOk";
            button.addEventListener("click", function()
            {
                var name = document.getElementById("inputName").value;
                var surname = document.getElementById("inpuSurname").value;
                var sidiCode = document.getElementById("inputSidicode").value;
                var taxCode = document.getElementById("inputTaxcode").value;
                var obj = { name: name, surname: surname, sidiCode: sidiCode, taxCode: taxCode};
                var myJSON = JSON.stringify(obj);
                $('#modalAll').modal('hide');
                addCall(call,myJSON);
                

            });
            $('#modalAll').on('hidden.bs.modal', function (e) {
                $("#modalBtnOk" ).remove();
                //document.getElementById('modalBtn').removeEventListener('click',list());
            })
            document.getElementById("modalFooter").appendChild(button);
            $('#modalAll').modal('show');
            
            
            break;
        case 2:
            getUrl = 'http://localhost:8080/schoolServlet/Students';
            
            document.getElementById('modalBody').innerHTML ='<form class="form-signin" id="form">'+
            '<h1 class="h3 mb-3 font-weight-normal">Add class</h1>'+
            '<label for="inputYear" >Year</label>'+
            '    <input type="text" id="inputYear" class="form-control"  name="email">'+
            '<label for="inputSection" >Section</label>'+
            '    <input type="text" id="inputSection" class="form-control" >'+
        '</form>';
            document.getElementById('modalBtn').innerHTML ="No";
    
             button = document.createElement("button");
            button.innerHTML="Si";
            button.className="btn btn-success";
            button.type ="button";
            button.id="modalBtnOk";
            button.addEventListener("click", function()
            {
                var year = document.getElementById("inputYear").value;
                var section = document.getElementById("inputSection").value;
                var obj = { year: year, section: section};
                var myJSON = JSON.stringify(obj);
                $('#modalAll').modal('hide');
                addCall(call,myJSON);
                

            });
            $('#modalAll').on('hidden.bs.modal', function (e) {
                $("#modalBtnOk" ).remove();
                //document.getElementById('modalBtn').removeEventListener('click',list());
            })
            document.getElementById("modalFooter").appendChild(button);
            $('#modalAll').modal('show');
            break;
        case 3:
            getUrl = 'http://localhost:8080/schoolServlet/StudentClasses?id=' + id;
            break;
    }
}
function addCall(call,json)
{
    switch (call) {
        case 1:
            getUrl = 'http://localhost:8080/schoolServlet/Students';
            break;
        case 2:
            getUrl = 'http://localhost:8080/schoolServlet/classi';
            break;
        case 3:
            getUrl = 'http://localhost:8080/schoolServlet/StudentClasses';
            break;
    }
    var xhr = new XMLHttpRequest();

    xhr.open("POST", getUrl, false);
    xhr.onload =loadPage(call);
    xhr.onerror = function()
    {
        alert("Errore");
    };

    xhr.send(json);
}