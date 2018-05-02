/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import java.util.List;


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
    
    //int getIdTutore();
    //void setIdTutore(int idTutore); come azienda
    
    
    Azienda getAzienda();
    void setAzienda(Azienda azienda);
    //aggiungere il metodo protected nell'implemenzione
    //void setIdAzienda(int idAzienda);   
    
    Tutore getTutore();
    void setTutore(Tutore tutore);
    
    List<Richiesta> getListaRichieste();
    
    
    
    
    
    
    
    
    
}
