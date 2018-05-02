/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

/**
 *
 * @author claudio
 */
public interface Richiesta {
    
    int getIdStudente();
    void setIdStudente(int idStudente);
    
    int getIdTirocinio();
    void setIdTirocinio(int idTirocinio);    
    
    String getStatus();
    void setStatus(String status);
    
    String getProgetto();
    void setProgetto(String progetto);
    
    String getCdl();
    void setCdl(String cdl);
    
    
    
}
