/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

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
                
                /*
                List<String> arr=new ArrayList<String>();     
                
                
                if(!SecurityLayer.addSlashes(request.getParameter("nome")).equals("")){
                    arr.add("Nome");
                }                
                  if(!SecurityLayer.addSlashes(request.getParameter("cognome")).equals("")){
                    arr.add("Cognome");                }
                  if(!SecurityLayer.addSlashes(request.getParameter("luogonasc")).equals("")){
                    arr.add("LuogoNasc");
                }
                  if(!SecurityLayer.addSlashes(request.getParameter("datanasc")).equals("")){
                    arr.add("DataNasc");
                }
                  if(!SecurityLayer.addSlashes(request.getParameter("codfisc")).equals("")){
                    arr.add("CodiceFisc");
                }
                  if(!SecurityLayer.addSlashes(request.getParameter("residenza")).equals("")){
                    arr.add("Residenza");
                }
                  if(!SecurityLayer.addSlashes(request.getParameter("telefono")).equals("")){
                    arr.add("Telefono");
                }                  
                  if(!SecurityLayer.addSlashes(request.getParameter("sesso")).equals("")){
                    arr.add("Sesso");
                }                
                  if(!SecurityLayer.addSlashes(request.getParameter("email")).equals("")){
                    arr.add("EmailUtente");
                }
                   if(!SecurityLayer.addSlashes(request.getParameter("handicap")).equals("")){
                    arr.add("Handicap");
                }                 
                  if(!SecurityLayer.addSlashes(request.getParameter("laurea")).equals("")){
                    arr.add("CorsoLaurea");
                }
                  if(!SecurityLayer.addSlashes(request.getParameter("dottorato")).equals("")){
                    arr.add("Dottorato");
                }
                    if(!SecurityLayer.addSlashes(request.getParameter("specializzazione")).equals("")){
                    arr.add("ScuolaSpec");
                }                 
                if(!SecurityLayer.addSlashes(request.getParameter("cdl")).equals("")){
                    arr.add("Laurea");
                }               
                String[] campi =new String[arr.size()];
                arr.toArray(campi);               
                for(int i=0; i<campi.length;i++){
                    System.out.println("campo "+campi[i]);
                }
                System.out.println(request.getParameter("handicap"));
                */
                u.setNome(SecurityLayer.addSlashes(request.getParameter("nome")));                
                u.setCognome(SecurityLayer.addSlashes(request.getParameter("cognome")));
                u.setDataNasc(date);                 
                u.setLuogoNasc(SecurityLayer.addSlashes(request.getParameter("luogonasc")));                               
                u.setResidenza(SecurityLayer.addSlashes(request.getParameter("residenza")));
                u.setCodFisc(SecurityLayer.addSlashes(request.getParameter("codfisc")));
                u.setTelefono(SecurityLayer.addSlashes(request.getParameter("telefono")));
                
                u.setSesso(SecurityLayer.addSlashes(request.getParameter("sesso")));
                u.setCdl(SecurityLayer.addSlashes(request.getParameter("cdl")));
                u.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
                u.setLaurea(SecurityLayer.addSlashes(request.getParameter("laurea")));
                u.setDottorato(SecurityLayer.addSlashes(request.getParameter("dottorato")));
                u.setSpecializzazione(SecurityLayer.addSlashes(request.getParameter("specializzazione")));
                u.setEmailUtente(SecurityLayer.addSlashes(request.getParameter("email")));
                u.setIdUtente(userid);
                System.out.println(userid);
               ((InternShipDataLayer)request.getAttribute("datalayer")).storeStudente(u);
               
               //query dinamica da verificare
               
               //String up=((InternShipDataLayer)request.getAttribute("datalayer")).creaQueryUpdate(campi, "Utente", "IdUtente");
               
               
               //System.out.println(up);
               
              //((InternShipDataLayer)request.getAttribute("datalayer")).modificaUtente(up,u);
               
               action_activate(request, response, userid, usertype);
            }else if(usertype.equals("comp")){
                Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).creaAzienda();
                a.setNomeAzienda(SecurityLayer.addSlashes(request.getParameter("nome")));
                a.setRagioneSociale(SecurityLayer.addSlashes(request.getParameter("ragionesociale")));
                a.setIndirizzo(SecurityLayer.addSlashes(request.getParameter("indirizzo")));
                a.setPartitaIva(SecurityLayer.addSlashes(request.getParameter("partitaiva")));
                a.setCodiceFisc(SecurityLayer.addSlashes(request.getParameter("codicefisc")));
                a.setNomeRappr(SecurityLayer.addSlashes(request.getParameter("nomerappr")));
                a.setCognomeRappr(SecurityLayer.addSlashes(request.getParameter("cognomerappr")));
                a.setNomeResp(SecurityLayer.addSlashes(request.getParameter("nomeresp")));
                a.setCognomeResp(SecurityLayer.addSlashes(request.getParameter("cognomeresp")));
                a.setTelefonoResp(SecurityLayer.addSlashes(request.getParameter("telefonoresp")));
                a.setEmailResp(SecurityLayer.addSlashes(request.getParameter("emailresp")));
                a.setForo(SecurityLayer.addSlashes(request.getParameter("foro")));
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
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                res.activate("modify_user",request,response);
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



