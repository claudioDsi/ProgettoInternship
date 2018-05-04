/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import it.example.framework.data.DataLayerException;
import java.io.InputStream;

/**
 *
 * @author claudio
 */
public interface Convenzione {
    int getIdConvenzione();
    int getIdAzienda();
    
    void setIdAzienda(int idAzienda);    

    InputStream getStreamConvezione() throws DataLayerException;
    void setStreamConvenzione(InputStream is) throws DataLayerException;

    String getTipo();
    void setTipo(String tipo);

    long getDimensione();
    
    public String getFilename();
    public void setFilename(String filename);
    
    boolean isModified();
    
   

   
    
}
