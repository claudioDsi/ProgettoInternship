function topnav (id){
    var bar = document.getElementById(id);

    var w = window.innerWidth
    || document.documentElement.clientWidth
    || document.body.clientWidth;

    var h = window.innerHeight
    || document.documentElement.clientHeight
    || document.body.clientHeight;

    if (w>=1000){
        bar.setAttribute("class", "w3-top");
    }

    var divPrincipale = document.createElement('div');
    divPrincipale.setAttribute("class", "w3-bar w3-theme w3-border w3-row w3-mobile");
    bar.appendChild(divPrincipale);

    var divLogo = document.createElement('div');
    divLogo.setAttribute("class", "w3-container w3-left w3-rest w3-mobile");
    divPrincipale.appendChild(divLogo);

    var collapseSide = document.createElement('a');
    collapseSide.setAttribute("id", "collapse");
    collapseSide.setAttribute("class", "w3-bar-item w3-button w3-right w3-hide-large w3-hover-white w3-large w3-theme");
    collapseSide.setAttribute("href", "javascript:void(0)");
    collapseSide.setAttribute("onclick", "w3_open()");
    divLogo.appendChild(collapseSide);

    var iButton = document.createElement('i');
    iButton.setAttribute("class", "fa fa-bars");
    collapseSide.appendChild(iButton);

    var aImg = document.createElement('a');
    aImg.setAttribute("class", "w3-bar-item w3-button w3-mobile");
    aImg.setAttribute("href", "home");
    divLogo.appendChild(aImg);

    var logo = document.createElement('img');
    logo.setAttribute("src", "images/internship.png");
    logo.setAttribute("style", "width: 110px; height: 35px;");
    aImg.appendChild(logo);

    var divOther = document.createElement('div');
    divOther.setAttribute("class", "w3-col w3-container w3-right w3-mobile");
    divOther.setAttribute("style","width:350px");
    divPrincipale.appendChild(divOther);

    var home = document.createElement('a');
    home.setAttribute("href", "home");
    home.setAttribute("class", "w3-bar-item w3-button w3-xlarge w3-mobile");
    home.innerHTML = "Home";
    divOther.appendChild(home);

    var contact = document.createElement('a');
    contact.setAttribute("href", "contact");
    contact.setAttribute("class", "w3-bar-item w3-button w3-xlarge w3-mobile");
    contact.innerHTML = "Contatti";
    divOther.appendChild(contact);

    if(id == "navbar2"){
        var login = document.createElement('a');
        login.setAttribute("href", "login");
        login.setAttribute("class", "w3-bar-item w3-button w3-xlarge w3-mobile");
        login.innerHTML = "Login";
        divOther.appendChild(login);
    }
    else{
        var logout = document.createElement('a');
        logout.setAttribute("href", "logout");
        logout.setAttribute("class", "w3-bar-item w3-button w3-xlarge w3-mobile");
        logout.innerHTML = "Logout";
        divOther.appendChild(logout);
    }
}