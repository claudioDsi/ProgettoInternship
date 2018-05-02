/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import it.example.data.impl.Documento;
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
    
    List<Tirocinio> getListaTirocini();
    

    /**
     * @return the valutazione
     */
    float getValutazione();
    void setValutazione(float valutazione);
    Documento getConvenzione();
    /**
     * @param convenzione the convenzione to set
     */
    void setConvenzione(Documento convenzione);
    
    
    
    
}
