/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class Search extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                request.setAttribute("Session", s);
            }
            res.activate("search.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            Map<String,Object> parametri = new HashMap();
            List<Tirocinio> tirocini = new ArrayList<>();
            parametri.put("azienda", request.getParameter("azienda"));
            parametri.put("luogo", request.getParameter("luogo"));
            parametri.put("numore", request.getParameter("numore"));
            parametri.put("nummesi", request.getParameter("nummesi"));
            parametri.put("settore", request.getParameter("settore"));
            tirocini = ((InternShipDataLayer)request.getAttribute("datalayer")).searchTirocini(parametri);
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                request.setAttribute("Session", s);
            }
            request.setAttribute("tirocini", tirocini);
            res.activate("search_result.ftl.html", request, response);
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException {
        try{
            if(request.getParameter("search")!=null){
                //è stata fatta una ricerca ed in base ai filtri verranno trovati i risultati
                request.setAttribute("page_title", "Risultati ricerca");
                action_search(request, response);
            }else{
                //mostro la pagina per cercare i tirocini
                request.setAttribute("page_title", "Ricerca tirocini");
                action_default(request, response);
            }
        }catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);        
        }  
    }
    
}