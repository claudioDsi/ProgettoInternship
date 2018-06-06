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
import it.example.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class Modify extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, String utype) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            if(utype.equals("stud")){
                res.activate("modify_user.ftl.html", request, response);
            }else{
                res.activate("modify_company.ftl.html", request, response);
            }
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_modify(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            Utente u;
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            u = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtenteByLogin(username, password);
            if(u!=null){
                if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
                    HttpSession s = SecurityLayer.createSession(request, u.getUsername(), u.getIdUtente(), u.getPrivilegi(), "stud");
                    request.setAttribute("Session", s);
                }
            }else{
                Azienda a;
                a = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAziendaByLogin(username, password);
                if(a!=null){
                    if(username.equals(a.getUsername()) && password.equals(a.getPassword())){
                        HttpSession s = SecurityLayer.createSession(request, a.getUsername(), a.getIdAzienda(), a.getPrivilegi(), "comp");
                        request.setAttribute("Session", s);
                    }
                }
            }
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
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int) s.getAttribute("userid");
            try{
                if(request.getParameter("update")!=null){
                    action_modify(request, response);
                }else{
                    int uid = SecurityLayer.checkNumeric(request.getParameter("uid"));
                    String utype = request.getParameter("utype");
                    if(uid == userid){
                        if(utype.equals("stud")){
                            request.setAttribute("page_title", "Modifica Utente");
                            action_default(request, response, utype);
                        }else{
                            request.setAttribute("page_title", "Modifica Azienda");
                            action_default(request, response, utype);
                        }
                    }else{
                        response.sendRedirect("profile?uid="+uid+"&utype="+utype);
                    }
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

