/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.datamodel;

import org.univaq.tirocinio.framework.data.DataLayerException;

/**
 *
 * @author claudio
 */
public interface Richiesta {

    int getIdRichiesta();
    void setIdRichiesta(int idRichiesta);
    
    int getCodTutore();
    void setCodTutore(int codTutore);
    
    Utente getStudente() throws  DataLayerException;
    void setStudente(Utente studente);
    
    int getIdStudente();
    void setIdStudente(int idStudente);
    
    int getIdTirocinio();
    void setIdTirocinio(int idTirocinio);
    
    Tirocinio getTirocinio() throws DataLayerException;
    void setTirocinio(Tirocinio tirocinio);
    
    String getStatus();
    void setStatus(String status);

    String getCfu();
    void setCfu(String cdl);
    
    String getNomeTutor();
    void setNomeTutor(String nomeTutor);
    
    String getCognomeTutor();
    void setCognomeTutor(String cognomeTutor);
    
    String getEmailTutor();
    void setEmailTutor(String emailTutor);

    boolean isDirty();
    void setDirty(boolean dirty);
    void copyFrom(Richiesta richiesta) throws DataLayerException;
}
