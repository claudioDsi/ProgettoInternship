function sidebar(sidebar){
    var side = document.getElementById('sidebar')

    var nav = document.createElement('nav')
    nav.setAttribute("id", "mySidebar")
    nav.setAttribute("class", "w3-sidebar w3-bar-block w3-collapse w3-large w3-theme-l5 w3-animate-left")
    side.appendChild(nav)
    
    var a1 = document.createElement('a')
    a1.setAttribute("href", "javascript:void(0)")
    a1.setAttribute("onclick", "w3_close()")
    a1.setAttribute("class", "w3-right w3-xlarge w3-padding-large w3-hover-black w3-hide-large")
    a1.setAttribute("title", "Close Menu")
    nav.appendChild(a1)
    
    var i = document.createElement('i')
    i.setAttribute("class", "fa fa-remove")
    a1.appendChild(i)
    
    var a2 = document.createElement(a)
    a2.setAttribute("class", "w3-bar-item w3-button w3-hover-black w3-mobile")
    a2.setAttribute("href", "search")
    a2.innerHTML = "Ricerca Tirocinio"
    nav.appendChild(a2)
    
    var a3 = document.createElement(a)
    a3.setAttribute("class", "w3-bar-item w3-button w3-hover-black w3-mobile")
    a3.setAttribute("href", "listaaziende")
    a3.innerHTML = "Lista Aziende"
    nav.appendChild(a3)
    
    var a4 = document.createElement(a)
    a4.setAttribute("class", "w3-bar-item w3-button w3-hover-black w3-mobile")
    a4.setAttribute("href", "faq")
    a4.innerHTML = "FAQ"
    nav.appendChild(a4)
}                