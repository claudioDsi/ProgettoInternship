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
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.Tirocinio;

/**
 *
 * @author vince
 */
public class SetDates extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException, ParseException {
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession s = SecurityLayer.checkSession(request);
        request.setAttribute("Session", s);
        request.setAttribute("tirocinio", tirocinio);
        res.activate("set_dates.ftl.html", request, response);
    }
    
    private void action_modify_dates(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException, ParseException {
        try {
            //tutti i campi devono essere riempiti
            boolean no_update = false;
            //controllo campo dataInizio
            if(request.getParameter("dataInizio")==null || request.getParameter("dataInizio").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            //controllo campo dataFine
            if(request.getParameter("dataFine")==null || request.getParameter("dataFine").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            if(!no_update){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date_in_string_start = request.getParameter("dataInizio");
                Date date_start = sdf.parse(date_in_string_start);
                String date_in_string_end = request.getParameter("dataFine");
                Date date_end = sdf.parse(date_in_string_end);
                tirocinio.setDataInizio(date_start);
                tirocinio.setDataFine(date_end);
                ((InternShipDataLayer)request.getAttribute("datalayer")).updateDateTirocinio(date_start, date_end, tirocinio);
                ((InternShipDataLayer)request.getAttribute("datalayer")).activateProgetto(tirocinio);
                response.sendRedirect("panel");
            }else{
                //è stato generato un messaggio di errore
                action_default(request, response, tirocinio);
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
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                int userid = (int)s.getAttribute("userid");
                String utype = (String)s.getAttribute("type");
                if(request.getParameter("tid")!=null){
                    int tid = SecurityLayer.checkNumeric(request.getParameter("tid"));
                    Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(tid);
                    if(tirocinio!=null){
                        if(utype.equals("comp") && userid==tirocinio.getIdAzienda() && tirocinio.getStatus()==true){
                            //sono l'azienda che ha inserito il tirocinio e il tirocinio è stato accettato
                            if(tirocinio.getDataFine()==null && tirocinio.getDataInizio()==null){
                                //le date non sono ancora state inserite
                                if(request.getParameter("add")!=null){
                                    action_modify_dates(request, response, tirocinio);
                                }else{
                                    action_default(request, response, tirocinio);
                                }
                            }else{
                                response.sendRedirect("show?tid=" + tirocinio.getIdTirocinio());  
                            }
                        }else{
                            response.sendRedirect("show?tid=" + tirocinio.getIdTirocinio());
                        }
                    }else{
                        //il tirocinio specificato non esiste
                        response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                    }
                }else{
                    //il tirocinio non è stato specificato
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
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch(ParseException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);        
        }
    }
    
}