/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.example.data.impl.*;
import it.example.datamodel.*;
import it.example.datamodel.InternShipDataLayer;
import it.example.framework.data.DataLayerException;

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