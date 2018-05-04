/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import it.example.framework.data.DataLayerException;

/**
 *
 * @author claudio
 */
public interface Richiesta {
    
   
   
    Utente getStudente() throws  DataLayerException;
    void setStudente(Utente studente);
    
    Tirocinio getTirocinio() throws DataLayerException;
    void setTirocinio(Tirocinio tirocinio);
    
    String getStatus();
    void setStatus(String status);
    
    String getProgetto();
    void setProgetto(String progetto);
    
    String getCdl();
    void setCdl(String cdl);
    
    int getIdDocumento();
    void setIdDocumento(int idDocumento);
    
    
    
    String getNomeTutor();
    void setNomeTutor(String nomeTutor);
    
    String getCognomeTutor();
    void setCognomeTutor(String cognomeTutor);
    
    String getEmailTutor();
    void setEmailTutor(String emailTutor);
    
    
    
}
