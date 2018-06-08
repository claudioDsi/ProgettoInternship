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
public class Profile extends InternshipDBController {
    
    private void action_company(HttpServletRequest request, HttpServletResponse response, int uid, String utype) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            Azienda a;
            a = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(uid);
            request.setAttribute("azienda", a);
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                int session_userid = (int) s.getAttribute("userid");
                if(uid==session_userid){
                    request.setAttribute("modifica", true);
                }else{
                    request.setAttribute("modifica", false);
                }
            }
            request.setAttribute("Session", s);
            res.activate("profilo.ftl.html", request, response);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_student(HttpServletRequest request, HttpServletResponse response, int uid, String utype) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            Utente u;
            u = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(uid);
            request.setAttribute("utente", u);
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                int session_userid = (int) s.getAttribute("userid");
                if(uid==session_userid){
                    request.setAttribute("modifica", true);
                }else{
                    request.setAttribute("modifica", false);
                }
            }
            request.setAttribute("Session", s);
            res.activate("profilo.ftl.html", request, response);
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
            int uid = SecurityLayer.checkNumeric(request.getParameter("uid"));
            String utype = request.getParameter("utype");
            try{
                if(utype.equals("comp")){
                    request.setAttribute("page_title", "Profilo Azienda");
                    action_company(request, response, uid, utype);
                }else{
                    if(utype.equals("stud")){
                        request.setAttribute("page_title", "Profilo Studente");
                        action_student(request, response, uid, utype);
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