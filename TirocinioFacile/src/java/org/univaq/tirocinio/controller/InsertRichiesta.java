/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Richiesta;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;

public class InsertRichiesta extends InternshipDBController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
            //action_update function dell'esempio del prof
            try{
                Richiesta r;
                r = ((InternShipDataLayer)request.getAttribute("datalayer")).creaRichiesta();
                r.setIdStudente(1);
                r.setIdTirocinio(1);
            r.setStatus("Status");
            r.setCfu("Cfu");
            r.setNomeTutor("NomeTutor");
            r.setCognomeTutor("CognomeTutor");
            r.setEmailTutor("EmailTutor");
            ((InternShipDataLayer)request.getAttribute("datalayer")).storeRichiesta(r);
            }catch (DataLayerException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
            }
    }
    
}