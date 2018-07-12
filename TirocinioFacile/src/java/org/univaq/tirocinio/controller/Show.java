/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class Show extends InternshipDBController {
    
    private void action_anonymous(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
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
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            List<Richiesta> lista_richieste = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaRichiesteTirocinio(tirocinio.getIdTirocinio());
            int userid =(int)s.getAttribute("userid");
            //controllo che il tirocinio sia già stato assegnato
            if(tirocinio.getStatus()){
                //il tirocinio è stato accettato quindi controllo se lui è l'utente a cui è stato assegnato
                Richiesta richiesta_accettata = null;
                request.setAttribute("status", true);
                for(int i = 0; i < lista_richieste.size(); i++){
                    if(lista_richieste.get(i).getStatus().equals("accepted")){
                        richiesta_accettata = lista_richieste.get(i);
                    }
                }
                if(userid==richiesta_accettata.getIdStudente()){
                    //lo studente in sessione è quello a cui è stato assegnato il tirocinio, quindi può vederne i dettagli
                    Date dataFine = tirocinio.getDataFine();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateNow = new Date();
                    if(dateNow.compareTo(dataFine)>0){
                        request.setAttribute("valuta", true);
                    }else{
                        request.setAttribute("valuta", false);
                    }
                    if(tirocinio.getStatusProgetto()){
                        request.setAttribute("progetto", true);
                    }else{
                        request.setAttribute("progetto", false);
                    }
                    if(tirocinio.getIdProgetto()==0){
                        request.setAttribute("upload", true);
                    }else{
                        request.setAttribute("upload", false);
                    }
                    if(tirocinio.getStatusResoconto()){
                        request.setAttribute("resoconto", true);
                    }else{
                        request.setAttribute("resoconto", false);
                    }
                    request.setAttribute("azione", "accepted");
                    request.setAttribute("richiesta", richiesta_accettata);
                }else{
                    //lo studente in sessione non è quello a cui è stato assegnato il tirocinio
                    request.setAttribute("azione", "anonymous");
                }
            }else{
                //il tirocinio non è stato ancora assegnato
                Richiesta richiesta_studente = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(userid, tirocinio.getIdTirocinio());
                if(richiesta_studente!=null){
                    //l'utente ha già una richiesta per il tirocinio
                    if(richiesta_studente.getStatus().equals("pending")){
                        request.setAttribute("azione", "pending");
                    }else if(richiesta_studente.getStatus().equals("rejected")){
                        request.setAttribute("azione", "reject");
                    }                  
                    request.setAttribute("richiesta", richiesta_studente);
                }else{
                    //l'utente non ha mai fatto richiesta per il tirocinio quindi può applicarsi
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
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
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
                    if(tirocinio.getStatusProgetto()){
                        request.setAttribute("progetto", true);
                    }else{
                        request.setAttribute("progetto", false);
                    }
                    if(tirocinio.getIdProgetto()==0){
                        request.setAttribute("upload", true);
                    }else{
                        request.setAttribute("upload", false);
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
            if(request.getParameter("tid")!=null){
                int tirocinio_id = SecurityLayer.checkNumeric(request.getParameter("tid"));
                Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(tirocinio_id);
                
                if(tirocinio!=null){
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
                }else{
                    //il tirocinio richiesto non esiste
                    response.sendRedirect("home");
                }
            }else{
                //id tirocinio mancante
                response.sendRedirect("home");
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