/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.Utente;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
public class Login extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            if(request.getParameter("tid")!=null){
                String tirocinio_id = request.getParameter("tid");
                request.setAttribute("tid", tirocinio_id);
            }else{
                request.setAttribute("tid", "");
            }
            res.activate("login.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_login_user(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, NoSuchAlgorithmException {
        try {
            String username = SecurityLayer.addSlashes(request.getParameter("username"));
            String password = SecurityLayer.addSlashes(request.getParameter("password"));
            String hashedPassword = SecurityLayer.securePassword(password);
            Utente u = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtenteByLogin(username, hashedPassword);
            if(u!=null){
                if(username.equals(u.getUsername()) && hashedPassword.equals(u.getPassword()) && u.getPrivilegi()==0){
                    HttpSession s = SecurityLayer.createSession(request, u.getIdUtente(), u.getPrivilegi(), "admin");
                        request.setAttribute("Session", s);
                }else if(username.equals(u.getUsername()) && hashedPassword.equals(u.getPassword()) && u.getPrivilegi()==1){
                    HttpSession s = SecurityLayer.createSession(request, u.getIdUtente(), u.getPrivilegi(), "stud");
                    request.setAttribute("Session", s);
                }
                //controllo se voglio essere reindirizzato ad un tirocinio
                if(request.getParameter("tid").equals("")){
                    //se non sto loggando dopo aver visitato un tirocinio
                    HttpSession s = SecurityLayer.checkSession(request);
                    if(s!=null){
                        if(s.getAttribute("type").equals("stud")){
                            response.sendRedirect("applications");
                        }else{
                            response.sendRedirect("managecompany");
                        }
                    }
                }else{
                    //altrimenti vengo reindirizzato al tirocinio a cui iscrivermi
                    String id_tirocinio = request.getParameter("tid");
                    response.sendRedirect("show?tid="+id_tirocinio);
                }   
            }else{
                //se ho sbagliato ad inserire i miei dati
                request.setAttribute("usererror", "Username o Password Utente sono errati!");
                //res.activate("login.ftl.html", request, response);
                //action_error(request, response);
                action_default(request, response);
            }
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_login_company(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, NoSuchAlgorithmException {
        try {
            //TemplateResult res = new TemplateResult(getServletContext());
            String username = SecurityLayer.addSlashes(request.getParameter("username"));
            String password = SecurityLayer.addSlashes(request.getParameter("password"));
            String hashedPassword = SecurityLayer.securePassword(password);
            Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAziendaByLogin(username, hashedPassword);
            if(a!=null){
                if(username.equals(a.getUsername()) && hashedPassword.equals(a.getPassword())){
                    HttpSession s = SecurityLayer.createSession(request, a.getIdAzienda(), a.getPrivilegi(), "comp");
                    request.setAttribute("Session", s);
                    response.sendRedirect("panel");
                }else{
                    request.setAttribute("companyerror", "Username o Password Azienda sono errati!");
                    //res.activate("login.ftl.html", request, response);
                    action_default(request, response);
                }
            }else{
                //se ho sbagliato ad inserire i dati
                request.setAttribute("companyerror", "Username o Password Azienda sono errati!");
                //res.activate("login.ftl.html", request, response);
                action_default(request, response);
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
        request.setAttribute("page_title", "Login Utente");
        try{
            HttpSession s = SecurityLayer.checkSession(request);
            if(s==null){
                if(request.getParameter("loginuser")!=null){
                    // se il parametro login ha un valore assegnato allora inserisco faccio loggare l'utente
                    action_login_user(request, response);
                }else if(request.getParameter("logincomp")!=null){
                    // controllo se vuole loggarsi un'azienda
                    action_login_company(request, response);
                }else{
                    // altrimenti mostro la pagina per loggarsi
                    action_default(request, response);
                }
            }else{
                //se sono già loggato non si può visualizzare la pagina di login
                response.sendRedirect("home");
            }
        }catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (NoSuchAlgorithmException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } 
    }
    
}
