/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $.get("http://localhost:8080/REST_Server_Patientenakte/resources/patienten/patientenIDs", function (data, status) {
        for (var i = 0; i < data["Liste der IDs"].length; i++) {
            let tempID = data["Liste der IDs"][i].id;
            let box = document.createElement("div");
            box.innerHTML = tempID;
            box.setAttribute("id", tempID);
            box.setAttribute("style", "background-color: #efefef; height:30px; width: 30px;")
            addEvent(box);
            document.getElementById("liste").appendChild(box);
            
        }
    });
});

function addEvent(box){
    $(box).hover(function() {
        $.getJSON("http://localhost:8080/REST_Server_Patientenakte/resources/patienten/patient/" + box.innerHTML, function (data, status){
            let tempLI = document.createElement("li");
            tempLI.innerHTML =  data["vorname"] + " " + data["nachname"];
            let tempUL = document.createElement("ul");
            tempUL.setAttribute("id", "ID_ul");
            tempUL.appendChild(tempLI);
            box.appendChild(tempUL);    
            $(box).animate({height: '80px', width:'100%'}, "fast");
        });
    }, function(){
        box.removeChild(document.getElementById("ID_ul"));
        $(box).animate({height: '30px', width: '30px'});
    });
}

