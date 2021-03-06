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
import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.Documento;

/**
 *
 * @author vince
 */
public class UploadConvenzione extends InternshipDBController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response, int aid) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);
            request.setAttribute("Session", s);
            request.setAttribute("page_title", "Upload della Convenzione dell'Azienda");
            request.setAttribute("aid", aid);
            res.activate("upload_convenzione.ftl.html", request, response);
        }catch(TemplateManagerException ex){
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
    
    private void action_upload(HttpServletRequest request, HttpServletResponse response, int aid) throws IOException, ServletException, TemplateManagerException, NoSuchAlgorithmException, DataLayerException{
        Part file_to_upload = request.getPart("filetoupload");
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        String dir_path = getServletContext().getRealPath("/uploads").replace("build\\", "");
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
            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(aid);
            ((InternShipDataLayer)request.getAttribute("datalayer")).updateConvenzioneAzienda(azienda, doc.getDocId());
            response.sendRedirect("managecompany");
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
                    if(type.equals("admin")){
                        //sei admin
                        if(request.getParameter("aid")!=null){
                            int aid = SecurityLayer.checkNumeric(request.getParameter("aid"));
                            Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(aid);
                            if(azienda!=null){
                                if(request.getParameter("upload")!=null){
                                    if(request.getPart("filetoupload")!=null){
                                        //posso fare l'upload della convenzione
                                        action_upload(request, response, aid);
                                    }else{
                                        //faccio scegliere il file da caricare
                                        action_default(request, response, aid);
                                    }
                                }else if(azienda.getIdConvenzione()==0){
                                    //faccio scegliere il file da caricare
                                    action_default(request, response, aid);
                                }else{
                                    //la convenzione è stata già caricata
                                    response.sendRedirect("managecompany");
                                }
                            }else{
                                //l'azienda non esiste
                                response.sendRedirect("managecompany");
                            }
                        }else{
                            response.sendRedirect("managecompany");
                        }    
                    }else{
                    //sei un'azienda o un utente
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