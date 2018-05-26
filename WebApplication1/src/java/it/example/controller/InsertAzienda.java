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

public class InsertAzienda extends InternshipDBController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException {
            //action_update function dell'esempio del prof
            try{
                Azienda a;
                a = ((InternShipDataLayer)request.getAttribute("datalayer")).creaAzienda();
                a.setUsername("Username");
                a.setPassword("Password");
                a.setPrivilegi(1);
                a.setNomeAzienda("Nome");
                a.setRagioneSociale("RagioneSociale");
                a.setIndirizzo("Indirizzo");
                a.setPartitaIva("PartitaIva");
                a.setStatus(true);
                a.setCodiceFisc("CodiceFiscale");
                a.setNomeRappr("NomeRappr");
                a.setCognomeRappr("CognomeRappr");
                a.setNomeResp("NomeResp");
                a.setCognomeResp("CognomeResp");
                a.setTelefonoResp("TelefonoResp");
                a.setEmailResp("EmailResp");
                a.setForo("Foro");
                a.setValutazione((float)2.3);
                ((InternShipDataLayer)request.getAttribute("datalayer")).storeAzienda(a);
            }catch (DataLayerException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
            }
    }
}