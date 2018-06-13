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