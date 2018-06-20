//LOGIN FORM
var x = document.getElementById("form_login");
var createbutton1 = document.createElement('input');
createbutton1.setAttribute("class", "w3-button w3-block w3-yellow w3-round");
createbutton1.setAttribute("style", "width:50%; float:right;");
createbutton1.setAttribute("type", "button")
createbutton1.setAttribute("name", "button1")
createbutton1.setAttribute("onclick", "show('log_az')");
createbutton1.setAttribute("value", "Login Azienda");
x.appendChild(createbutton1);

var createbutton2 = document.createElement('input');
createbutton2.setAttribute("class", "w3-button w3-block w3-red w3-round");
createbutton2.setAttribute("style", "width:50%; float:right;");
createbutton2.setAttribute("type", "button")
createbutton2.setAttribute("name", "button1")
createbutton2.setAttribute("onclick", "show('log_stud')");
createbutton2.setAttribute("value", "Login Studente");
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

var loginStud = document.createElement('input');
loginStud.setAttribute("type", "hidden");
loginStud.setAttribute("name", "tid");
loginStud.setAttribute("value", "");
y.appendChild(loginStud);

var paragraf = document.createElement('p');
y.appendChild(paragraf)

var setUsername = document.createElement('input');
setUsername.setAttribute("class", "w3-input w3-animate-input w3-yellow");
setUsername.setAttribute("type", "text");
setUsername.setAttribute("name", "username");
setUsername.setAttribute("value", "");
setUsername.setAttribute("style", "width:60%");
y.appendChild(setUsername);

var usernameLabel = document.createElement('label'); // Create Label for Name Field
usernameLabel.innerHTML = "Username : "; // Set Field Labels
y.appendChild(usernameLabel);

/*
var x = document.getElementById("form_sample");
var createform = document.createElement('form'); // Create New Element Form
createform.setAttribute("action", ""); // Setting Action Attribute on Form
createform.setAttribute("method", "post"); // Setting Method Attribute on Form
x.appendChild(createform);

var heading = document.createElement('h2'); // Heading of Form
heading.innerHTML = "Contact Form ";
createform.appendChild(heading);

var line = document.createElement('hr'); // Giving Horizontal Row After Heading
createform.appendChild(line);

var linebreak = document.createElement('br');
createform.appendChild(linebreak);

var namelabel = document.createElement('label'); // Create Label for Name Field
namelabel.innerHTML = "Your Name : "; // Set Field Labels
createform.appendChild(namelabel);

var inputelement = document.createElement('input'); // Create Input Field for Name
inputelement.setAttribute("type", "text");
inputelement.setAttribute("name", "dname");
createform.appendChild(inputelement);

var linebreak = document.createElement('br');
createform.appendChild(linebreak);

var emaillabel = document.createElement('label'); // Create Label for E-mail Field
emaillabel.innerHTML = "Your Email : ";
createform.appendChild(emaillabel);

var emailelement = document.createElement('input'); // Create Input Field for E-mail
emailelement.setAttribute("type", "text");
emailelement.setAttribute("name", "demail");
createform.appendChild(emailelement);

var emailbreak = document.createElement('br');
createform.appendChild(emailbreak);

var messagelabel = document.createElement('label'); // Append Textarea
messagelabel.innerHTML = "Your Message : ";
createform.appendChild(messagelabel);

var texareaelement = document.createElement('textarea');
texareaelement.setAttribute("name", "dmessage");
createform.appendChild(texareaelement);

var messagebreak = document.createElement('br');
createform.appendChild(messagebreak);

var submitelement = document.createElement('input'); // Append Submit Button
submitelement.setAttribute("type", "submit");
submitelement.setAttribute("name", "dsubmit");
submitelement.setAttribute("value", "Submit");
createform.appendChild(submitelement);*/