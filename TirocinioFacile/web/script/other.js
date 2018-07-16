/*
 * OUTLINE
 * Gestione sidebar: pertura e chiusura con overlay
 */

function w3_open() {
    var mySidebar = document.getElementById("mySidebar");
    var overlayBg = document.getElementById("myOverlay");
    if (mySidebar.style.display === 'block') {
        mySidebar.style.display = 'none';
        overlayBg.style.display = "none";
    } else {
        mySidebar.style.display = 'block';
        overlayBg.style.display = "block";
    }
}

function w3_close() {
    var mySidebar = document.getElementById("mySidebar");
    var overlayBg = document.getElementById("myOverlay");
    mySidebar.style.display = "none";
    overlayBg.style.display = "none";
}

/*
 * HOME ANONIMO
 * Generazione pulsanti login, abilitazione e disabilitazione onclick
 */

function generateButtons() {
    document.getElementById("log_stud").style.display = "block"
    var x = document.getElementById("button1");
    var y = document.getElementById("button2");
    var z = document.getElementById("ulogin");
    var w = document.getElementById("labelLogin");
    x.style.display = "block";
    y.style.display = "block";
    z.style.display = "block";
    w.style.display = "block";
}

function show(id) {
    var x = document.getElementById(id);
    if (id == "log_stud") {
        var y = document.getElementById("log_az");
    } else {
        var y = document.getElementById("log_stud");
    }
    if (x.style.display === "none") {
        y.style.display = "none";
        x.style.display = "block";
    }
}

/*
 *  FILTRO AZIENDE
 */

function searchCompany() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("azTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

/*
 * STAMPA CONVENZIONE E PROGETTO FORMATIVO
 */

function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    var originalContents = document.body.innerHTML;
    document.body.innerHTML = printContents;
    window.print();
    document.body.innerHTML = originalContents;
}