/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;

/**
 *
 * @author claudio
 */
public class Homepage extends InternshipDBController {
    
    private void action_show_home(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        List<Tirocinio> list = new ArrayList();
        try{
            TemplateResult res = new TemplateResult(getServletContext());           
            list = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaTirocini();              
            request.setAttribute("tirocini", list);
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                request.setAttribute("Session", s);
            }
            res.activate("home.ftl.html", request, response);   
        }catch(DataLayerException dte){
            request.setAttribute("message", "Data access exception: " + dte.getMessage());
            action_error(request, response);
        }             
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response){
        if (request.getAttribute("exception")!=null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException{
        request.setAttribute("page_title", "Homepage");
        try{         
            action_show_home(request, response);          
        }catch(IOException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }  
    }

}
