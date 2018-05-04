/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import it.example.framework.data.DataLayerException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author claudio
 */
public interface Utente {   
   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
    int getIdUtente();    
    void setIdUtente(int idUtente);   

    String getUsername();
    void setUsername(String username);
    
    String getPassword();
    void setPassword(String password);
    
    int getPrivilegi();
    void setPrivilegi(int privilegi);
    
    Date getDataNasc();  
    void setDataNasc(Date dataNasc);
    
    String getLuogoNasc();
    void setLuogoNasc(String luogoNasc);
    
    String getCodFisc();   
    void setCodFisc(String codFisc);
    
     String getTelefono(); 
     void setTelefono(String telefono);
     
     String getCdl();
     void setCdl(String cdl);
     
     String getResidenza();
     void setResidenza(String residenza);
     
     
     Boolean getHandicap();
     void setHandicap(boolean handicap);
     
     String getLaurea();
     void setLaurea(String laurea);
     
     String getDottorato();
     void setDottorato(String dottorato);
     
     String getSpecializzazione();
     void setSpecializzazione(String specializzazione);
     
     List<Richiesta> getListaRichieste() throws DataLayerException;
     
    


     
     
     
     
     
     
    
}
