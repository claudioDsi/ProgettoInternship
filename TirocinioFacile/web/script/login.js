//LOGIN FORM
var x = document.getElementById("form_login");

var createbutton1 = document.createElement('input');
createbutton1.setAttribute("class", "w3-button w3-block w3-yellow w3-round");
createbutton1.setAttribute("style", "width:50%; float:right;");
createbutton1.setAttribute("type", "button")
createbutton1.setAttribute("name", "button1")
createbutton1.setAttribute("onclick", "show('log_stud')");
createbutton1.setAttribute("value", "Login Studente");
x.appendChild(createbutton1);

var createbutton2 = document.createElement('input');
createbutton2.setAttribute("class", "w3-button w3-block w3-red w3-round");
createbutton2.setAttribute("style", "width:50%; float:right;");
createbutton2.setAttribute("type", "button")
createbutton2.setAttribute("name", "button2")
createbutton2.setAttribute("onclick", "show('log_az')");
createbutton2.setAttribute("value", "Login Azienda");
x.appendChild(createbutton2);

var createStudDiv = document.createElement('div');
createStudDiv.setAttribute("id", "log_stud");
createStudDiv.setAttribute("class", "w3-yellow w3-round");
createStudDiv.setAttribute("style", "display:block;");
x.appendChild(createStudDiv);

var y = document.getElementById("log_stud");

var createFormStud = document.createElement('form');
createFormStud.setAttribute("class", "w3-container w3-card-4");
createFormStud.setAttribute("method", "post");
createFormStud.setAttribute("action", "login");
y.appendChild(createFormStud);

var loginInput = document.createElement('input');
loginInput.setAttribute("type", "hidden");
loginInput.setAttribute("name", "tid");
loginInput.setAttribute("value", "");
y.appendChild(loginInput);

var line = document.createElement('br');
y.appendChild(line);

var setUsername = document.createElement('input');
setUsername.setAttribute("class", "w3-input w3-animate-input w3-yellow");
setUsername.setAttribute("type", "text");
setUsername.setAttribute("name", "username");
setUsername.setAttribute("value", "");
setUsername.setAttribute("style", "width:60%; margin-left:15px");
y.appendChild(setUsername);

var usernameLabel = document.createElement('label'); // Create Label for Name Field
usernameLabel.setAttribute("style", "margin-left:15px;")
usernameLabel.innerHTML = "Username"; // Set Field Labels
y.appendChild(usernameLabel);

var paragraf = document.createElement('p');
y.appendChild(paragraf);

var setPassword = document.createElement('input');
setPassword.setAttribute("class", "w3-input w3-animate-input w3-yellow");
setPassword.setAttribute("type", "text");
setPassword.setAttribute("name", "username");
setPassword.setAttribute("value", "");
setPassword.setAttribute("style", "width:60%; margin-left:15px");
y.appendChild(setPassword);

var passwordLabel = document.createElement('label'); // Create Label for Name Field
passwordLabel.setAttribute("style", "margin-left:15px");
passwordLabel.innerHTML = "Password"; // Set Field Labels
y.appendChild(passwordLabel);

y.appendChild(paragraf);

var loginButton = document.createElement('input');
loginButton.setAttribute("class", "w3-button w3-section w3-teal w3-ripple");
loginButton.setAttribute("type", "submit");
loginButton.setAttribute("style", "margin-left:15px;")
loginButton.setAttribute("name", "login");
loginButton.setAttribute("value", "Login")
y.appendChild(loginButton);

var createAzDiv = document.createElement('div');
createAzDiv.setAttribute("id", "log_az");
createAzDiv.setAttribute("class", "w3-red w3-round");
createAzDiv.setAttribute("style", "display:none;");
x.appendChild(createAzDiv);

var z = document.getElementById("log_az");

var createFormAz = document.createElement('form');
createFormAz.setAttribute("class", "w3-container w3-card-4");
createFormAz.setAttribute("method", "post");
createFormAz.setAttribute("action", "login");
z.appendChild(createFormAz);

z.appendChild(loginInput);

z.appendChild(line);

var setUsernameAz = document.createElement('input');
setUsernameAz.setAttribute("class", "w3-input w3-animate-input w3-red");
setUsernameAz.setAttribute("type", "text");
setUsernameAz.setAttribute("name", "username");
setUsernameAz.setAttribute("value", "");
setUsernameAz.setAttribute("style", "width:60%; margin-left:15px");
z.appendChild(setUsernameAz);

z.appendChild(usernameLabel);

z.appendChild(paragraf);

var setPasswordAz = document.createElement('input');
setPasswordAz.setAttribute("class", "w3-input w3-animate-input w3-red");
setPasswordAz.setAttribute("type", "text");
setPasswordAz.setAttribute("name", "username");
setPasswordAz.setAttribute("value", "");
setPasswordAz.setAttribute("style", "width:60%; margin-left:15px");
z.appendChild(setPasswordAz);

z.appendChild(passwordLabel);

z.appendChild(paragraf);

z.appendChild(loginButton);