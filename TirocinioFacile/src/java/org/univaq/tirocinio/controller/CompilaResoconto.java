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
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import org.univaq.tirocinio.datamodel.*;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;
import org.univaq.tirocinio.framework.result.TemplateResult;

/**
 *
 * @author vince
 */
public class CompilaResoconto extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                request.setAttribute("Session", s);
            }
            request.setAttribute("tid", tirocinio.getIdTirocinio());
            res.activate("compila_resoconto.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_compila(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException {
        try {
            tirocinio.setDescrizione(SecurityLayer.addSlashes(request.getParameter("descrizione")));
            tirocinio.setRisultato(SecurityLayer.addSlashes(request.getParameter("risultato")));
            tirocinio.setStatusResoconto(true);
            ((InternShipDataLayer)request.getAttribute("datalayer")).updateResocontoTirocinio(tirocinio);
            response.sendRedirect("panel");
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
            if(s!=null){
                int userid = (int)s.getAttribute("userid"); //id utente in sessione
                String utype = (String)s.getAttribute("type");
                if(utype.equals("comp")){
                    //sei un'azienda
                    Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                    if(request.getParameter("tid")!=null){
                        int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tid"));
                        Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(id_tirocinio);
                        if(tirocinio!=null){
                            if(tirocinio.getIdAzienda()==azienda.getIdAzienda()){
                                //sei l'azienda che ha inserito il tirocinio
                                if(request.getParameter("add")!=null){
                                    //hai inviato la form
                                    action_compila(request, response, tirocinio);
                                }else{
                                    //ti mostro la form da compilare
                                    action_default(request, response, tirocinio);
                                }
                            }else{
                                //non sei l'azienda che ha inserito il tirocinio
                                response.sendRedirect("panel");
                            }
                        }else{
                            //il tirocinio non esiste
                            response.sendRedirect("panel");
                        }
                    }else{
                        //non è stato definito un valore per il tirocinio
                        response.sendRedirect("panel");
                    }
                }else{
                    //sei utente o admin
                    response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                }
            }else{
                //sei anonimo
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