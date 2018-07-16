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
public interface Azienda {
    
    int getIdAzienda();
    void setIdAzienda(int idAzienda);
    
    String getNomeAzienda();
    void setNomeAzienda(String nomeAzienda);
    
    String getRagioneSociale();
    void setRagioneSociale(String ragioneSociale);
    
    String getUsername();
    void setUsername(String username);
    
    String getPassword();
    void setPassword(String password);
    
    int getPrivilegi();
    void setPrivilegi(int privilegi);
    
    boolean getStatus();
    void setStatus(boolean status);
    
    boolean getStatusConvenzione();
    void setStatusConvenzione(boolean statusConvenzione);
    
    String getIndirizzo();
    void setIndirizzo(String indirizzo); 
    
    String getPartitaIva();
    void setPartitaIva(String partitaIva);
    
    String getCodiceFisc();
    void setCodiceFisc(String codiceFisc);
    
    String getNomeRappr();
    void setNomeRappr(String nomeRappr); 
    
    String getCognomeRappr();
    void setCognomeRappr(String cognomeRappr);
    
    String getNomeResp();
    void setNomeResp(String nomeResp); 
    
    String getCognomeResp();
    void setCognomeResp(String cognomeResp);
    
    String getTelefonoResp(); 
    void setTelefonoResp(String telefonoResp);
    
    String getEmailResp(); 
    void setEmailResp(String emailResp);
    
    String getForo() ;
    void setForo(String foro);

    List<Tirocinio> getListaTirocini() throws DataLayerException;
    void setListaTirocini(List<Tirocinio> listaTirocini);
    
    List<Tutore> getListaTutori() throws DataLayerException;
    void setListaTutori(List<Tutore> listaTutori);

    float getValutazione();
    void setValutazione(float valutazione);
    
    int getNumeroTirocini();
    void setNumeroTirocini(int numeroTirocini);
    
    int getNumTiroCompletati();
    void setNumTiroCompletati(int numTiroCompletati);
    
    int getIdConvenzione();
    void setIdConvenzione(int idConvenzione);

    boolean isDirty();
    void setDirty(boolean dirty);
    void copyFrom(Azienda azienda) throws DataLayerException;
}
