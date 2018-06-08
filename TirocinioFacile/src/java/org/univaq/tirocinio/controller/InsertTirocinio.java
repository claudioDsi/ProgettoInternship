/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;

import org.univaq.tirocinio.datamodel.Tirocinio;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;

public class InsertTirocinio extends InternshipDBController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
            //action_update function dell'esempio del prof
            try{
                Tirocinio t;
                t = ((InternShipDataLayer)request.getAttribute("datalayer")).creaTirocinio();
                t.setLuogo("Roma");
                t.setOrario("Orario");
                t.setNumOre(20);           
                t.setMesi("NumMesi");
                t.setObiettivi("Obiettivi");
                t.setModalità("Modalità");
                t.setFacilitazioni("Facilitazioni");
                t.setSettore("Settore");
                t.setIdTutore(1);
                t.setIdAzienda(2);
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeTirocinio(t);
            }catch (DataLayerException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
            }
    }
    
}
