/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import org.univaq.tirocinio.datamodel.*;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class InsertTutore extends InternshipDBController {
    
        private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            request.setAttribute("Session", s);
            res.activate("new_tutor.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, ParseException {
        try {
            //tutti i campi devono essere riempiti
            boolean no_update = false;
            //controllo campo nome
            if(request.getParameter("nome")==null || request.getParameter("nome").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            //controllo campo cognome
            if(request.getParameter("cognome")==null || request.getParameter("cognome").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            //controllo campo datanasc
            if(request.getParameter("datanasc")==null || request.getParameter("datanasc").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            //controllo campo telefono
            if(request.getParameter("telefono")==null || request.getParameter("telefono").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            //controllo campo email
            if(request.getParameter("email")==null || request.getParameter("email").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            if(!no_update){
                TemplateResult res = new TemplateResult(getServletContext());
                Tutore tutore = ((InternShipDataLayer)request.getAttribute("datalayer")).creaTutore();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date_in_string = request.getParameter("datanasc");
                Date date = sdf.parse(date_in_string);
                int userid = SecurityLayer.checkNumeric(request.getParameter("userid"));
                tutore.setNome(SecurityLayer.addSlashes(request.getParameter("nome")));
                tutore.setCognome(SecurityLayer.addSlashes(request.getParameter("cognome")));
                tutore.setDataNasc(date);
                tutore.setTelefono(SecurityLayer.addSlashes(request.getParameter("telefono")));
                tutore.setEmailTutore(SecurityLayer.addSlashes(request.getParameter("email")));
                tutore.setCodAzienda(userid);
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeTutore(tutore);
                action_activate(request, response, tutore.getIdTutore());
            }else{
                //è stato generato un messaggio di errore
                action_default(request, response);
            }
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int user_key) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            Tutore tutore = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTutore(user_key);
            HttpSession s = SecurityLayer.checkSession(request);
            request.setAttribute("Session", s);
            request.setAttribute("tutore", tutore);
            res.activate("result.ftl.html", request, response);
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
                request.setAttribute("page_title", "Inserisci nuovo tutore");
                HttpSession s = SecurityLayer.checkSession(request);            
                if(s!=null){
                    int userid = (int)s.getAttribute("userid"); //id utente in sessione
                    String utype = (String)s.getAttribute("type");
                    if(request.getParameter("add")!=null && utype.equals("comp")){
                        Azienda azienda =((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                        //vedo se l'azienda è abilitata
                        if(azienda.getStatus()){
                            //controllo che l'utente in sessione sia quello che ha inviato la form
                            int form_user = SecurityLayer.checkNumeric(request.getParameter("userid"));
                            if(userid==form_user){
                                action_add(request, response);
                            }else{
                                //ritorno al profilo dell'utente in sessione
                                response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                            }
                        }else{
                            //non sei abilitata
                            response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                        }
                    }else if(utype.equals("comp")){
                        Azienda azienda =((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                        //vedo se l'azienda è abilitata
                        if(azienda.getStatus()){
                            //se sei un'azienda ti mostro la form per aggiungere il tutore
                            action_default(request, response);
                        }else{
                            //non sei abilitata
                            response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                        }  
                    }else{
                        response.sendRedirect("profile?uid="+userid+"&utype="+utype);
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
            }  
    }
    
}