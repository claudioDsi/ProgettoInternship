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
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.datamodel.Tirocinio;

/**
 *
 * @author vince
 */
public class SetRichiesta extends InternshipDBController {
    
    private void action_modify_request(HttpServletRequest request, HttpServletResponse response, int value, Richiesta richiesta, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            if(value==1){
                //accetto la richiesta, cambio lo stato del tirocinio e rifiuto tutte le altre
                richiesta.setStatus("accepted");
                tirocinio.setStatus(true);
                ((InternShipDataLayer)request.getAttribute("datalayer")).modifyRequestStatus(richiesta);
                ((InternShipDataLayer)request.getAttribute("datalayer")).modifyTirocinioStatus(tirocinio);
                ((InternShipDataLayer)request.getAttribute("datalayer")).rejectAllRequests(richiesta, tirocinio);
            }else if(value==0){
                //rifiuto la richiesta
                richiesta.setStatus("rejected");
                ((InternShipDataLayer)request.getAttribute("datalayer")).modifyRequestStatus(richiesta);
            }
            response.sendRedirect("panel?tid="+tirocinio.getIdTirocinio());
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
                int rid = SecurityLayer.checkNumeric(request.getParameter("rid"));
                int value = SecurityLayer.checkNumeric(request.getParameter("val"));
                Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoRichiesta(rid);
                Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(richiesta.getIdTirocinio());
                HttpSession s = SecurityLayer.checkSession(request);
                int userid = (int)s.getAttribute("userid");
                String utype = (String)s.getAttribute("type");
                if(utype.equals("comp") && userid==tirocinio.getIdAzienda() && tirocinio.getStatus()==false && richiesta.getStatus().equals("pending")){
                    //sono l'azienda che ha inserito il tirocinio e sia il tirocinio che la richiesta ancora sono in attesa
                    action_modify_request(request, response, value, richiesta, tirocinio);
                }else{
                    response.sendRedirect("show?tid=" + tirocinio.getIdTirocinio());
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