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
import org.univaq.tirocinio.datamodel.Tutore;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class Stats extends InternshipDBController {
    
    private void action_stats(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
        try{
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            HttpSession s = SecurityLayer.checkSession(request);
            //tutori con più tirocini svolti
            List<Tutore> best_tutori = ((InternShipDataLayer)request.getAttribute("datalayer")).getBestTutors();
            //aziende con valutazioni migliori
            List<Azienda> best_aziende = ((InternShipDataLayer)request.getAttribute("datalayer")).getBestCompanies();
            //aziende con più tirocini assegnati
            List<Azienda> most_stages = ((InternShipDataLayer)request.getAttribute("datalayer")).getCompaniesWithMoreStages();
            request.setAttribute("best_tutori", best_tutori);
            request.setAttribute("best_aziende", best_aziende);
            request.setAttribute("most_stages", most_stages);
            request.setAttribute("Session", s);
            res.activate("stats.ftl.html", request, response);
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
                    String type = (String)s.getAttribute("type");
                    if(type.equals("admin")){
                        //sei admin e mostro la pagina delle statistiche
                        request.setAttribute("page_title", "Statistiche Utenti ed Aziende");
                        action_stats(request, response);
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