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
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import org.univaq.tirocinio.datamodel.*;
import org.univaq.tirocinio.framework.result.FailureResult;

public class Show extends InternshipDBController {
    
    private void action_anonymous(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                request.setAttribute("Session", s);
            }
            if(tirocinio.getStatus()){
                request.setAttribute("status", true);
            }
            request.setAttribute("tirocinio", tirocinio);
            res.activate("show_tirocinio.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_student(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio, HttpSession s) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            List<Richiesta> lista_richieste = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaRichiesteTirocinio(tirocinio.getIdTirocinio());
            int userid =(int)s.getAttribute("userid");
            //controllo che il tirocinio sia già stato assegnato
            if(tirocinio.getStatus()){
                Richiesta richiesta_accettata = null;
                request.setAttribute("status", true);
                for(int i = 0; i < lista_richieste.size(); i++){
                    if(lista_richieste.get(i).getStatus().equals("accepted")){
                        richiesta_accettata = lista_richieste.get(i);
                    }
                }
                if(userid==richiesta_accettata.getIdStudente()){
                    //lo studente in sessione è quello a cui è stato assegnato il tirocinio, quindi può vederne i dettagli
                    request.setAttribute("azione", "accepted");
                    request.setAttribute("richiesta", richiesta_accettata);
                }else{
                    //lo studente in sessione non è quello a cui è stato assegnato il tirocinio
                    request.setAttribute("azione", "anonymous");
                }
            }else{
                Richiesta richiesta_studente = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(userid, tirocinio.getIdTirocinio());
                if(richiesta_studente!=null){
                    request.setAttribute("azione", "pending");
                    request.setAttribute("richiesta", richiesta_studente);
                }else{
                    request.setAttribute("azione", "applicati");
                }
            }
            request.setAttribute("tirocinio", tirocinio);
            request.setAttribute("Session", s);
            res.activate("show_tirocinio.ftl.html", request, response);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_company(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio, HttpSession s) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            int userid = (int)s.getAttribute("userid");
            if(userid==tirocinio.getIdAzienda()){
                List<Richiesta> lista_richieste = new ArrayList();
                lista_richieste = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaRichiesteTirocinio(tirocinio.getIdTirocinio());
                if(tirocinio.getStatus()){
                    Richiesta richiesta_accettata = null;
                    request.setAttribute("status", true);
                    for(int i = 0; i < lista_richieste.size(); i++){
                        if(lista_richieste.get(i).getStatus().equals("accepted")){
                            richiesta_accettata = lista_richieste.get(i);
                        }
                    }
                    request.setAttribute("azione", "accepted");
                    request.setAttribute("richiesta", richiesta_accettata);
                }else{
                    request.setAttribute("azione", "pending");
                    if(!lista_richieste.isEmpty()){
                        request.setAttribute("richiesta", lista_richieste);
                    }
                }
            }else{
                if(tirocinio.getStatus()){
                    request.setAttribute("status", true);
                }
                request.setAttribute("azione", "anonymous");
            }          
            request.setAttribute("tirocinio", tirocinio);
            request.setAttribute("Session", s);
            res.activate("show_tirocinio.ftl.html", request, response);
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
            request.setAttribute("page_title", "Dettagli tirocinio");
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = 0;
            String utype = "";
            if(s!=null){
                userid = (int)s.getAttribute("userid");
                utype = (String)s.getAttribute("type");
                request.setAttribute("Session", s);
            }
            int tirocinio_id = SecurityLayer.checkNumeric(request.getParameter("tid"));
            Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(tirocinio_id);
            if(utype.equals("comp")){
                //caso azienda
                action_company(request, response, tirocinio, s);
            }else if(utype.equals("stud")){
                //caso studente
                action_student(request, response, tirocinio, s);
            }else{
                //caso utente anonimo e admin
                action_anonymous(request,response, tirocinio);
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