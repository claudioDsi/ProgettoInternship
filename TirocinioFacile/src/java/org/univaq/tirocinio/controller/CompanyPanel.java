/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.datamodel.Utente;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class CompanyPanel extends InternshipDBController {
    
    private void action_show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Gestione Tirocini Inseriti");
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid");
            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
            List<Tirocinio> lista_tirocini = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaTirociniByAzienda(azienda);
            if(!lista_tirocini.isEmpty()){
                request.setAttribute("lista_tirocini", lista_tirocini);
                Map resoconto = new HashMap();
                for (int i = 0; i < lista_tirocini.size(); i++) {	
                    Date dataFine = lista_tirocini.get(i).getDataFine();
                    if(dataFine!=null){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateNow = new Date();
                        if(dateNow.compareTo(dataFine)>0){
                            resoconto.put(lista_tirocini.get(i).getIdTirocinio(), true);
                        }else{
                            resoconto.put(lista_tirocini.get(i).getIdTirocinio(), false);
                        }
                    }
                }
                request.setAttribute("resoconto", resoconto);
            }      
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("Session", s);
            res.activate("company_panel.ftl.html", request, response);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_richieste(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException{
        try{
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid");
            int tirocinioid = SecurityLayer.checkNumeric(request.getParameter("tid"));
            Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(tirocinioid);
            if(tirocinio!=null){
                if(tirocinio.getIdAzienda()==userid){
                    //sei l'azienda che ha creato il tirocinio quindi puoi vederne i dettagli
                    List<Richiesta> lista_richieste = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaRichiesteTirocinio(tirocinioid);
                    if(!lista_richieste.isEmpty()){
                        for (int i = 0; i < lista_richieste.size(); i++) {
                            Utente studente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(lista_richieste.get(i).getIdStudente());
                            lista_richieste.get(i).setStudente(studente);
                        }
                        request.setAttribute("lista_richieste", lista_richieste);
                    }
                    request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                    request.setAttribute("Session", s);
                    res.activate("manage_request.ftl.html", request, response);
                }else{
                    //non puoi vedere i dettagli delle richieste
                    response.sendRedirect("panel");
                }
            }else{
                //il tirocinio non esiste
                response.sendRedirect("panel");
            }
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_delete(HttpServletRequest request, HttpServletResponse response, int del) throws IOException, ServletException, TemplateManagerException{
        try{
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid");
            Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(del);
            if(tirocinio!=null){
                if(tirocinio.getIdAzienda()==userid){                    
                    //sei l'azienda che ha creato il tirocinio quindi puoi eliminarlo
                    //vengono eliminate prima le richieste ad esso associate
                    ((InternShipDataLayer)request.getAttribute("datalayer")).eliminaRichiesteTirocinio(tirocinio);
                    ((InternShipDataLayer)request.getAttribute("datalayer")).eliminaTirocinio(del);
                    action_show(request, response);
                }else{
                    //non puoi cancellare il tirocinio
                    response.sendRedirect("panel");
                }
            }else{
                //il tirocinio non esiste
                response.sendRedirect("panel");
            }
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
                    if(type.equals("comp")){
                        //sei un'azienda
                        if(request.getParameter("tid")!=null){
                            //hai selezionato un tirocinio da gestire
                            request.setAttribute("page_title", "Gestione Richieste Tirocinio");                           
                            action_richieste(request, response);
                        }else if(request.getParameter("del")!=null){
                            //vuoi cancellare il tirocinio
                            int del = SecurityLayer.checkNumeric(request.getParameter("del"));
                            action_delete(request, response, del);
                        }else{
                            //sei un'azienda e mostro le tue candidature e i tuoi tirocini
                            
                            action_show(request, response);
                        }
                    }else if(type.equals("stud") || type.equals("admin")){
                        //sei uno studente o un admin
                        int userid = (int)s.getAttribute("userid");
                        response.sendRedirect("profile?uid="+ userid +"&utype="+type);
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