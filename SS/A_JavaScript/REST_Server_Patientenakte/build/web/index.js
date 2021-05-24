/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function loadInfo(current) {
    var xmlhttp;
    if (current.length == 0) {
        document.getElementById("inner").innerHTML = "Nichts ausgewählt!";
    }

    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function ()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            try {
                var patient = JSON.parse(xmlhttp.responseText);
                var txt = "<table>" +
                        "<tr><td>Pat ID:</td><td>" + patient.id + "</td></tr>" +
                        "<tr><td>Vorname:</td><td>" + patient.vorname + "</td></tr>" +
                        "<tr><td>Nachname:</td><td>" + patient.nachname + "</td></tr>" +
                        "<tr><td>Daten:</td></tr>";
                for (var i = 0; i < patient.Daten.length; i++) {
                    txt += "<tr><td></td><td>" + patient.Daten[i].Datum + " " + patient.Daten[i].Eintrag + "</td></tr>";
                }
                txt += "</tables>";
                document.getElementById("inner").innerHTML = txt;
            } catch (TypeError) {
                document.getElementById("inner").innerHTML = "ID gibt es nicht!";
            }
        }
    }

    xmlhttp.open("GET", "http://localhost:8080/REST_Server_Patientenakte/resources/patienten/patient/" + current + "?x=" + (new Date()).getMilliseconds(), true);
    xmlhttp.send();
}

