/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.datamodel;

import org.univaq.tirocinio.framework.data.DataLayerException;
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
    
    String getTitolo();
    void setTitolo(String titolo);
    
    boolean getStatus();
    void setStatus(boolean status);
    
    int getNumOre();
    void setNumOre(int numOre);
    
    String getObiettivi();
    void setObiettivi(String obiettivi);
    
    String getModalità();
    void setModalità(String modalità);
    
    String getFacilitazioni();
    void setFacilitazioni(String facilitazioni);
    
    String getSettore();
    void setSettore(String settore);
    
    int getIdTutore();
    void setIdTutore(int idTutore); 
    
    Azienda getAzienda() throws DataLayerException;
    void setAzienda(Azienda azienda);
    //aggiungere il metodo protected nell'implemenzione  
    int getIdAzienda();
    void setIdAzienda(int idAzienda); 
    
    Tutore getTutore() throws DataLayerException;
    void setTutore(Tutore tutore);
    
    List<Richiesta> getListaRichieste() throws DataLayerException;
    
    boolean isDirty();
    void setDirty(boolean dirty);
    void copyFrom(Tirocinio tirocinio) throws DataLayerException;
    
}
