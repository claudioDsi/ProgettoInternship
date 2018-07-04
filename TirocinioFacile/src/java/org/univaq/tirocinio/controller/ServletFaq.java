/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;

/**
 *
 * @author claudio
 */
public class ServletFaq extends InternshipDBController{
    
    private void action_error(HttpServletRequest request, HttpServletResponse response){
        if (request.getAttribute("exception")!=null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
         
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try{
             TemplateResult res = new TemplateResult(getServletContext());
             HttpSession s = SecurityLayer.checkSession(request);
             if(s!=null){
                 request.setAttribute("Session", s);
             }
             res.activate("faq.ftl.html", request, response);   
        }catch(TemplateManagerException tex){
            action_error(request, response);
            tex.getMessage();            
        }      
    }

}
