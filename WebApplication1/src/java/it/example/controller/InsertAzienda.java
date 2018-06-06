/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.example.data.impl.*;
import it.example.datamodel.*;
import it.example.datamodel.InternShipDataLayer;
import it.example.framework.data.DataLayerException;
import it.example.framework.result.FailureResult;
import it.example.framework.result.TemplateManagerException;
import it.example.framework.result.TemplateResult;

public class InsertAzienda extends InternshipDBController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("register_company.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
                Azienda a;
                a = ((InternShipDataLayer)request.getAttribute("datalayer")).creaAzienda();
                a.setUsername(request.getParameter("username"));
                a.setPassword(request.getParameter("password"));
                a.setPrivilegi(2);
                a.setNomeAzienda(request.getParameter("nome"));
                a.setRagioneSociale(request.getParameter("ragionesociale"));
                a.setIndirizzo(request.getParameter("indirizzo"));
                a.setPartitaIva(request.getParameter("partitaiva"));
                a.setStatus(false);
                a.setCodiceFisc(request.getParameter("codicefisc"));
                a.setNomeRappr(request.getParameter("nomerappr"));
                a.setCognomeRappr(request.getParameter("cognomerappr"));
                a.setNomeResp(request.getParameter("nomeresp"));
                a.setCognomeResp(request.getParameter("cognomeresp"));
                a.setTelefonoResp(request.getParameter("telefonoresp"));
                a.setEmailResp(request.getParameter("emailresp"));
                a.setForo(request.getParameter("foro"));
                a.setValutazione((float)0);
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeAzienda(a);
                action_activate(request, response, a.getIdAzienda());
            }catch (DataLayerException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
            }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int azienda_key) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(azienda_key);
            request.setAttribute("azienda", azienda);
            res.activate("result.ftl.html", request, response);
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
            request.setAttribute("page_title", "Inserisci Azienda");
            try{
                if(request.getParameter("add")!=null){
                    // se il parametro add ha un valore assegnato allora inserisco l'azienda registrata
                    action_write(request, response);
                }else{
                    // altrimenti mostro la pagina per registrarsi
                    action_default(request, response);
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