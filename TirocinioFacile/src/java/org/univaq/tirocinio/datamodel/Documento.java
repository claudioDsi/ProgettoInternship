/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.datamodel;

import java.io.InputStream;
import org.univaq.tirocinio.framework.data.DataLayerException;

/**
 *
 * @author vince
 */
public interface Documento {
    
    int getDocId();

    String getDescrizione();

    void setDescrizione(String descrizione);

    InputStream getDocData() throws DataLayerException;

    void setDocData(InputStream is) throws DataLayerException;

    String getTipo();

    void setTipo(String tipo);

    long getSize();

    public String getFilename();

    public void setFilename(String filename);

    void setDirty(boolean dirty);

    boolean isDirty();
    
}
