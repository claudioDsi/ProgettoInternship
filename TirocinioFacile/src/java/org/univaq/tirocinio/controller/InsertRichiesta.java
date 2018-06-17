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
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import org.univaq.tirocinio.datamodel.*;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

public class InsertRichiesta extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid");
            Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(userid, tirocinio.getIdTirocinio());
            if(tirocinio.getStatus() || richiesta!=null){
                response.sendRedirect("show?tid="+tirocinio.getIdTirocinio());
            }else{
                Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(tirocinio.getIdAzienda());
                List<Tutore> listaTutori = a.getListaTutori();
                request.setAttribute("tirocinio", tirocinio);
                request.setAttribute("Session", s);
                request.setAttribute("listaTutori", listaTutori);
                res.activate("add_request.ftl.html", request, response);
            }
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_add(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException {
        try {
            HttpSession s = SecurityLayer.checkSession(request);
            int session_userid = (int)s.getAttribute("userid");
            int request_userid = SecurityLayer.checkNumeric(request.getParameter("userid"));
            int tirocinio_id = tirocinio.getIdTirocinio();
            Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(request_userid, tirocinio.getIdTirocinio());
            if(tirocinio.getStatus() || richiesta!=null || session_userid!=request_userid){
                response.sendRedirect("show?tid="+tirocinio_id);
            }else{
                Richiesta new_richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).creaRichiesta();
                new_richiesta.setCfu(SecurityLayer.addSlashes(request.getParameter("cfu")));
                new_richiesta.setIdStudente(request_userid);
                new_richiesta.setIdTirocinio(tirocinio_id);
                new_richiesta.setStatus("pending");
                int tutore_id = SecurityLayer.checkNumeric(request.getParameter("tutore"));
                Tutore tutore = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTutore(tutore_id);
                new_richiesta.setCodTutore(tutore.getIdTutore());
                new_richiesta.setNomeTutor(SecurityLayer.addSlashes(tutore.getNome()));
                new_richiesta.setCognomeTutor(SecurityLayer.addSlashes(tutore.getCognome()));
                new_richiesta.setEmailTutor(SecurityLayer.addSlashes(tutore.getEmailTutore()));
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeRichiesta(new_richiesta);
                response.sendRedirect("show?tid="+tirocinio_id);
                //action_activate(request, response, new_richiesta.getIdRichiesta());
            }    
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int richiesta_key) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            HttpSession s = SecurityLayer.checkSession(request);
            Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoRichiesta(richiesta_key);
            request.setAttribute("nuova_richiesta", richiesta);
            request.setAttribute("Session", s);
            res.activate("result.ftl.html", request, response);
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
            if(utype.equals("stud")){
                //se sei uno studente puoi fare richiesta per il tirocinio
                if(request.getParameter("add")!=null){
                    action_add(request, response, tirocinio);
                }else{
                    request.setAttribute("page_title", "Aggiungi richiesta");
                    action_default(request, response, tirocinio);
                }              
            }else{
                //altrimenti (azienda, admin, o anonimo) non puoi
                response.sendRedirect("show?tid="+tirocinio_id);
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