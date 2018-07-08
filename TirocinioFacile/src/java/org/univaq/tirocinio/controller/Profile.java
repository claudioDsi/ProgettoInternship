/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.Utente;
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
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

/**
 *
 * @author vince
 */
public class Profile extends InternshipDBController {
    
    private void action_company(HttpServletRequest request, HttpServletResponse response, int uid) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(uid);
            if(a!=null){
                request.setAttribute("azienda", a);
                HttpSession s = SecurityLayer.checkSession(request);
                if(s!=null){
                    int session_userid = (int) s.getAttribute("userid");
                    if(uid==session_userid){
                        //se sono l'azienda stessa posso vedermi in qualsiasi condizione
                        request.setAttribute("modifica", true);
                        request.setAttribute("Session", s);
                        if(a.getStatus()){
                            request.setAttribute("inserisci", true);
                        }else{
                            request.setAttribute("inserisci", false);
                        }
                        if(a.getStatusConvenzione()){
                            request.setAttribute("convenzione", true);
                        }else{
                            request.setAttribute("convenzione", false);
                        }
                        if(a.getIdConvenzione()!=0){
                            request.setAttribute("scarica", true);
                        }else{
                            request.setAttribute("scarica", false);
                        }
                        res.activate("profilo.ftl.html", request, response);
                    }else if(a.getStatus()){
                        //se sono un qualsiasi altro utente posso vedere l'azienda solo se è stata abilitata dall'admin
                        request.setAttribute("modifica", false);
                        request.setAttribute("inserisci", false);
                        request.setAttribute("Session", s);
                        res.activate("profilo.ftl.html", request, response);
                    }else{
                        response.sendRedirect("home");
                    }
                }else{
                    if(a.getStatus()){
                        //se sono un utente anonimo vedo l'azienda solo se abilitata
                        request.setAttribute("modifica", false);
                        request.setAttribute("inserisci", false);
                        res.activate("profilo.ftl.html", request, response);
                    }else {
                        response.sendRedirect("home");
                    }
                }
            }else{
                //l'azienda non esiste
                response.sendRedirect("home");
            }
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_student(HttpServletRequest request, HttpServletResponse response, int uid) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            Utente u = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(uid);
            if(u!=null){
                request.setAttribute("utente", u);
                HttpSession s = SecurityLayer.checkSession(request);
                if(s!=null){
                    int session_userid = (int) s.getAttribute("userid");
                    if(uid==session_userid){
                        request.setAttribute("modifica", true);
                    }else{
                        request.setAttribute("modifica", false);
                    }
                }
                request.setAttribute("Session", s);
                res.activate("profilo.ftl.html", request, response);
            }else{
                //l'utente specificato non esiste
                response.sendRedirect("home");
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
            if(request.getParameter("uid")!=null && request.getParameter("utype")!=null){
                int uid = SecurityLayer.checkNumeric(request.getParameter("uid"));
                String utype = request.getParameter("utype");
                if(utype.equals("comp")){
                    request.setAttribute("page_title", "Profilo Azienda");
                    action_company(request, response, uid);
                }else if(utype.equals("stud") || utype.equals("admin")){
                    request.setAttribute("page_title", "Profilo Utente");
                    action_student(request, response, uid);
                }else{
                    //tipo utente errato
                    response.sendRedirect("home");
                }
            }else{
                //non sono stati specificati tipo ed id utente
                response.sendRedirect("home");
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