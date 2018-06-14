/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.Utente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class Login extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            if(request.getParameter("tid")!=null){
                String tirocinio_id = request.getParameter("tid");
                request.setAttribute("tid", tirocinio_id);
            }else{
                request.setAttribute("tid", "");
            }
            res.activate("login.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Utente u = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtenteByLogin(username, password);
            if(u!=null){
                if(username.equals(u.getUsername()) && password.equals(u.getPassword()) && u.getPrivilegi()==0){
                    HttpSession s = SecurityLayer.createSession(request, u.getUsername(), u.getIdUtente(), u.getPrivilegi(), "admin");
                        request.setAttribute("Session", s);
                }else{
                    if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
                        HttpSession s = SecurityLayer.createSession(request, u.getUsername(), u.getIdUtente(), u.getPrivilegi(), "stud");
                        request.setAttribute("Session", s);
                    }
                }
            }else{
                Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAziendaByLogin(username, password);
                if(a!=null){
                    if(username.equals(a.getUsername()) && password.equals(a.getPassword())){
                        HttpSession s = SecurityLayer.createSession(request, a.getUsername(), a.getIdAzienda(), a.getPrivilegi(), "comp");
                        request.setAttribute("Session", s);
                    }
                }
            }           
            if(request.getParameter("tid").equals("")){
                res.activate("result.ftl.html", request, response);                
            }else{
                String id_tirocinio = request.getParameter("tid");
                response.sendRedirect("show?tid="+id_tirocinio);
            }
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.setAttribute("page_title", "Login Utente");
        try{
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                if(request.getParameter("login")!=null){
                    // se il parametro login ha un valore assegnato allora inserisco faccio loggare l'utente
                    action_login(request, response);
                }else{
                    // altrimenti mostro la pagina per loggarsi
                    action_default(request, response);
                }
            }else{
                response.sendRedirect("home");
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
