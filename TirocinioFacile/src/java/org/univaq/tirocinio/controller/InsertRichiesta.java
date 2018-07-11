/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
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

/**
 *
 * @author vince
 */
public class InsertRichiesta extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            HttpSession s = SecurityLayer.checkSession(request);
            int userid = (int)s.getAttribute("userid");
            Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(userid, tirocinio.getIdTirocinio());
            if(tirocinio.getStatus() || richiesta!=null){
                //il tirocinio è stato accettato già oppure hai già fatto richiesta per il tirocinio
                response.sendRedirect("show?tid="+tirocinio.getIdTirocinio());
            }else{
                Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(tirocinio.getIdAzienda());
                List<Tutore> listaTutori = a.getListaTutori();
                request.setAttribute("tirocinio", tirocinio);
                request.setAttribute("Session", s);
                request.setAttribute("listaTutori", listaTutori);
                res.activate("add_request.ftl.html", request, response);
            }
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_add(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio) throws IOException, ServletException, TemplateManagerException, MessagingException {
        try {
            //tutti i campi devono essere riempiti
            boolean no_update = false;
            //controllo campo cfu
            if(request.getParameter("cfu")==null || request.getParameter("cfu").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            //controllo campo tutore
            if(request.getParameter("tutore")==null || request.getParameter("tutore").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }
            if(!no_update){
                HttpSession s = SecurityLayer.checkSession(request);
                int session_userid = (int)s.getAttribute("userid");
                int request_userid = SecurityLayer.checkNumeric(request.getParameter("userid"));
                int tirocinio_id = tirocinio.getIdTirocinio();
                Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(request_userid, tirocinio.getIdTirocinio());
                if(tirocinio.getStatus() || richiesta!=null || session_userid!=request_userid){
                    //Il tirocinio è già assegnato, hai già una richesta oppure non sei colui che vuole aggiungere la richiesta
                    response.sendRedirect("show?tid="+tirocinio_id);
                }else{
                    Richiesta new_richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).creaRichiesta();
                    new_richiesta.setCfu(SecurityLayer.addSlashes(request.getParameter("cfu")));
                    new_richiesta.setIdStudente(request_userid);
                    new_richiesta.setIdTirocinio(tirocinio_id);
                    new_richiesta.setStatus("pending");
                    int tutore_id = SecurityLayer.checkNumeric(request.getParameter("tutore"));
                    Tutore tutore = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTutore(tutore_id);
                    new_richiesta.setCodTutore(tutore.getIdTutore());
                    new_richiesta.setNomeTutor(SecurityLayer.addSlashes(tutore.getNome()));
                    new_richiesta.setCognomeTutor(SecurityLayer.addSlashes(tutore.getCognome()));
                    new_richiesta.setEmailTutor(SecurityLayer.addSlashes(tutore.getEmailTutore()));
                    ((InternShipDataLayer)request.getAttribute("datalayer")).storeRichiesta(new_richiesta);
                    Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(tutore.getCodAzienda());
                    String toTutor = tutore.getEmailTutore();
                    String toCompanyResp = azienda.getEmailResp();
                    String from = "admin@internship.com";
                    Utente utente = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoUtente(session_userid);
                    String subject = "Nuova Richiesta per il Tirocinio '" + tirocinio.getTitolo() + "'";
                    String body = "Lo Studente " + utente.getNome() + " " + utente.getCognome() + " ha fatto richiesta per il tirocinio '" 
                            + tirocinio.getTitolo() + "'. Ha scelto come tutore " + tutore.getNome() + " " + tutore.getCognome() + " ed il numero di CFU richiesti sono "
                            + new_richiesta.getCfu()+". Sul sito potete trovare tutte le informazioni al riguardo.";
                    String filename1 = "tutor" + new_richiesta.getIdRichiesta();
                    String filename2 = "company" + new_richiesta.getIdRichiesta();
                    //invio email al tutore
                    String dirpath = getServletContext().getRealPath("/email/").replace("build\\", "");
                    SecurityLayer.createMessage(toTutor, from, subject, body, filename1, dirpath);
                    SecurityLayer.createMessage(toCompanyResp, from, subject, body, filename2, dirpath);
                    //invio email al responsabile dell'azienda
                    response.sendRedirect("show?tid="+tirocinio_id);
                }
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException {
        try{
            HttpSession s = SecurityLayer.checkSession(request);
            if(request.getParameter("tid")!=null){
                int tirocinio_id = SecurityLayer.checkNumeric(request.getParameter("tid"));
                if(s!=null){
                    String utype = (String)s.getAttribute("type");
                    request.setAttribute("Session", s);                
                    Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(tirocinio_id);
                    if(utype.equals("stud")){
                        //se sei uno studente puoi fare richiesta per il tirocinio
                        if(tirocinio!=null){
                            if(request.getParameter("add")!=null){
                                action_add(request, response, tirocinio);
                            }else{
                                request.setAttribute("page_title", "Aggiungi richiesta");
                                action_default(request, response, tirocinio);
                            }
                        }else{
                            //il tirocinio non esiste
                            response.sendRedirect("applications");
                        }
                    }else{
                        //altrimenti (azienda, admin) non puoi
                        response.sendRedirect("show?tid="+tirocinio_id);
                    }
                }else{
                    //sei anonimo
                    response.sendRedirect("show?tid="+tirocinio_id);
                }
            }else{
                //non è stato specificato un tirocinio
                response.sendRedirect("home");
            }
        }catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);        
        } catch (MessagingException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response); 
        }  
    }
    
}