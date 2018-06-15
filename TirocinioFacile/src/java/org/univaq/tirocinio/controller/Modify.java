/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.univaq.tirocinio.datamodel.*;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class Modify extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, String utype) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            
            if(utype.equals("stud") || utype.equals("admin")){
                res.activate("modify_user.ftl.html", request, response);
            }else{
                res.activate("modify_company.ftl.html", request, response);
            }
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_modify(HttpServletRequest request, HttpServletResponse response,int userid, String usertype) throws IOException, ServletException, TemplateManagerException, ParseException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            if(usertype.equals("stud") || usertype.equals("admin")){
                Utente u = ((InternShipDataLayer)request.getAttribute("datalayer")).creaStudente();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date_in_string = request.getParameter("datanasc");
                Date date = sdf.parse(date_in_string);
                u.setNome(request.getParameter("nome"));
                u.setCognome(request.getParameter("cognome"));
                u.setDataNasc(date);
                u.setLuogoNasc(request.getParameter("luogonasc"));
                u.setResidenza(request.getParameter("residenza"));
                u.setCodFisc(request.getParameter("codfisc"));
                u.setTelefono(request.getParameter("telefono"));
                u.setCdl(request.getParameter("cdl"));
                u.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
                u.setLaurea(request.getParameter("laurea"));
                u.setDottorato(request.getParameter("dottorato"));
                u.setSpecializzazione(request.getParameter("specializzazione"));
                u.setEmailUtente(request.getParameter("email"));
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
            if(usertype.equals("stud") || usertype.equals("admin")){
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
                    //se l'azione di modifica è stata inviata, la eseguo
                    action_modify(request, response, userid, usertype);
                }else{
                    int uid = SecurityLayer.checkNumeric(request.getParameter("uid"));
                    String utype = request.getParameter("utype");
                    if(uid == userid){
                        //se l'utente che si vuole modificare è lo stesso di quello in sessione, posso modificare
                        if(utype.equals("stud") || utype.equals("admin")){
                            request.setAttribute("page_title", "Modifica Utente");
                            request.setAttribute("Session", s);
                            action_default(request, response, utype);
                        }else if(utype.equals("comp")){
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
        } catch (ParseException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }  
    }
    
}



