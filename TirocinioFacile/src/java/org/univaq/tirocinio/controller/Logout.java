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
import org.univaq.tirocinio.framework.result.FailureResult;
import org.univaq.tirocinio.framework.security.SecurityLayer;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class Logout extends InternshipDBController {
    
    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{ 
            SecurityLayer.disposeSession(request);
            response.sendRedirect("login");
        }catch(IOException ex) {
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
            request.setAttribute("page_title", "Logout Utente");
            try{  
                action_logout(request, response);
            }catch (IOException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }
    }
    
}