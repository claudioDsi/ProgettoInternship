/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
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
    
    private void action_add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            Tutore tutore = ((InternShipDataLayer)request.getAttribute("datalayer")).creaTutore();
            int userid = SecurityLayer.checkNumeric(request.getParameter("userid"));
            tutore.setNome(request.getParameter("nome"));
            tutore.setCognome(request.getParameter("cognome"));
            tutore.setDataNasc(request.getParameter("datanasc"));
            tutore.setTelefono(request.getParameter("telefono"));
            tutore.setCodAzienda(userid);
            ((InternShipDataLayer)request.getAttribute("datalayer")).storeTutore(tutore);
            action_activate(request, response, tutore.getIdTutore());
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int user_key) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
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
            request.setAttribute("page_title", "Inserisci nuovo tutore");
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid"); //id utente in sessione
            String utype = (String)s.getAttribute("type");
            try{
                if(request.getParameter("add")!=null){
                    //controllo che l'utente in sessione sia quello che ha inviato la form
                    int form_user = SecurityLayer.checkNumeric(request.getParameter("userid"));
                    if(userid==form_user){
                        action_add(request, response);
                    }else{
                        //ritorno al profilo dell'utente in sessione
                        response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                    }
                }else if(utype.equals("comp")){
                    //se sei un'azienda ti mostro la form per agigungere il tutore
                    action_default(request, response);
                }else{
                    response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                }
            }catch (IOException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }catch (TemplateManagerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
        }  
    }
    
}