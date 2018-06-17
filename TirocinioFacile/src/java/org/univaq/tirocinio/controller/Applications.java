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
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class Applications extends InternshipDBController {
    
    private void action_show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid");
            String type = (String)s.getAttribute("type");
            List<Richiesta> lista_richieste = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaRichiesteStudente(userid);
            List<Tirocinio> lista_tirocini_approvati = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaTirociniApprovatiByStudente(userid);
            if(!lista_richieste.isEmpty()){
                request.setAttribute("lista_richieste", lista_richieste);
            }
            if(!lista_tirocini_approvati.isEmpty()){
                request.setAttribute("lista_tirocini_approvati", lista_tirocini_approvati);
            }
            request.setAttribute("Session", s);
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            res.activate("applications.ftl.html", request, response);
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
            try{
                HttpSession s = SecurityLayer.checkSession(request);
                if(s!=null){
                    int userid = (int)s.getAttribute("userid");
                    String type = (String)s.getAttribute("type");
                    if(type.equals("stud")){
                        //sei uno studente e mostro le tue candidature e i tuoi tirocini
                        request.setAttribute("page_title", "Riepilogo Tirocini e Candidature");
                        action_show(request, response);
                    }else if(type.equals("comp")){
                        //sei un'azienda
                        response.sendRedirect("profile?uid=${Session.getAttribute('userid')}&utype=${Session.getAttribute('type')}");
                    }
                }else{
                    //non sei iscritto
                    response.sendRedirect("login");
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