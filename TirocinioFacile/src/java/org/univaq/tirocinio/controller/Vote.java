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
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;
import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.datamodel.Tirocinio;

/**
 *
 * @author vince
 */
public class Vote extends InternshipDBController {
    
    private void action_vote(HttpServletRequest request, HttpServletResponse response, Tirocinio tirocinio, Azienda azienda) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            int valutazione = SecurityLayer.checkNumeric(request.getParameter("voto"));
            tirocinio.setStatusVoto(true);
            //modifico lo stato del voto sul tirocinio
            ((InternShipDataLayer)request.getAttribute("datalayer")).updateStatusVoto(tirocinio);
            int numTiroCompl = azienda.getNumTiroCompletati();
            int new_numTiroCompl = numTiroCompl+1;
            float old_val = azienda.getValutazione();
            float new_val = (float)((old_val*numTiroCompl)+valutazione)/new_numTiroCompl;
            azienda.setValutazione(new_val);
            azienda.setNumTiroCompletati(new_numTiroCompl);
            //aggiorno i dati sulla valutazione e sul numero di tirocini votati dell'azienda
            ((InternShipDataLayer)request.getAttribute("datalayer")).updateValutazione(azienda);
            response.sendRedirect("show?tid="+tirocinio.getIdTirocinio());
        }catch(DataLayerException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
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
                HttpSession s = SecurityLayer.checkSession(request);
                if(s!=null){
                    int userid = (int)s.getAttribute("userid");
                    String type = (String)s.getAttribute("type");
                    if(type.equals("stud")){
                        //sei uno studente
                        int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tid"));
                        int id_azienda = SecurityLayer.checkNumeric(request.getParameter("aid"));
                        Azienda azienda = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoAzienda(id_azienda);
                        Tirocinio tirocinio = ((InternShipDataLayer)request.getAttribute("datalayer")).getInfoTirocinio(id_tirocinio);
                        Richiesta richiesta = ((InternShipDataLayer)request.getAttribute("datalayer")).getRichiestaStudenteTirocinio(userid, id_tirocinio);
                        if(richiesta.getStatus().equals("accepted") && !tirocinio.getStatusVoto() && userid==richiesta.getIdStudente() && tirocinio.getStatus()){
                            //sono lo studente la cui richiesta per quel tirocinio è stata accettata
                            action_vote(request, response, tirocinio, azienda);
                        }else{
                            response.sendRedirect("profile?uid=${Session.getAttribute('userid')}&utype=${Session.getAttribute('type')}");
                        }
                    }else if(type.equals("comp")){
                        //sei un'azienda
                        response.sendRedirect("profile?uid=${Session.getAttribute('userid')}&utype=${Session.getAttribute('type')}");
                    }
                }else{
                    //non sei iscritto
                    response.sendRedirect("home");
                }
            }catch (IOException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }catch (TemplateManagerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }catch (DataLayerException ex){
                request.setAttribute("exception", ex);
                action_error(request, response);
            }  
    }
    
}