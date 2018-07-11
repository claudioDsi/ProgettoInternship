/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import org.univaq.tirocinio.datamodel.*;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.SplitSlashesFmkExt;
import org.univaq.tirocinio.framework.result.StreamResult;

/**
 *
 * @author vince
 */
public class DownloadProgetto extends InternshipDBController {
    
    private void action_download(HttpServletRequest request, HttpServletResponse response, int id_progetto) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            StreamResult result = new StreamResult(getServletContext());
            Documento documento = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoDocumento(id_progetto);
            if(documento!=null){
                String dir_path = getServletContext().getRealPath("/uploads").replace("build\\", "");
                try (InputStream is = new FileInputStream(dir_path + File.separatorChar + documento.getLocalfile())) {
                    request.setAttribute("contentType", documento.getTipo());
                    result.activate(is, documento.getSize(), documento.getFilename(), request, response);
                }
            }else {
                request.setAttribute("exception", new Exception("Resource not found in file database"));
                action_error(request, response);
            }
        }catch(DataLayerException ex){
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException {      
        try{
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                int userid = (int)s.getAttribute("userid");
                String type = (String)s.getAttribute("type");
                if(type.equals("comp")){
                    //sei un'azienda
                    if(request.getParameter("tid")!=null){
                        int tid = SecurityLayer.checkNumeric(request.getParameter("tid"));
                        Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(tid);
                        if(tirocinio!=null){
                            if(tirocinio.getIdAzienda()==userid){
                                //sei l'azienda che ha creato il tirocinio
                                int id_progetto = tirocinio.getIdProgetto();
                                if(id_progetto!=0){
                                    //ti faccio scaricare il tuo progetto
                                    action_download(request, response, tirocinio.getIdProgetto());
                                }else{
                                    //non ci sono progetti da scaricare
                                    response.sendRedirect("show?tid="+tid);
                                }
                            }else{
                                //non puoi scaricare la scansione del progetto per questo tirocinio
                                response.sendRedirect("show?tid="+tid);
                            }
                        }else{
                            //il tirocinio non esiste
                            response.sendRedirect("panel");
                        }
                    }else{
                        //non è stato scelto un tirocinio
                        response.sendRedirect("panel");
                    }
                }else if(type.equals("stud")){
                    //sei uno studente
                    if(request.getParameter("tid")!=null){
                        int tid = SecurityLayer.checkNumeric(request.getParameter("tid"));
                        Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(tid);
                        if(tirocinio!=null){
                            Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(userid, tirocinio.getIdTirocinio());
                            if(richiesta!=null){
                                if(richiesta.getStatus().equals("accepted")){
                                    //sei lo studente a cui è stato assegnato il tirocinio
                                    int id_progetto = tirocinio.getIdProgetto();
                                    if(id_progetto!=0){
                                        //ti faccio scaricare il progetto
                                        action_download(request, response, tirocinio.getIdProgetto());
                                    }else{
                                        //non puoi
                                        response.sendRedirect("show?tid="+tid);
                                    }
                                }else{
                                    //non puoi scaricare la scansione del progetto per questo tirocinio
                                    response.sendRedirect("show?tid="+tid);
                                }
                            }else{
                                //non hai fatto richiesta per il tirocinio
                                response.sendRedirect("profile?uid="+userid+"&utype="+type);
                            }
                        }else{
                            //il tirocinio non esiste
                            response.sendRedirect("profile?uid="+userid+"&utype="+type);
                        }
                    }else{
                        //non hai scelto un tirocinio
                        response.sendRedirect("profile?uid="+userid+"&utype="+type);
                    }
                }else{
                    //non sei azienda o studente
                    response.sendRedirect("home");
                }
            }else{
                //sei un utente anonimo
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
