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
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class Search extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                request.setAttribute("Session", s);
            }
            List<Tirocinio> tirocini = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaTirocini();
            request.setAttribute("tiro", tirocini);            
            res.activate("search.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            if(request.getParameter("obiettivi")!=null && request.getParameter("azienda")!=null && request.getParameter("luogo")!=null && request.getParameter("numore")!=null && request.getParameter("nummesi")!=null && request.getParameter("settore")!=null){
                //i campi sono stati inviati tutti
                boolean no_update = false;
                //controllo che il campo numore contenga un numero (intero)
                if(!(request.getParameter("numore").equals(""))){
                    if(SecurityLayer.checkIsNumber(request.getParameter("numore"))){
                        request.setAttribute("messaggionumore", "Il numero di ore inserito deve essere un numero intero");
                        no_update = true;
                    }
                }
                //controllo che il campo numore contenga un numero (intero)
                if(!(request.getParameter("nummesi").equals(""))){
                    if(SecurityLayer.checkIsNumber(request.getParameter("nummesi"))){
                        request.setAttribute("messaggionummesi", "Il numero di mesi inserito deve essere un numero intero");
                        no_update = true;
                    }
                }
                if(!no_update){
                    Map<String,Object> parametri = new HashMap();
                    parametri.put("azienda", SecurityLayer.addSlashes(request.getParameter("azienda")));
                    parametri.put("luogo", SecurityLayer.addSlashes(request.getParameter("luogo")));
                    parametri.put("numore", SecurityLayer.addSlashes(request.getParameter("numore")));
                    parametri.put("nummesi", SecurityLayer.addSlashes(request.getParameter("nummesi")));
                    parametri.put("settore", SecurityLayer.addSlashes(request.getParameter("settore")));
                    List<Tirocinio> tirocini = ((InternShipDataLayer)request.getAttribute("datalayer")).searchTirocini(parametri);
                    List<Tirocinio> tirocini_obiettivi = new ArrayList<Tirocinio>();
                    HttpSession s = SecurityLayer.checkSession(request);
                    if(s!=null){
                        request.setAttribute("Session", s);
                    }
                    //filtro i risultati per la parola chiave specificata in obiettivi
                    if(request.getParameter("obiettivi").equals("")){
                        request.setAttribute("tirocini", tirocini);
                    }else{                   

                        String obiettivo = SecurityLayer.addSlashes(request.getParameter("obiettivi"));
                        for (int i = 0; i < tirocini.size(); i++) {
                            if(tirocini.get(i).getObiettivi().contains(obiettivo)){
                                tirocini_obiettivi.add(tirocini.get(i));
                            }
                        }
                        request.setAttribute("tirocini", tirocini_obiettivi);
                    }
                    res.activate("search.ftl.html", request, response);
                }else{
                    //alcuni campi non sono validi
                    action_default(request, response);
                }
            }else{
                //alcuni campi non sono stati inviati
                response.sendRedirect("search");
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