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
public interface Tirocinio {
    
    int getIdTirocinio();
    void setIdTirocinio(int idTirocinio);
    
    String getLuogo();
    void setLuogo(String luogo);
    
    String getOrario();
    void setOrario(String orario);
    
    String getMesi();
    void setMesi(String mesi);
    
    
    int getNumOre();
    void setNumOre(int numOre);
    
    String getObiettivi();
    void setObiettivi(String obiettivi);
    
    String getModalità();
    void setModalità(String modalità);
    
    String getFacilitazioni();
    void setFacilitazioni(String facilitazioni);
    
    int getIdTutore();
    void setIdTutore(int idTutore);
    
    int getIdAzienda();
    void setIdAzienda(int idAzienda);
    
    
    Azienda getAzienda();
    void setAzienda(Azienda azienda);
    
    Tutore getTutore();
    void setTutore(Tutore tutore);
    
    
    
    
    
    
    
    
    
}
