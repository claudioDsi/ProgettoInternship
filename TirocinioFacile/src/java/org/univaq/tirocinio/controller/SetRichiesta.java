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
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.datamodel.Tutore;

/**
 *
 * @author vince
 */
public class SetRichiesta extends InternshipDBController {
    
    private void action_modify_request(HttpServletRequest request, HttpServletResponse response, int value, Richiesta richiesta, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException {
        try {
            if(value==1){
                HttpSession s = SecurityLayer.checkSession(request);
                int userid = (int)s.getAttribute("userid");
                Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                int old_val_a = azienda.getNumeroTirocini();
                int new_val_a = old_val_a + 1;
                Tutore tutore = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTutore(richiesta.getCodTutore());
                int old_val_t = tutore.getNumTirocini();
                int new_val_t = old_val_t + 1;
                richiesta.setStatus("accepted");
                tirocinio.setStatus(true);
                //accetto la richiesta e modifico il suo status
                ((InternShipDataLayer)request.getAttribute("datalayer")).modifyRequestStatus(richiesta);
                //modifico status e assegno il tutore scelto al tirocinio
                ((InternShipDataLayer)request.getAttribute("datalayer")).modifyTirocinioStatus(tirocinio,tutore.getIdTutore());
                //rifiuto le altre richieste
                ((InternShipDataLayer)request.getAttribute("datalayer")).rejectAllRequests(richiesta, tirocinio);
                //aggiorno il numero di tirocini dell'azienda
                ((InternShipDataLayer)request.getAttribute("datalayer")).updateNumTiroAzienda(new_val_a, azienda);
                //aggiorno il numero di tirocini del tutore
                ((InternShipDataLayer)request.getAttribute("datalayer")).updateNumTiroTutore(new_val_t, tutore);
                response.sendRedirect("setdates?tid="+tirocinio.getIdTirocinio());
            }else if(value==0){
                //rifiuto la richiesta
                richiesta.setStatus("rejected");
                ((InternShipDataLayer)request.getAttribute("datalayer")).modifyRequestStatus(richiesta);
                response.sendRedirect("panel?tid="+tirocinio.getIdTirocinio());
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try{
            if(request.getParameter("rid")!=null && request.getParameter("val")!=null){
                HttpSession s = SecurityLayer.checkSession(request);
                if(s!=null){
                    int userid = (int)s.getAttribute("userid");
                    String utype = (String)s.getAttribute("type");
                    int rid = SecurityLayer.checkNumeric(request.getParameter("rid"));
                    int value = SecurityLayer.checkNumeric(request.getParameter("val"));
                    Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoRichiesta(rid);
                    if(richiesta!=null){
                        Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(richiesta.getIdTirocinio());
                        if(utype.equals("comp") && userid==tirocinio.getIdAzienda() && tirocinio.getStatus()==false && richiesta.getStatus().equals("pending")){
                            //sono l'azienda che ha inserito il tirocinio e sia il tirocinio che la richiesta ancora sono in attesa
                            action_modify_request(request, response, value, richiesta, tirocinio);
                        }else{
                            response.sendRedirect("show?tid=" + tirocinio.getIdTirocinio());
                        }
                    }else{
                        //la richiesta specificata non esiste
                        response.sendRedirect("home");
                    }
                }else{
                    //sei anonimo
                    response.sendRedirect("home");
                }
            }else{
                //i campi non sono stati definiti
            }
        }catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
}