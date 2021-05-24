/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var kurt = {bild: "herr.png", vorname: "Kurt", nachname:"Soonntag"};
var katrin = {bild: "dame.png", vorname: "Katring", nachname: "Musterfrau"};
var currentObj = kurt;
var expand = false;

function openExpand(current){
    var ul = document.createElement("ol");
    ul.setAttribute("id", "current");
    var li1 = document.createElement("li");
    var li2 = document.createElement("li");
    if(current.getAttribute("id") == "kurt"){
        currentObj = kurt;
    }else if(current.getAttribute("id") == "katrin"){
        currentObj = katrin;
    }
    var text1 =document.createTextNode(currentObj.vorname);
    var text2 = document.createTextNode(currentObj.nachname);
    li1.appendChild(text1);
    li2.appendChild(text2);
    document.getElementById("bild").src = currentObj.bild;
    ul.appendChild(li1);
    ul.appendChild(li2);
    current.appendChild(ul);
    current.style.backgroundColor = "blue";
    expand = true;
}

function closeExpand(current){
    if(expand){
        current.removeChild(document.getElementById("current"));
        document.getElementById("bild").src = "";
        current.style.backgroundColor = "";
    }
}


