/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import org.univaq.tirocinio.datamodel.*;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;

public class ShowProgetto extends InternshipDBController {
    
        private void action_show(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio, Azienda azienda, Utente utente) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            HttpSession s = SecurityLayer.checkSession(request);
            request.setAttribute("Session", s);
            request.setAttribute("tirocinio", tirocinio);
            request.setAttribute("azienda", azienda);
            request.setAttribute("utente", utente);
            res.activate("show_progetto.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
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
            request.setAttribute("page_title", "Scarica o Stampa Progetto");
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                int userid = (int)s.getAttribute("userid"); //id utente in sessione
                String utype = (String)s.getAttribute("type");
                if(request.getParameter("tid")!=null){
                    int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tid"));
                    Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(id_tirocinio);
                    if(tirocinio!=null){
                        if(utype.equals("comp")){   
                            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                            //vedo se è possibile generare il progetto e che l'azienda in sessione sia la proprietaria del tirocinio
                            if(tirocinio.getStatusProgetto()&& tirocinio.getIdAzienda()==azienda.getIdAzienda()){
                                List<Richiesta> lista_richieste = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaRichiesteTirocinio(id_tirocinio);
                                Utente utente = null;
                                for (int i = 0; i < lista_richieste.size(); i++) {
                                    if(lista_richieste.get(i).getStatus().equals("accepted")){
                                        utente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(lista_richieste.get(i).getIdStudente());
                                    }
                                }
                                action_show(request,response, tirocinio, azienda, utente);
                            }else{
                                response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                            }
                        }else if(utype.equals("stud")){
                            Utente utente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(userid);
                            Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(utente.getIdUtente(), id_tirocinio);
                            if(richiesta!=null){
                                //vedo se è possibile generare il progetto e che l'utente in sessione sia il tirocinante assegnato
                                if(tirocinio.getStatusProgetto()&& richiesta.getStatus().equals("accepted")){
                                    Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(tirocinio.getIdAzienda());
                                    action_show(request,response, tirocinio, azienda, utente);
                                }else{
                                    response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                                }
                            }else{
                                //lo studente non ha fatto richiesta per questo tirocinio
                                response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                            }
                        }else{
                            //non sei un'azienda o uno studente
                            response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                        }
                    }else{
                        //il tirocinio specificato non esiste
                        response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                    }
                }else{
                    //non hai scelto un progetto da visualizzare
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
        }catch (DataLayerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);        
        }  
    }
    
}