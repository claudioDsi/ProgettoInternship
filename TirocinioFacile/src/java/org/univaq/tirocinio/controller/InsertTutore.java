/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Tutore;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;

public class InsertTutore extends InternshipDBController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
            //action_update function dell'esempio del prof
            try{
                Tutore t;
                t = ((InternShipDataLayer)request.getAttribute("datalayer")).creaTutore();
                t.setNome("Nome");
                t.setCognome("Cognome");
                t.setDataNasc("DataNasc");
                t.setNumTirocini(5);
                t.setTelefono("Telefono");
                t.setCodAzienda(2); 
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeTutore(t);
            }catch (DataLayerException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
            }
    }
    
}