/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Azienda;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;

public class InsertAzienda extends InternshipDBController {

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("register_company.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, NoSuchAlgorithmException {
        try {
            boolean no_update = false;
            //si controlla che l'username inserito è uno nuovo ma non già scelto da altri utenti
            List<String> lista_username_azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getUsernameAzienda();
            List<String> lista_username_utenti = ((InternShipDataLayer)request.getAttribute("datalayer")).getUsernameUtenti();
            for(int i = 0; i < lista_username_azienda.size(); i++){
                if(lista_username_azienda.get(i).equals(request.getParameter("username"))){
                    request.setAttribute("usernamemessage", "The username is already chosen!");
                    no_update = true;
                }
            }
            for(int i = 0; i < lista_username_utenti.size(); i++){
                if(lista_username_utenti.get(i).equals(request.getParameter("username"))){
                    request.setAttribute("usernamemessage", "The username is already chosen!");
                    no_update = true;
                }
            } 
            //controllo che la password inserita sia stata ripetuta correttamente
            if(!(request.getParameter("password").equals(request.getParameter("rpassword")))){
                request.setAttribute("passwordmessage", "You have to repeat the right password!");
                no_update = true;
            }
            if(!no_update){
                Azienda a = ((InternShipDataLayer)request.getAttribute("datalayer")).creaAzienda();
                a.setUsername(SecurityLayer.addSlashes(request.getParameter("username")));
                String password = SecurityLayer.addSlashes(request.getParameter("password"));
                a.setPassword(SecurityLayer.securePassword(password));
                a.setPrivilegi(2);
                a.setNomeAzienda(SecurityLayer.addSlashes(request.getParameter("nome")));
                a.setRagioneSociale(SecurityLayer.addSlashes(request.getParameter("ragionesociale")));
                a.setIndirizzo(SecurityLayer.addSlashes(request.getParameter("indirizzo")));
                a.setPartitaIva(SecurityLayer.addSlashes(request.getParameter("partitaiva")));
                a.setStatus(false);
                a.setCodiceFisc(SecurityLayer.addSlashes(request.getParameter("codicefisc")));
                a.setNomeRappr(SecurityLayer.addSlashes(request.getParameter("nomerappr")));
                a.setCognomeRappr(SecurityLayer.addSlashes(request.getParameter("cognomerappr")));
                a.setNomeResp(SecurityLayer.addSlashes(request.getParameter("nomeresp")));
                a.setCognomeResp(SecurityLayer.addSlashes(request.getParameter("cognomeresp")));
                a.setTelefonoResp(SecurityLayer.addSlashes(request.getParameter("telefonoresp")));
                a.setEmailResp(SecurityLayer.addSlashes(request.getParameter("emailresp")));
                a.setForo(SecurityLayer.addSlashes(request.getParameter("foro")));
                a.setValutazione((float)0);
                a.setNumeroTirocini(0);
                a.setNumTiroCompletati(0);
                a.setStatusConvenzione(false);
                a.setIdConvenzione(0);
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeAzienda(a);
                action_activate(request, response, a.getIdAzienda());
            }else{
                action_default(request, response);
            }
            }catch (DataLayerException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
            }
    }
    
    private void action_activate(HttpServletRequest request, HttpServletResponse response, int azienda_key) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(azienda_key);
            request.setAttribute("azienda", azienda);
            res.activate("result.ftl.html", request, response);
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response){
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
            request.setAttribute("page_title", "Inserisci Azienda");
            try{
                HttpSession s = SecurityLayer.checkSession(request);
                if(s==null){
                    //non sei loggato quindi puoi registrarti
                    if(request.getParameter("add")!=null){
                        //se il parametro add ha un valore assegnato allora inserisco l'azienda registrata
                        action_write(request, response);
                    }else{
                        //altrimenti mostro la pagina per registrarsi
                        action_default(request, response);
                    }
                }else{
                    //sei già loggato
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