/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.univaq.tirocinio.datamodel.*;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class Modify extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, String utype, int userid) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            if(utype.equals("stud") || utype.equals("admin")){
                Utente utente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(userid);
                request.setAttribute("utente", utente);
                res.activate("modify_user.ftl.html", request, response);
            }else{
                Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                request.setAttribute("azienda", azienda);
                res.activate("modify_company.ftl.html", request, response);
            }
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_modify(HttpServletRequest request, HttpServletResponse response, int userid, String usertype) throws IOException, ServletException, TemplateManagerException, ParseException, NoSuchAlgorithmException {
        try {
            //tutti i campi devono essere riempiti
            boolean no_update = false;
            Azienda azienda;
            Utente utente;
            String current_username = "";
            //controllo campo username
            if(request.getParameter("username")==null || request.getParameter("username").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                //si controlla che l'username inserito o è quello vecchio o è uno nuovo ma non già scelto da altri utenti            
                if(usertype.equals("stud") || usertype.equals("admin")){
                    utente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(userid);
                    current_username = utente.getUsername();
                }else if(usertype.equals("comp")){
                    azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                    current_username = azienda.getUsername();
                }
                if(!(current_username.equals(request.getParameter("username")))){
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
                }
            }
            //controllo campo password e rpassword
            if(request.getParameter("password")==null || request.getParameter("password").equals("") || request.getParameter("rpassword")==null || request.getParameter("rpassword").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                //controllo che la password inserita sia stata ripetuta correttamente
                if(!(request.getParameter("password").equals(request.getParameter("rpassword")))){
                    request.setAttribute("passwordmessage", "You have to repeat the right password!");
                    no_update = true;
                }
            }
            //controllo gli altri campi inseriti
            if(usertype.equals("stud") || usertype.equals("admin")){
                //controllo campo residenza
                if(request.getParameter("residenza")==null || request.getParameter("residenza").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo telefono
                if(request.getParameter("telefono")==null || request.getParameter("telefono").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo handicap
                if(request.getParameter("handicap")==null || request.getParameter("handicap").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo email
                if(request.getParameter("email")==null || request.getParameter("email").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo cdl
                if(request.getParameter("cdl")==null || request.getParameter("cdl").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo laurea
                if(request.getParameter("laurea")==null || request.getParameter("laurea").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo dottorato
                if(request.getParameter("dottorato")==null || request.getParameter("dottorato").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo specializzazione
                if(request.getParameter("specializzazione")==null || request.getParameter("specializzazione").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
            }else if(usertype.equals("comp")){
                //controllo campo nome
                if(request.getParameter("nome")==null || request.getParameter("nome").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo ragionesociale
                if(request.getParameter("ragionesociale")==null || request.getParameter("ragionesociale").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo indirizzo
                if(request.getParameter("indirizzo")==null || request.getParameter("indirizzo").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo partitaiva
                if(request.getParameter("partitaiva")==null || request.getParameter("partitaiva").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo codicefisc
                if(request.getParameter("codicefisc")==null || request.getParameter("codicefisc").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo nomerappr
                if(request.getParameter("nomerappr")==null || request.getParameter("nomerappr").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo cognomerappr
                if(request.getParameter("cognomerappr")==null || request.getParameter("cognomerappr").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo nomeresp
                if(request.getParameter("nomeresp")==null || request.getParameter("nomeresp").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo cognomeresp
                if(request.getParameter("cognomeresp")==null || request.getParameter("cognomeresp").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo telefonoresp
                if(request.getParameter("telefonoresp")==null || request.getParameter("telefonoresp").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo emailresp
                if(request.getParameter("emailresp")==null || request.getParameter("emailresp").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
                //controllo campo foro
                if(request.getParameter("foro")==null || request.getParameter("foro").equals("")){
                    request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                    no_update = true;
                }
            }
            if(!no_update){
                if(usertype.equals("stud") || usertype.equals("admin")){
                    Utente u = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(userid);                             
                    u.setResidenza(SecurityLayer.addSlashes(request.getParameter("residenza")));
                    u.setTelefono(SecurityLayer.addSlashes(request.getParameter("telefono")));
                    u.setCdl(SecurityLayer.addSlashes(request.getParameter("cdl")));
                    u.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
                    u.setLaurea(SecurityLayer.addSlashes(request.getParameter("laurea")));
                    u.setDottorato(SecurityLayer.addSlashes(request.getParameter("dottorato")));
                    u.setSpecializzazione(SecurityLayer.addSlashes(request.getParameter("specializzazione")));
                    u.setEmailUtente(SecurityLayer.addSlashes(request.getParameter("email")));
                    u.setUsername(SecurityLayer.addSlashes(request.getParameter("username")));
                    String password = SecurityLayer.addSlashes(request.getParameter("password"));
                    u.setPassword(SecurityLayer.securePassword(password));
                    ((InternShipDataLayer)request.getAttribute("datalayer")).storeStudente(u);
                    action_activate(request, response, userid, usertype);
                }else if(usertype.equals("comp")){
                    Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).creaAzienda();
                    a.setNomeAzienda(SecurityLayer.addSlashes(request.getParameter("nome")));
                    a.setRagioneSociale(SecurityLayer.addSlashes(request.getParameter("ragionesociale")));
                    a.setIndirizzo(SecurityLayer.addSlashes(request.getParameter("indirizzo")));
                    a.setPartitaIva(SecurityLayer.addSlashes(request.getParameter("partitaiva")));
                    a.setCodiceFisc(SecurityLayer.addSlashes(request.getParameter("codicefisc")));
                    a.setNomeRappr(SecurityLayer.addSlashes(request.getParameter("nomerappr")));
                    a.setCognomeRappr(SecurityLayer.addSlashes(request.getParameter("cognomerappr")));
                    a.setNomeResp(SecurityLayer.addSlashes(request.getParameter("nomeresp")));
                    a.setCognomeResp(SecurityLayer.addSlashes(request.getParameter("cognomeresp")));
                    a.setTelefonoResp(SecurityLayer.addSlashes(request.getParameter("telefonoresp")));
                    a.setEmailResp(SecurityLayer.addSlashes(request.getParameter("emailresp")));
                    a.setForo(SecurityLayer.addSlashes(request.getParameter("foro")));
                    a.setIdAzienda(userid);
                    a.setUsername(SecurityLayer.addSlashes(request.getParameter("username")));
                    String password = SecurityLayer.addSlashes(request.getParameter("password"));
                    a.setPassword(SecurityLayer.securePassword(password));
                    ((InternShipDataLayer)request.getAttribute("datalayer")).storeAzienda(a);
                    action_activate(request, response, userid, usertype);
                }
            }else{
                //è stato rilevato un errore nei campi inseriti
                if(usertype.equals("stud") || usertype.equals("admin")){
                    request.setAttribute("page_title", "Modifica Utente");
                }else{
                    request.setAttribute("page_title", "Modifica Azienda");
                }
                action_default(request, response, usertype, userid);
            }
        }catch(DataLayerException ex){            
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int userid, String usertype) throws IOException, ServletException, TemplateManagerException {
        try {
            HttpSession s = SecurityLayer.checkSession(request);
            if(usertype.equals("stud") || usertype.equals("admin")){
                Utente utente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(userid);
                request.setAttribute("utente", utente);
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            }else if(usertype.equals("comp")){
                Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                request.setAttribute("azienda", azienda);
            }
            request.setAttribute("Session", s);
            response.sendRedirect("profile?uid="+userid+"&utype="+usertype);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException { 
            try{
                HttpSession s = SecurityLayer.checkSession(request);
                if(s!=null){
                    int userid = (int) s.getAttribute("userid");
                    String usertype = (String) s.getAttribute("type");            
                    if(request.getParameter("update")!=null){
                        //se l'azione di modifica è stata inviata, la eseguo
                        action_modify(request, response, userid, usertype);
                    }else{
                        if(request.getParameter("uid")!=null){
                            int uid = SecurityLayer.checkNumeric(request.getParameter("uid"));
                            String utype = request.getParameter("utype");
                            if(uid == userid){
                                //se l'utente che si vuole modificare è lo stesso di quello in sessione, posso modificare
                                if(utype.equals("stud") || utype.equals("admin")){
                                    request.setAttribute("page_title", "Modifica Utente");
                                    request.setAttribute("Session", s);
                                    action_default(request, response, utype, userid);
                                }else if(utype.equals("comp")){
                                    request.setAttribute("page_title", "Modifica Azienda");
                                    request.setAttribute("Session", s);
                                    action_default(request, response, utype, userid);
                                }
                            }else{
                                //non sei lo stesso utente che si vuole modificare
                                response.sendRedirect("profile?uid="+userid+"&utype="+usertype);
                            }
                        }else{
                            //id dell'utente da modificare non specificato
                            response.sendRedirect("profile?uid="+userid+"&utype="+usertype);
                        }
                    }
                }else{
                    //sei anonimo
                    response.sendRedirect("home");
                }
            }catch (IOException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }catch (TemplateManagerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
        }catch (ParseException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (NoSuchAlgorithmException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }  
    }
    
}



