/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Utente;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

public class InsertUser extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("register_user.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void action_write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, ParseException, NoSuchAlgorithmException {
        try {
            Utente u = ((InternShipDataLayer)request.getAttribute("datalayer")).creaStudente();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date_in_string = request.getParameter("datanasc");
            Date date = sdf.parse(date_in_string);
            u.setUsername(SecurityLayer.addSlashes(request.getParameter("username")));
            String password = SecurityLayer.addSlashes(request.getParameter("password"));
            u.setPassword(SecurityLayer.securePassword(password));
            u.setPrivilegi(1);
            u.setNome(SecurityLayer.addSlashes(request.getParameter("nome")));
            u.setCognome(SecurityLayer.addSlashes(request.getParameter("cognome")));
            u.setDataNasc(date);
            u.setLuogoNasc(SecurityLayer.addSlashes(request.getParameter("luogonasc")));
            u.setResidenza(SecurityLayer.addSlashes(request.getParameter("residenza")));
            u.setCodFisc(SecurityLayer.addSlashes(request.getParameter("codfisc")));
            u.setTelefono(SecurityLayer.addSlashes(request.getParameter("telefono")));
            u.setCdl(SecurityLayer.addSlashes(request.getParameter("cdl")));
            u.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
            u.setLaurea(SecurityLayer.addSlashes(request.getParameter("laurea")));
            u.setDottorato(SecurityLayer.addSlashes(request.getParameter("dottorato")));
            u.setSpecializzazione(SecurityLayer.addSlashes(request.getParameter("specializzazione")));
            u.setSesso(SecurityLayer.addSlashes(request.getParameter("sesso")));
            u.setEmailUtente(SecurityLayer.addSlashes(request.getParameter("email")));
            ((InternShipDataLayer)request.getAttribute("datalayer")).storeStudente(u);
            action_activate(request, response, u.getIdUtente());
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int user_key) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            Utente utente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(user_key);
            request.setAttribute("utente", utente);
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
            request.setAttribute("page_title", "Inserisci Studente");
            try{
                HttpSession s = SecurityLayer.checkSession(request);
                if(s==null){
                    //non sei loggato quindi puoi registrarti
                    if(request.getParameter("add")!=null){
                        // se il parametro add ha un valore assegnato allora inserisco l'utente registrato
                        action_write(request, response);
                    }else{
                        // altrimenti mostro la pagina per registrarsi
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
        } catch (ParseException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (NoSuchAlgorithmException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } 
    }
    
}
