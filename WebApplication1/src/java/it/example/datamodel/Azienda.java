/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import it.example.data.impl.ConvenzioneImpl;
import it.example.framework.data.DataLayerException;
import java.util.List;

/**
 *
 * @author claudio
 */
public interface Azienda {
    
    int getIdAzienda();
    void setIdAzienda(int idAzienda);
    
    String getNomeAzienda();
    void setNomeAzienda(String nomeAzienda);
    
    String getUsername();
    void setUsername(String username);
    
    String getPassword();
    void setPassword(String password);
    
    int getPrivilegi();
    void setPrivilegi(int privilegi);
    
    boolean getStatus();
    void setStatus(boolean status);
    
    String getIndirizzo();
    void setIndirizzo(String indirizzo); 
    
    String getPartitaIva();
    void setPartitaIva(String partitaIva);
    
    String getRappresentante();
    void setRappresentante(String rappresentante); 
    
    String getResponsabile();
    void setResponsabile(String responsabile); 
    
    String getEmailResp(); 
    void setEmailResp(String emailResp);
    
    String getForo() ;
    void setForo(String foro);
    
    //lista tirocini
    
    List<Tirocinio> getListaTirocini() throws DataLayerException;    

   
    float getValutazione();
    void setValutazione(float valutazione);
    ConvenzioneImpl getConvenzione() throws DataLayerException;
    
    void setConvenzione(ConvenzioneImpl convenzione);
    
    
    
    
}
