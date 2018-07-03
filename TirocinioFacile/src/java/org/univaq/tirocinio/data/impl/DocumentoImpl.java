/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.data.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.univaq.tirocinio.datamodel.Documento;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;

/**
 *
 * @author vince
 */
public class DocumentoImpl implements Documento{
    
    private int docId;
    private long size;
    private String descrizione;
    private String tipo;
    private String filename;
    protected InternShipDataLayer ownLayer;
    private boolean dirty;
    
    public DocumentoImpl(InternShipDataLayer dataLayer) {
        this.ownLayer = dataLayer;
        docId = 0;
        size = 0;
        descrizione = "";
        tipo = "";
        filename = "";
        dirty = false;
    }
    
    @Override
    public int getDocId() {
        return docId;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
        this.dirty = true;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public void setFilename(String filename) {
        this.filename = filename;
        this.dirty = true;
    }

//    @Override
//    public InputStream getImageData() throws DataLayerException {
//        return ownerdatalayer.getImageData(key);
//    }
//    @Override
//    public void setImageData(InputStream is) throws DataLayerException {
//        ownerdatalayer.storeImageData(key, is);
//        this.dirty = true;
//    }
    
    @Override
    public InputStream getDocData() throws DataLayerException {
        try {
            return new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            throw new DataLayerException("Error opening file", ex);
        }
    }

    @Override
    public void setDocData(InputStream is) throws DataLayerException {

        OutputStream os = null;
        try {
            byte[] buffer = new byte[1024];
            os = new FileOutputStream(filename);
            int read;
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
            this.dirty = true;
        } catch (FileNotFoundException ex) {
            throw new DataLayerException("Error storing image file", ex);
        } catch (IOException ex) {
            throw new DataLayerException("Error storing image file", ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.dirty = true;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    //questi metodi sono protetti per essere accessibili solo alle altre classi
    //del data model
    //these methods are protected to be accessible to other datamodel classes only
    protected void setDocId(int docId) {
        this.docId = docId;
    }

    protected void setSize(long size) {
        this.size = size;
    }
    
}
