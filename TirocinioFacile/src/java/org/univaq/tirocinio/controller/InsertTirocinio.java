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

/**
 *
 * @author vince
 */
public class InsertTirocinio extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, Azienda azienda) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("page_title", "Inserisci nuovo tirocinio");
            HttpSession s = SecurityLayer.checkSession(request);
            List<Tutore> listaTutori = ((InternShipDataLayer)request.getAttribute("datalayer")).getListaTutoriAzienda(azienda);
            if(listaTutori!=null){
                request.setAttribute("listaTutori", listaTutori);
            }
            request.setAttribute("Session", s);
            res.activate("new_tirocinio.ftl.html", request, response);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_add(HttpServletRequest request, HttpServletResponse response, Azienda azienda) throws IOException, ServletException, TemplateManagerException {
        try {
            //tutti i campi devono essere riempiti
            boolean no_update = false;
            //controllo campo titolo
            if(request.getParameter("titolo")==null || request.getParameter("titolo").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkText(request.getParameter("titolo"))){
                    request.setAttribute("messaggiotitolo", "Il titolo inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo luogo
            if(request.getParameter("luogo")==null || request.getParameter("luogo").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkPlace(request.getParameter("luogo"))){
                    request.setAttribute("messaggioluogo", "Il luogo inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo orario
            if(request.getParameter("orario")==null || request.getParameter("orario").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkText(request.getParameter("orario"))){
                    request.setAttribute("messaggioorario", "L'orario inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo numore
            if(request.getParameter("numore")==null || request.getParameter("numore").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkIsNumber(request.getParameter("numore"))){
                    request.setAttribute("messaggionumore", "Il numero di ore inserito non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo nummesi
            if(request.getParameter("nummesi")==null || request.getParameter("nummesi").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkIsNumber(request.getParameter("nummesi"))){
                    request.setAttribute("messaggionummesi", "Il numero di mesi selezionato non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo obiettivi
            if(request.getParameter("obiettivi")==null || request.getParameter("obiettivi").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkText(request.getParameter("obiettivi"))){
                    request.setAttribute("messaggioobiettivi", "Gli obiettivi scelti non sono validi!");
                    no_update = true;
                }    
            }
            //controllo campo modalita
            if(request.getParameter("modalita")==null || request.getParameter("modalita").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkText(request.getParameter("modalita"))){
                    request.setAttribute("messaggiomodalita", "Le modalità inserite non sono valide!");
                    no_update = true;
                }    
            }
            //controllo campo facilitazioni
            if(request.getParameter("facilitazioni")==null || request.getParameter("facilitazioni").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkText(request.getParameter("facilitazioni"))){
                    request.setAttribute("messaggiofacilitazioni", "Le facilitazioni inserite non sono valide!");
                    no_update = true;
                }    
            }
            //controllo campo settore
            if(request.getParameter("settore")==null || request.getParameter("settore").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(SecurityLayer.checkText(request.getParameter("settore"))){
                    request.setAttribute("messaggiosettore", "Il settore scelto non è valido!");
                    no_update = true;
                }    
            }
            //controllo campo tutore
            if(request.getParameter("tutore")==null || request.getParameter("tutore").equals("")){
                request.setAttribute("messaggiocampi", "Tutti i campi devono essere riempiti!");
                no_update = true;
            }else{
                if(request.getParameter("tutore").equals("errore")){
                    request.setAttribute("messaggiotutore", "Devi prima inserire un tutore per poter creare un tirocinio!");
                    no_update = true;
                }
            }
            if(!no_update){
                TemplateResult res = new TemplateResult(getServletContext());
                Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).creaTirocinio();
                int userid = SecurityLayer.checkNumeric(request.getParameter("userid"));
                tirocinio.setTitolo(SecurityLayer.addSlashes(request.getParameter("titolo")));
                tirocinio.setLuogo(SecurityLayer.addSlashes(request.getParameter("luogo")));
                tirocinio.setOrario(SecurityLayer.addSlashes(request.getParameter("orario")));
                tirocinio.setMesi(SecurityLayer.checkNumeric(request.getParameter("nummesi")));
                tirocinio.setNumOre(SecurityLayer.checkNumeric(request.getParameter("numore")));
                tirocinio.setObiettivi(SecurityLayer.addSlashes(request.getParameter("obiettivi")));
                tirocinio.setModalita(SecurityLayer.addSlashes(request.getParameter("modalita")));
                tirocinio.setFacilitazioni(SecurityLayer.addSlashes(request.getParameter("facilitazioni")));
                tirocinio.setSettore(SecurityLayer.addSlashes(request.getParameter("settore")));
                tirocinio.setIdAzienda(userid);
                tirocinio.setIdTutore(SecurityLayer.checkNumeric(request.getParameter("tutore")));
                tirocinio.setStatusProgetto(false);
                tirocinio.setIdProgetto(0);
                tirocinio.setStatusResoconto(false);
                tirocinio.setDescrizione("");
                tirocinio.setRisultato("");
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeTirocinio(tirocinio);
                action_activate(request, response, tirocinio.getIdTirocinio());
            }else{
                //è stato generato un messaggio di errore
                action_default(request, response, azienda);
            }
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int user_key) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(user_key);
            HttpSession s = SecurityLayer.checkSession(request);
            request.setAttribute("Session", s);
            request.setAttribute("tirocinio", tirocinio);
            res.activate("result.ftl.html", request, response);
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
            if(s!=null){
                int userid = (int)s.getAttribute("userid"); //id utente in sessione
                String utype = (String)s.getAttribute("type");
                if(request.getParameter("add")!=null && utype.equals("comp")){
                    Azienda azienda =((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                    //vedo se l'azienda è abilitata
                    if(azienda.getStatus()){
                        //controllo che l'utente in sessione sia quello che ha inviato la form
                        int form_user = SecurityLayer.checkNumeric(request.getParameter("userid"));
                        if(userid==form_user){
                            action_add(request, response, azienda);
                        }else{
                            //ritorno al profilo dell'utente in sessione
                            response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                        }
                    }else{
                        //non sei abilitata
                        response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                    }
                }else if(utype.equals("comp")){
                    Azienda azienda =((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                    //vedo se l'azienda è abilitata
                    if(azienda.getStatus()){
                        //se sei un'azienda abilitata ti mostro la form per aggiungere il tirocinio
                        action_default(request, response, azienda);
                    }else{
                        //non sei abilitata
                        response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                    }
                }else{
                    //sei utente o admin
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
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);        }  
    }
    
}