/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.controller;


import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.framework.data.DataLayerException;
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.result.TemplateManagerException;
import org.univaq.tirocinio.framework.result.TemplateResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author claudio
 */
public class MostraTirocini extends InternshipDBController{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
       request.setAttribute("page_title", "Riepilogo tirocini");
            try{         
                    
                action_show_tirocini(request, response);
                
            }catch (IOException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }catch (TemplateManagerException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
        }  
    }
    
    
    
         
     
    
    private void action_show_tirocini(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        List<Tirocinio> list=new ArrayList<Tirocinio>();
        try{
            TemplateResult res=new TemplateResult(getServletContext());           
            list=((InternShipDataLayer)request.getAttribute("datalayer")).getListaTirocini();              
            request.setAttribute("tirocini", list);            
            res.activate("prova.ftl.html", request, response);
            
            
        }
        catch(DataLayerException dte){
            request.setAttribute("message", "Data access exception: " + dte.getMessage());
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

   
}
