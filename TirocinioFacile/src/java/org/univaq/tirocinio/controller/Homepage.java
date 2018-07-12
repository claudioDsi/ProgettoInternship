/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;

/**
 *
 * @author claudio
 */
public class Homepage extends InternshipDBController {
    
    private void action_show_home_anonimo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        List<Tirocinio> list = new ArrayList<Tirocinio>();
        List<Azienda> aziendeConv=new ArrayList<Azienda>();
        try{
            request.setAttribute("page_title", "Homepage");
            TemplateResult res = new TemplateResult(getServletContext());
            list = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaTirocini(); 
            aziendeConv=((InternShipDataLayer)request.getAttribute("datalayer")).getAziendeConvenzionate();
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("tirocini", list);
            request.setAttribute("convenzioni", aziendeConv);
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                request.setAttribute("Session", s);
            }
            res.activate("home.ftl.html", request, response);   
        }catch(DataLayerException dte){
            request.setAttribute("message", "Data access exception: " + dte.getMessage());
            action_error(request, response);
        }             
    }
    
    private void action_show_home_admin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try{
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Gestione delle abilitazioni delle aziende");
            HttpSession s = SecurityLayer.checkSession(request);
            List<Azienda> lista_aziende = ((InternShipDataLayer)request.getAttribute("datalayer")).showListaAziende();
            request.setAttribute("lista_aziende", lista_aziende);
            request.setAttribute("Session", s);
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            res.activate("home.ftl.html", request, response);
        }catch(DataLayerException dte){
            request.setAttribute("message", "Data access exception: " + dte.getMessage());
            action_error(request, response);
        }  
    }
    
    private void action_show_home_student(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try{
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Riepilogo Tirocini e Candidature");
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid");
            //ottengo dal db le richieste e i tirocini relativi allo studente
            List<Richiesta> lista_richieste = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaRichiesteStudente(userid);
            List<Tirocinio> lista_tirocini_approvati = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaTirociniApprovatiByStudente(userid);
            if(!lista_richieste.isEmpty()){
                request.setAttribute("lista_richieste", lista_richieste);
            }
            if(!lista_tirocini_approvati.isEmpty()){
                request.setAttribute("lista_tirocini_approvati", lista_tirocini_approvati);
            }
            request.setAttribute("Session", s);
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            res.activate("home.ftl.html", request, response);
        }catch(DataLayerException dte){
            request.setAttribute("message", "Data access exception: " + dte.getMessage());
            action_error(request, response);
        }  
    }
    
    private void action_show_home_company(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try{
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Gestione Tirocini Inseriti");
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid");
            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
            List<Tirocinio> lista_tirocini = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaTirociniByAzienda(azienda);
            if(!lista_tirocini.isEmpty()){
                request.setAttribute("lista_tirocini", lista_tirocini);
                Map resoconto = new HashMap();
                for (int i = 0; i < lista_tirocini.size(); i++) {	
                    Date dataFine = lista_tirocini.get(i).getDataFine();
                    if(dataFine!=null){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateNow = new Date();
                        if(dateNow.compareTo(dataFine)>0){
                            resoconto.put(lista_tirocini.get(i).getIdTirocinio(), true);
                        }else{
                            resoconto.put(lista_tirocini.get(i).getIdTirocinio(), false);
                        }
                    }
                }
                request.setAttribute("resoconto", resoconto);
            }      
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("Session", s);
            res.activate("home.ftl.html", request, response);
        }catch(DataLayerException dte){
            request.setAttribute("message", "Data access exception: " + dte.getMessage());
            action_error(request, response);
        }  
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response){
        if (request.getAttribute("exception")!=null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException{
        try{
            boolean checkRes = SecurityLayer.checkIndirizzo("Via braccio d'arpino, snc");
            System.out.println(checkRes);
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                String type = (String)s.getAttribute("type");
                if(type.equals("stud")){
                    //sei studente
                    action_show_home_student(request, response);
                }else if(type.equals("comp")){
                    //sei un'azienda
                    action_show_home_company(request, response);
                }else if(type.equals("admin")){
                    //sei admin
                    action_show_home_admin(request, response);
                }
            }else{
                //sei anonimo
                action_show_home_anonimo(request, response);
            }
        }catch(IOException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }  
    }

}
