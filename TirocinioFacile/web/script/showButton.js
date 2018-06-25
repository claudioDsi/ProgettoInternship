/*
 * Generazione dei buttons di login studente ed azienda
 */

var x = document.getElementById("button1");
var y = document.getElementById("button2");
var z = document.getElementById("ulogin");
var w = document.getElementById("labelLogin");
x.style.display = "block";
y.style.display = "block";
z.style.display = "block";
w.style.display = "block";

/*
 * Aggiorno la classe della div content per il padding
 */

document.getElementById('content').setAttribute("class", "w3-rest w3-padding-64")

/*
 * Mostra e nasconde le div di login studente e azienda
 */

function show(id) {
    var x = document.getElementById(id);
    if (id == "log_stud"){
        var y = document.getElementById("log_az");
    }
    else{
        var y = document.getElementById("log_stud");
    }
    if (x.style.display === "none") {
        y.style.display = "none";
        x.style.display = "block";
    } else {
        x.style.display = "none";
        y.style.display = "block";
    }
}