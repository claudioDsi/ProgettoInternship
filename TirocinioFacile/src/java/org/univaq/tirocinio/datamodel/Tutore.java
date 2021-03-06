/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.datamodel;

import org.univaq.tirocinio.framework.data.DataLayerException;
import java.util.Date;
/**
 *
 * @author claudio
 */
public interface Tutore {
    
    int getIdTutore();
    void setIdTutore(int idTutore);
    
    String getNome();
    void setNome(String nome);
    
    String getCognome();
    void setCognome(String cognome);
    
    Date getDataNasc();
    void setDataNasc(Date data);
    
    String getEmailTutore();
    void setEmailTutore(String emailTutore);
    
    int getNumTirocini();
    void setNumTirocini(int numTirocini);
    
    String getTelefono();
    void setTelefono(String telefono);
    
    int getCodAzienda();
    void setCodAzienda(int codAzienda);
    
    //num tirocini
    
    boolean isDirty();
    void setDirty(boolean dirty);
    void copyFrom(Tutore tutore) throws DataLayerException;
}
