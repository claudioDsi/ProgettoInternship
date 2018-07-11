/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
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
import javax.servlet.http.Part;
import org.univaq.tirocinio.datamodel.Documento;
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.datamodel.Tirocinio;

/**
 *
 * @author vince
 */
public class UploadProgetto extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, int tid) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            request.setAttribute("Session", s);
            request.setAttribute("page_title", "Upload del Progetto del Tirocinio");
            request.setAttribute("tid", tid);
            res.activate("upload_progetto.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_upload(HttpServletRequest request, HttpServletResponse response, int tid) throws IOException, ServletException, TemplateManagerException, NoSuchAlgorithmException, DataLayerException{
        Part file_to_upload = request.getPart("filetoupload");
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        String dir_path = getServletContext().getRealPath("/")+"/uploads";
        File uploaded_file = File.createTempFile("upload_", "", new File(dir_path));
        Documento doc = ((InternShipDataLayer)request.getAttribute("datalayer")).creaDocumento();
        doc.setFilename(file_to_upload.getSubmittedFileName());//filename
        doc.setTipo(file_to_upload.getContentType());//tipo
        doc.setSize(file_to_upload.getSize()); //size
        doc.setLocalfile(uploaded_file.getName());//localfile       
        try (InputStream is = file_to_upload.getInputStream();
                OutputStream os = new FileOutputStream(uploaded_file)) {
            doc.setDocData(is,os,md);
            byte[] digest = md.digest();
            String sdigest = "";
            for (byte b : digest) {
                sdigest += String.valueOf(b);
            }
            doc.setDigest(sdigest); //digest
            ((InternShipDataLayer)request.getAttribute("datalayer")).storeDocumento(doc);
            Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(tid);
            ((InternShipDataLayer)request.getAttribute("datalayer")).updateProgettoTirocinio(tirocinio, doc.getDocId());
            response.sendRedirect("show?tid="+tid);
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
                                    if(request.getParameter("upload")!=null){
                                        if(request.getPart("filetoupload")!=null){
                                            //posso fare l'upload della convenzione
                                            action_upload(request, response, tid);
                                        }else{
                                            //faccio scegliere il file da caricare
                                            action_default(request, response, tid);
                                        }
                                    }else if(tirocinio.getIdProgetto()==0){
                                        //faccio scegliere il file da caricare
                                        action_default(request, response, tid);
                                    }else{
                                        //il progetto è stato già caricato
                                        response.sendRedirect("show?tid="+tid);
                                    }
                                }else{
                                    //non puoi caricare la scansione del progetto per questo tirocinio
                                    response.sendRedirect("show?tid="+tid);
                                }
                            }else{
                                //il tirocinio non esiste
                                response.sendRedirect("profile?uid="+userid+"&utype="+type);
                            }
                        }else{
                            //non è stato scelto un tirocinio
                            response.sendRedirect("profile?uid="+userid+"&utype="+type);
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
                                        if(request.getParameter("upload")!=null){
                                            if(request.getPart("filetoupload")!=null){
                                                //posso fare l'upload della convenzione
                                                action_upload(request, response, tid);
                                            }else{
                                                //faccio scegliere il file da caricare
                                                action_default(request, response, tid);
                                            }
                                        }else if(tirocinio.getIdProgetto()==0){
                                            //faccio scegliere il file da caricare
                                            action_default(request, response, tid);
                                        }else{
                                            //il progetto è stato già caricato
                                            response.sendRedirect("show?tid="+tid);
                                        }
                                    }else{
                                        //non puoi caricare la scansione del progetto per questo tirocinio
                                        response.sendRedirect("show?tid="+tid);
                                    }
                                }else{
                                    //lo studente non ha fatto richiesta per il tirocinio
                                    response.sendRedirect("show?tid="+tid);
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
            }catch (DataLayerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            } catch (NoSuchAlgorithmException ex) {  
                request.setAttribute("exception", ex);
                action_error(request, response);
            } 
    }
    
}