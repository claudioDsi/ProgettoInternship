/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Azienda;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;

/**
 *
 * @author vince
 */
public class InsertAzienda extends InternshipDBController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("register_company.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, NoSuchAlgorithmException {
        try {
            //tutti i campi devono essere riempiti
            boolean no_update = false;
            //controllo campo nome
            if(request.getParameter("nome")==null || request.getParameter("nome").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkPlace(request.getParameter("nome"))){
                    request.setAttribute("messaggionome", "Il nome inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo ragione sociale
            if(request.getParameter("ragionesociale")==null){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(!(request.getParameter("ragionesociale").equals(""))){
                    if(SecurityLayer.checkPlace(request.getParameter("ragionesociale"))){
                        request.setAttribute("messaggiocdl", "Ragione sociale non valida!");
                        no_update = true;
                    }
                }
            }
            //controllo campo partita iva
            if(request.getParameter("partitaiva")==null || request.getParameter("partitaiva").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkPartita(request.getParameter("partitaiva"))){
                    request.setAttribute("messaggioiva", "La partita IVA inserita non è valida!");
                    no_update = true;
                }    
            }
            //controllo campo codicefisc
            if(request.getParameter("codicefisc")==null){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(!(request.getParameter("codicefisc").equals(""))){
                    if(SecurityLayer.checkPlace(request.getParameter("codicefisc"))){
                        request.setAttribute("messaggiocodicefisc", "Inserisci un codice fiscale valido!");
                        no_update = true;
                    }   
                }
            }
            //controllo campo indirizzo
            if(request.getParameter("indirizzo")==null || request.getParameter("indirizzo").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkIndirizzo(request.getParameter("indirizzo"))){
                    request.setAttribute("messaggioindirizzo", "L'indirizzo inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo nomerappr
            if(request.getParameter("nomerappr")==null || request.getParameter("nomerappr").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkName(request.getParameter("nomerappr"))){
                    request.setAttribute("messaggionomerappr", "Il nome inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo cognomerappr
            if(request.getParameter("cognomerappr")==null || request.getParameter("cognomerappr").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkName(request.getParameter("cognomerappr"))){
                    request.setAttribute("messaggiocognomerappr", "Il cognome inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo nomeresp
            if(request.getParameter("nomeresp")==null || request.getParameter("nomeresp").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkName(request.getParameter("nomeresp"))){
                    request.setAttribute("messaggionomeresp", "Il nome inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo cognomeresp
            if(request.getParameter("cognomeresp")==null || request.getParameter("cognomeresp").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkName(request.getParameter("cognomeresp"))){
                    request.setAttribute("messaggiocognomeresp", "Il cognome inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo telefonoresp
            if(request.getParameter("telefonoresp")==null || request.getParameter("telefonoresp").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkIsNumeric(request.getParameter("telefonoresp"))){
                    request.setAttribute("messaggiotelefonoresp", "Il numero di telefono inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo emailresp
            if(request.getParameter("emailresp")==null || request.getParameter("emailresp").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkEmail(request.getParameter("emailresp"))){
                    request.setAttribute("messaggioemailresp", "L'email inserita non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo foro
            if(request.getParameter("foro")==null || request.getParameter("foro").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkPlace(request.getParameter("foro"))){
                    request.setAttribute("messaggioforo", "Il foro inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo username
            if(request.getParameter("username")==null || request.getParameter("username").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkUsername(request.getParameter("username"))){
                    request.setAttribute("messaggiousername", "L'username non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo password
            if(request.getParameter("password")==null || request.getParameter("password").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            //controllo campo rpassword
            if(request.getParameter("rpassword")==null || request.getParameter("rpassword").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            //si controlla che l'username inserito è uno nuovo ma non già scelto da altri utenti
            List<String> lista_username_azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getUsernameAzienda();
            List<String> lista_username_utenti = ((InternShipDataLayer)request.getAttribute("datalayer")).getUsernameUtenti();
            for(int i = 0; i < lista_username_azienda.size(); i++){
                if(lista_username_azienda.get(i).equals(request.getParameter("username"))){
                    request.setAttribute("usernamemessage", "The username is already chosen!");
                    no_update = true;
                }
            }
            for(int i = 0; i < lista_username_utenti.size(); i++){
                if(lista_username_utenti.get(i).equals(request.getParameter("username"))){
                    request.setAttribute("usernamemessage", "The username is already chosen!");
                    no_update = true;
                }
            } 
            //controllo che la password inserita sia stata ripetuta correttamente
            if(!(request.getParameter("password").equals(request.getParameter("rpassword")))){
                request.setAttribute("passwordmessage", "You have to repeat the right password!");
                no_update = true;
            }
            if(!no_update){
                Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).creaAzienda();
                a.setUsername(SecurityLayer.addSlashes(request.getParameter("username")));
                String password = SecurityLayer.addSlashes(request.getParameter("password"));
                a.setPassword(SecurityLayer.securePassword(password));
                a.setPrivilegi(2);
                a.setNomeAzienda(SecurityLayer.addSlashes(request.getParameter("nome")));
                a.setRagioneSociale(SecurityLayer.addSlashes(request.getParameter("ragionesociale")));
                a.setIndirizzo(SecurityLayer.addSlashes(request.getParameter("indirizzo")));
                a.setPartitaIva(SecurityLayer.addSlashes(request.getParameter("partitaiva")));
                a.setStatus(false);
                a.setCodiceFisc(SecurityLayer.addSlashes(request.getParameter("codicefisc")));
                a.setNomeRappr(SecurityLayer.addSlashes(request.getParameter("nomerappr")));
                a.setCognomeRappr(SecurityLayer.addSlashes(request.getParameter("cognomerappr")));
                a.setNomeResp(SecurityLayer.addSlashes(request.getParameter("nomeresp")));
                a.setCognomeResp(SecurityLayer.addSlashes(request.getParameter("cognomeresp")));
                a.setTelefonoResp(SecurityLayer.addSlashes(request.getParameter("telefonoresp")));
                a.setEmailResp(SecurityLayer.addSlashes(request.getParameter("emailresp")));
                a.setForo(SecurityLayer.addSlashes(request.getParameter("foro")));
                a.setValutazione((float)0);
                a.setNumeroTirocini(0);
                a.setNumTiroCompletati(0);
                a.setStatusConvenzione(false);
                a.setIdConvenzione(0);
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeAzienda(a);
                action_activate(request, response, a.getIdAzienda());
            }else{
                //è stato generato un messaggio di errore
                action_default(request, response);
            }
            }catch (DataLayerException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
            }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int azienda_key) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(azienda_key);
            request.setAttribute("azienda", azienda);
            res.activate("result.ftl.html", request, response);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response){
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
            request.setAttribute("page_title", "Inserisci Azienda");
            try{
                HttpSession s = SecurityLayer.checkSession(request);
                if(s==null){
                    //non sei loggato quindi puoi registrarti
                    if(request.getParameter("add")!=null){
                        //se il parametro add ha un valore assegnato allora inserisco l'azienda registrata
                        action_write(request, response);
                    }else{
                        //altrimenti mostro la pagina per registrarsi
                        action_default(request, response);
                    }
                }else{
                    //sei già loggato
                    response.sendRedirect("home");
                }
            }catch (IOException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }catch (TemplateManagerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
        }catch (NoSuchAlgorithmException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }  
    }
    
}