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
import java.util.List;
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

public class DownloadConvenzione extends InternshipDBController {
    
    private void action_download(HttpServletRequest request, HttpServletResponse response, int id_convenzione) throws IOException, ServletException, TemplateManagerException, DataLayerException {
        try {
            StreamResult result = new StreamResult(getServletContext());
            Documento documento = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoDocumento(id_convenzione);
            if(documento!=null){
                String dir_path = "../uploads";
                try (InputStream is = new FileInputStream(dir_path + File.separatorChar + documento.getLocalfile())) {
                            request.setAttribute("contentType", documento.getTipo());
                            result.activate(is, documento.getSize(), documento.getFilename(), request, response);
                        }
            }else {
                request.setAttribute("exception", new Exception("Resurce not found in file database"));
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
            int userid = (int)s.getAttribute("userid"); //id utente in sessione
            String utype = (String)s.getAttribute("type");
            if(utype.equals("comp")){
                Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(userid);
                if(request.getParameter("cid")!=null){
                    int id_convenzione = SecurityLayer.checkNumeric(request.getParameter("cid"));
                    if(id_convenzione==azienda.getIdConvenzione()){
                        //ti faccio scaricare la tua convenzione
                        action_download(request, response, azienda.getIdConvenzione());
                    }else{
                        //non puoi
                        response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                    }
                }else{
                    response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                }
            }else{
                response.sendRedirect("profile?uid="+userid+"&utype="+utype);
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
