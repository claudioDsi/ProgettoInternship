/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.datamodel;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import org.univaq.tirocinio.framework.data.DataLayerException;

/**
 *
 * @author vince
 */
public interface Documento {
    
    int getDocId();

    String getLocalfile();
    void setLocalfile(String localfile);
    
    String getDigest();
    void setDigest(String digest);

    InputStream getDocData() throws DataLayerException;

    void setDocData(InputStream is,OutputStream os, MessageDigest md) throws DataLayerException;

    String getTipo();

    void setTipo(String tipo);

    long getSize();
    void setSize(long size);

    public String getFilename();

    public void setFilename(String filename);

    void setDirty(boolean dirty);

    boolean isDirty();
    void copyFrom(Documento documento) throws DataLayerException;
    
}
