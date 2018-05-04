/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;
import it.example.datamodel.Convenzione;
import it.example.datamodel.InternShipDataLayer;
import it.example.framework.data.DataLayerException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author claudio
 */
public class ConvenzioneImpl implements Convenzione{
    private int idConvezione;
    private int idAzienda;
    private String filename;
    private long dimensione;
    private String tipo;
    private boolean isModified;
    protected InternShipDataLayer ownLayer;
    
    public ConvenzioneImpl(InternShipDataLayer dataLayer){
        idConvezione=0;
        idAzienda=0;
        filename="";
        dimensione=0;
        tipo="";
        isModified=false;
        ownLayer=dataLayer;
    }

    @Override
    public int getIdConvenzione() {
        return idConvezione;
    }

    @Override
    public int getIdAzienda() {
        return idAzienda;
    }

    @Override
    public void setIdAzienda(int idAzienda) {
        this.idAzienda=idAzienda;
    }

    @Override
    public InputStream getStreamConvezione() throws DataLayerException {
          try {
            return new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            throw new DataLayerException("Error opening image file", ex);
        }
    }

    @Override
    public void setStreamConvenzione(InputStream is) throws DataLayerException {
         OutputStream os = null;
        try {
            byte[] buffer = new byte[1024];
            os = new FileOutputStream(filename);
            int read;
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
            this.isModified = true;
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
        this.tipo=tipo;
    }

    @Override
    public long getDimensione() {
        return dimensione;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public void setFilename(String filename) {
        this.filename=filename;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }
    
}
