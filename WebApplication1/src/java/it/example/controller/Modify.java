/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.example.datamodel.*;
import it.example.datamodel.InternShipDataLayer;
import it.example.framework.data.DataLayerException;
import it.example.framework.result.FailureResult;
import it.example.framework.result.TemplateManagerException;
import it.example.framework.result.TemplateResult;
import it.example.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class Modify extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, String utype) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            
            if(utype.equals("stud")){
                res.activate("modify_user.ftl.html", request, response);
            }else{
                res.activate("modify_company.ftl.html", request, response);
            }
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_modify(HttpServletRequest request, HttpServletResponse response,int userid, String usertype) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            if(usertype.equals("stud")){
                Utente u = ((InternShipDataLayer)request.getAttribute("datalayer")).creaStudente();
                u.setNome(request.getParameter("nome"));
                u.setCognome(request.getParameter("cognome"));
                u.setDataNasc(request.getParameter("datanasc"));
                u.setLuogoNasc(request.getParameter("luogonasc"));
                u.setResidenza(request.getParameter("residenza"));
                u.setCodFisc(request.getParameter("codfisc"));
                u.setTelefono(request.getParameter("telefono"));
                u.setCdl(request.getParameter("cdl"));
                u.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
                u.setLaurea(request.getParameter("laurea"));
                u.setDottorato(request.getParameter("dottorato"));
                u.setSpecializzazione(request.getParameter("specializzazione"));
                u.setIdUtente(userid);
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeStudente(u);
                action_activate(request, response, userid, usertype);
            }else if(usertype.equals("comp")){
                Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).creaAzienda();
                a.setNomeAzienda(request.getParameter("nome"));
                a.setRagioneSociale(request.getParameter("ragionesociale"));
                a.setIndirizzo(request.getParameter("indirizzo"));
                a.setPartitaIva(request.getParameter("partitaiva"));
                a.setCodiceFisc(request.getParameter("codicefisc"));
                a.setNomeRappr(request.getParameter("nomerappr"));
                a.setCognomeRappr(request.getParameter("cognomerappr"));
                a.setNomeResp(request.getParameter("nomeresp"));
                a.setCognomeResp(request.getParameter("cognomeresp"));
                a.setTelefonoResp(request.getParameter("telefonoresp"));
                a.setEmailResp(request.getParameter("emailresp"));
                a.setForo(request.getParameter("foro"));
                a.setIdAzienda(userid);
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeAzienda(a);
                action_activate(request, response, userid, usertype);
            }
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int userid, String usertype) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            if(usertype.equals("stud")){
                Utente utente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(userid);
                request.setAttribute("utente", utente);
            }else if(usertype.equals("comp")){
                Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                request.setAttribute("azienda", azienda);
            }
            request.setAttribute("Session", s);
            response.sendRedirect("profile?uid="+userid+"&utype="+usertype);
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
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int) s.getAttribute("userid");
            String usertype = (String) s.getAttribute("type");
            try{
                if(request.getParameter("update")!=null){
                    action_modify(request, response, userid, usertype);
                }else{
                    int uid = SecurityLayer.checkNumeric(request.getParameter("uid"));
                    String utype = request.getParameter("utype");
                    if(uid == userid){
                        if(utype.equals("stud")){
                            request.setAttribute("page_title", "Modifica Utente");
                            request.setAttribute("Session", s);
                            action_default(request, response, utype);
                        }else{
                            request.setAttribute("page_title", "Modifica Azienda");
                            request.setAttribute("Session", s);
                            action_default(request, response, utype);
                        }
                    }else{
                        response.sendRedirect("profile?uid="+uid+"&utype="+utype);
                    }
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

