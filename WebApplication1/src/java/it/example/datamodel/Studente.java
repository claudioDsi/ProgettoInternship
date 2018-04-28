/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import java.util.Date;

/**
 *
 * @author claudio
 */
public interface Studente {   
   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
    int getIdStudente();    
    void setIdStudente(int idStudente);   

    
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
     
     Boolean getHandicap();
     
    


     
     
     
     
     
     
    
}
