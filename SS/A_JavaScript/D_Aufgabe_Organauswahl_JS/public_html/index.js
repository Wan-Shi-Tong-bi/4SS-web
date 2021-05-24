/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var herz = {bild: "herz.jpg", lat: "cor", beschreibung: "Das Herz (lateinisch Cor, griechisch Kardia, καρδία, oder latinisiert Cardia) ist ein bei verschiedenen Tiergruppen vorkommendes muskuläres Hohlorgan (Hohlmuskel), das mit Kontraktionen Blut oder Hämolymphe durch den Körper pumpt und so die Versorgung aller Organe sichert. Höherentwickelte Herzen, beispielsweise bei den Wirbeltieren, arbeiten wie eine Verdrängerpumpe, indem die Flüssigkeit (Blut) ventilgesteuert aus Blutgefäßen angesaugt wird (bei Säugern Hohl- bzw. Lungenvenen) und durch andere Blutgefäße ausgestoßen wird (bei Säugern Truncus pulmonalis bzw. Aorta)." };
        var niere = {bild: "niere.jpg", lat: "Ren", beschreibung: "Die Niere (lateinisch ren, normalerweise nur im Plural renes, davon abgeleitetes Adjektiv renalis; altgriechisch νεφρός nephrós) ist ein paarig angelegtes Organ des Harnsystems zur Harnbereitung und Regulation des Wasser- und Elektrolythaushalts von Wirbeltieren. In den beiden Nieren werden Blutanteile unterhalb einer gewissen Größe abfiltriert, für den Organismus wichtige Moleküle größtenteils wieder rückresorbiert, andere zusätzlich sezerniert und die wässrige Lösung vor ihrer Ausscheidung konzentriert. Mit den Erkrankungen der Nieren beschäftigen sich vor allem die Nephrologie als Teilgebiet der Inneren Medizin und die Urologie."};
        var leber = {bild: "leber.jpg", lat: "Iecur", beschreibung: "Die Leber (lateinisch iecur, altgriechisch ἧπαρ Hepar) ist das zentrale Organ des Stoffwechsels und die größte Drüse des Körpers bei Wirbeltieren. Die wichtigsten Aufgaben sind die Produktion lebenswichtiger Proteine (z. B. Gerinnungsfaktoren), die Verwertung von Nahrungsbestandteilen (z. B. Speicherung von Glykogen und Vitaminen), die Galleproduktion und damit einhergehend der Abbau und die Ausscheidung von Stoffwechselprodukten, Medikamenten und Giftstoffen (siehe dazu Enterohepatischer Kreislauf). Nährstoffe, die aus dem Darm ins Blut aufgenommen werden, gelangen über die Pfortader (Vena portae) zur Leber und werden dann von dieser je nach Bedarf ans Blut abgegeben oder aus dem Blut entfernt. Sie besteht aus einer linken und rechten Leberhälfte."};
        var current = herz;
        function selectionChanged(){
        var selectedOrgan = document.getElementById("organs").value;
                if (selectedOrgan == "Herz") {
        current = herz;
        } else if (selectedOrgan == "Niere") {
        current = niere;
        } else if (selectedOrgan == "Leber") {
        current = leber;
        }

        document.getElementById("lat").innerHTML = current.lat;
                document.getElementById("beschreibung").innerHTML = current.beschreibung;
                document.getElementById("bild").src = current.bild;
        }


