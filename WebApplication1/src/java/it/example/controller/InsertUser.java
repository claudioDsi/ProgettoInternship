/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.example.data.impl.*;
import it.example.datamodel.*;
import it.example.datamodel.InternShipDataLayer;
import it.example.framework.data.DataLayerException;
import it.example.framework.result.FailureResult;
import it.example.framework.result.TemplateManagerException;
import it.example.framework.result.TemplateResult;

public class InsertUser extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("register.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void action_write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            Utente u;
            u = ((InternShipDataLayer)request.getAttribute("datalayer")).creaStudente();
            u.setUsername(request.getParameter("username"));
            u.setPassword(request.getParameter("password"));
            u.setPrivilegi(1);
            u.setNome(request.getParameter("nome"));
            u.setCognome(request.getParameter("cognome"));
            u.setDataNasc(request.getParameter("datanasc"));
            u.setLuogoNasc(request.getParameter("luogonasc"));
            u.setResidenza(request.getParameter("residenza"));
            u.setCodFisc(request.getParameter("codfisc"));
            u.setTelefono(request.getParameter("telefono"));
            u.setCdl(request.getParameter("cdl"));
            u.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
            u.setLaurea("laurea");
            u.setDottorato("dottorato");
            u.setSpecializzazione("specializzazione");
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
            request.setAttribute("page_title", "Insert User");
            try{
                if(request.getParameter("add")!=null){
                    action_write(request, response);
                }else{
                    action_default(request, response);
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
