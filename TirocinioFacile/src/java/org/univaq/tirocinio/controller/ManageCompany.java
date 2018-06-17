/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.util.List;
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
import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class ManageCompany extends InternshipDBController {
    
    private void action_manage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
        try{
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            List<Azienda> lista_aziende = ((InternShipDataLayer)request.getAttribute("datalayer")).showListaAziende();
            request.setAttribute("lista_aziende", lista_aziende);
            request.setAttribute("Session", s);
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            res.activate("manage_company.ftl.html", request, response);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int aid, int val) throws IOException, ServletException, TemplateManagerException{
        try{
            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(aid);
            if(val==1 && azienda.getStatus()==false){
                ((InternShipDataLayer)request.getAttribute("datalayer")).activateAzienda(azienda);
            }
            action_manage(request, response);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
            try{
                HttpSession s = SecurityLayer.checkSession(request);
                if(s!=null){
                    int userid = (int)s.getAttribute("userid");
                    String type = (String)s.getAttribute("type");
                    if(type.equals("admin")){
                        //sei admin
                        request.setAttribute("page_title", "Gestione delle abilitazioni delle aziende");
                        if(request.getParameter("aid")!=null && request.getParameter("val")!=null){
                            //modifico lo status dell'azienda
                            int aid = SecurityLayer.checkNumeric(request.getParameter("aid"));
                            int val = SecurityLayer.checkNumeric(request.getParameter("val"));
                            action_activate(request, response, aid, val);
                        }else{
                            //mostro la lista delle aziende
                            action_manage(request, response);
                        }
                    }else{
                    //sei un'azienda o un utente
                        response.sendRedirect("home");
                    }
                }else{
                //sei un utente anonimo
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