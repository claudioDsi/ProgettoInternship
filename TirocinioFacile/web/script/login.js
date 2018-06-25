//LOGIN FORM
var x = document.getElementById("log");

var createbutton1 = document.createElement('input');
createbutton1.setAttribute("class", "w3-button w3-block w3-blue w3-round");
createbutton1.setAttribute("style", "width:50%; float:right;");
createbutton1.setAttribute("type", "button");
createbutton1.setAttribute("name", "button1");
createbutton1.setAttribute("onclick", "show('log_stud')");
createbutton1.setAttribute("value", "Login Studente");
x.appendChild(createbutton1);

var createbutton2 = document.createElement('input');
createbutton2.setAttribute("class", "w3-button w3-block w3-cyan w3-round");
createbutton2.setAttribute("style", "width:50%; float:right;");
createbutton2.setAttribute("type", "button");
createbutton2.setAttribute("name", "button2");
createbutton2.setAttribute("onclick", "show('log_az')");
createbutton2.setAttribute("value", "Login Azienda");
x.appendChild(createbutton2);

var createStudDiv = document.createElement('div');
createStudDiv.setAttribute("id", "log_stud");
createStudDiv.setAttribute("class", "w3-blue w3-round");
createStudDiv.setAttribute("style", "display:block;");
x.appendChild(createStudDiv);

var y = document.getElementById("log_stud");

var createFormStud = document.createElement('form');
createFormStud.setAttribute("class", "w3-container w3-card-4");
createFormStud.setAttribute("method", "post");
createFormStud.setAttribute("action", "login");
y.appendChild(createFormStud);

var loginInput1 = document.createElement('input');
loginInput1.setAttribute("type", "hidden");
loginInput1.setAttribute("name", "tid");
loginInput1.setAttribute("value", "");
y.appendChild(loginInput1);

var line1 = document.createElement('br');
y.appendChild(line1);

var setUsernameSt = document.createElement('input');
setUsernameSt.setAttribute("class", "w3-input w3-animate-input");
setUsernameSt.setAttribute("type", "text");
setUsernameSt.setAttribute("name", "username");
setUsernameSt.setAttribute("value", "");
setUsernameSt.setAttribute("style", "width:50%; margin-left:15px; max-width:90%;");
y.appendChild(setUsernameSt);

var usernameStLabel = document.createElement('label'); // Create Label for Name Field
usernameStLabel.setAttribute("style", "margin-left:15px;")
usernameStLabel.innerHTML = "Username"; // Set Field Labels
y.appendChild(usernameStLabel);

var paragraf1 = document.createElement('p');
y.appendChild(paragraf1);

var setStPassword = document.createElement('input');
setStPassword.setAttribute("class", "w3-input w3-animate-input");
setStPassword.setAttribute("type", "password");
setStPassword.setAttribute("name", "password");
setStPassword.setAttribute("value", "");
setStPassword.setAttribute("style", "width:50%; margin-left:15px; max-width:90%;");
y.appendChild(setStPassword);

var passwordStLabel = document.createElement('label'); // Create Label for Name Field
passwordStLabel.setAttribute("style", "margin-left:15px");
passwordStLabel.innerHTML = "Password"; // Set Field Labels
y.appendChild(passwordStLabel);

var paragraf2 = document.createElement('p');
y.appendChild(paragraf2);

//<input class="w3-button w3-section w3-teal w3-ripple" type="submit" name="login" value="Login"/>
var loginStButton = document.createElement('input');
loginStButton.setAttribute("class", "w3-button w3-section w3-teal w3-ripple");
loginStButton.setAttribute("type", "submit");
loginStButton.setAttribute("name", "login");
loginStButton.setAttribute("value", "Login");
loginStButton.setAttribute("style", "margin-left:15px;");
y.appendChild(loginStButton);

var pReg1 = document.createElement('p');
pReg1.setAttribute("style", "margin-left:15px")
pReg1.innerHTML = "Non sei ancora inscritto? ";
y.appendChild(pReg1);

var registrButton1 = document.createElement('a');
registrButton1.setAttribute("href", "user");
registrButton1.innerHTML = "Registrati"
pReg1.appendChild(registrButton1);

var createAzDiv = document.createElement('div');
createAzDiv.setAttribute("id", "log_az");
createAzDiv.setAttribute("class", "w3-cyan w3-round");
createAzDiv.setAttribute("style", "display:none;");
x.appendChild(createAzDiv);

var z = document.getElementById("log_az");

var createFormAz = document.createElement('form');
createFormAz.setAttribute("class", "w3-container w3-card-4");
createFormAz.setAttribute("method", "post");
createFormAz.setAttribute("action", "login");
z.appendChild(createFormAz);

var loginInput2 = document.createElement('input');
loginInput2.setAttribute("type", "hidden");
loginInput2.setAttribute("name", "tid");
loginInput2.setAttribute("value", "");
z.appendChild(loginInput2);

var line2 = document.createElement('br');
z.appendChild(line2);

var setUsernameAz = document.createElement('input');
setUsernameAz.setAttribute("class", "w3-input w3-animate-input");
setUsernameAz.setAttribute("type", "text");
setUsernameAz.setAttribute("name", "username");
setUsernameAz.setAttribute("value", "");
setUsernameAz.setAttribute("style", "width:50%; margin-left:15px; max-width:90%;");
z.appendChild(setUsernameAz);

var usernameAzLabel = document.createElement('label'); // Create Label for Name Field
usernameAzLabel.setAttribute("style", "margin-left:15px;")
usernameAzLabel.innerHTML = "Username"; // Set Field Labels
z.appendChild(usernameAzLabel);

var paragraf3 = document.createElement('p');
z.appendChild(paragraf3);


var setPasswordAz = document.createElement('input');
setPasswordAz.setAttribute("class", "w3-input w3-animate-input");
setPasswordAz.setAttribute("type", "password");
setPasswordAz.setAttribute("name", "password");
setPasswordAz.setAttribute("value", "");
setPasswordAz.setAttribute("style", "width:50%; margin-left:15px; max-width:90%;");
z.appendChild(setPasswordAz);

var passwordAzLabel = document.createElement('label'); // Create Label for Name Field
passwordAzLabel.setAttribute("style", "margin-left:15px");
passwordAzLabel.innerHTML = "Password"; // Set Field Labels
z.appendChild(passwordAzLabel);

var paragraf3 = document.createElement('p');
z.appendChild(paragraf3);

var loginAzButton = document.createElement('input');
loginAzButton.setAttribute("class", "w3-button w3-section w3-teal w3-ripple");
loginAzButton.setAttribute("type", "submit");
loginAzButton.setAttribute("style", "margin-left:15px;")
loginAzButton.setAttribute("name", "login");
loginAzButton.setAttribute("value", "Login")
z.appendChild(loginAzButton);

var pReg2 = document.createElement('p');
pReg2.setAttribute("style", "margin-left:15px")
pReg2.innerHTML = "Non sei ancora inscritto? ";
z.appendChild(pReg2);

var registrButton2 = document.createElement('a');
registrButton2.setAttribute("href", "azienda");
registrButton2.innerHTML = "Registrati"
pReg2.appendChild(registrButton2);