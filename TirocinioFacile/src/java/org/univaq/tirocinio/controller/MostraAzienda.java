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
import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;

/**
 *
 * @author claudio
 */
public class MostraAzienda extends InternshipDBController {
    
    private void action_show_aziende(HttpServletRequest request, HttpServletResponse response, int key) throws IOException, ServletException, TemplateManagerException{
        try{
            TemplateResult res = new TemplateResult(getServletContext());
            Azienda az = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(key);
            request.setAttribute("azienda", az);
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            res.activate("show_company_by_id.ftl.html", request, response);          
        }catch(DataLayerException dte){
            request.setAttribute("message","Errore nel datalayer"+ dte.getMessage());
            action_error(request, response);
        }       
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response){
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        request.setAttribute("page_title", "Riepilogo tirocini");
        int n;
        try{
            action_show_aziende(request, response,1);               
        }catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}
