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

public class NewServlet extends InternshipDBController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
            //action_update function dell'esempio del prof
            try{
                Utente u;
                u = ((InternShipDataLayer)request.getAttribute("datalayer")).creaStudente();
                u.setUsername("pippo");
                u.setPassword("kdfhsg");
                u.setPrivilegi(1);
                u.setNome("Mario");
                u.setCognome("Rossi");
                u.setDataNasc("11-11-11");
                u.setLuogoNasc("Roma");
                u.setResidenza("Roma");
                u.setCodFisc("ksdfhdfuhsdl");
                u.setTelefono("340654684");
                u.setCdl("Informatica");
                u.setHandicap(false);
                u.setLaurea("Triennale");
                u.setDottorato("sgf");
                u.setSpecializzazione("model driven engineering");
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeStudente(u);
            }catch (DataLayerException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
            }
    }
    
    
    
}
