/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import java.io.IOException;
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

public class ShowConvenzione extends InternshipDBController {
    
        private void action_show(HttpServletRequest request, HttpServletResponse response, Azienda azienda) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            HttpSession s = SecurityLayer.checkSession(request);
            request.setAttribute("Session", s);
            request.setAttribute("azienda", azienda);
            res.activate("show_convenzione.ftl.html", request, response);
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
            request.setAttribute("page_title", "Scarica o Stampa Convenzione");
            HttpSession s = SecurityLayer.checkSession(request);
            if(s!=null){
                int userid = (int)s.getAttribute("userid"); //id utente in sessione
                String utype = (String)s.getAttribute("type");
                if(utype.equals("comp")){
                    int uid = SecurityLayer.checkNumeric(request.getParameter("uid"));
                    Azienda azienda =((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(uid);
                    //vedo se la convenzione dell'azienda è stata generata e che l'azienda sia quella in sessione
                    if(azienda.getStatusConvenzione() && uid==userid){
                        action_show(request,response, azienda);
                    }else{
                        response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                    }
                }else{
                    //non sei un'azienda
                    response.sendRedirect("profile?uid="+userid+"&utype="+utype);
                }
            }else{
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
