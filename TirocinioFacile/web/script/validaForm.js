


function validaEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}



function valida(input){
   
    var sp=document.createElement("div");
    sp.textContent="*campo obbligatorio";
    
    if(input.value==""){
         input.parentNode.appendChild(sp);         
    }   
    else{
        sp.textContent="";
    }
    if(input.name=="email"){
        if(!validaEmail(input.value)){
            sp.textContent="email non valida";
        }
        else{
            sp.textContent="email valida";
        }
    }
   
}

function scanInputs() {
	 var inputs = document.getElementsByTagName('input');
	 var i;
	 
	 for(i=0; i<inputs.length; ++i) {	 
		if (inputs.item(i).type=="text") {
                        inputs.item(i).addEventListener("change",valida);
                        valida(inputs.item(i));
		}	 
	 }
}


function init() {
	scanInputs();
}

window.onload=init;

