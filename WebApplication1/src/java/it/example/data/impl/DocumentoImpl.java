/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;
import it.example.datamodel.Documento;
import it.example.framework.data.DataLayerException;
import java.io.InputStream;
import it.example.datamodel.InternShipDataLayer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author claudio
 */
public class DocumentoImpl implements Documento{
    
    private int codDocumento;
    private int idTirocinio;    
    private String filename;
    private long dimensione;
    private String tipo;
    protected InternShipDataLayer ownLayer;
    private boolean isModified;
    
    public DocumentoImpl(InternShipDataLayer dataLayer){
        codDocumento=0;
        idTirocinio=0;
        filename="";
        dimensione=0;
        tipo="";
        ownLayer=dataLayer;
    }

    @Override
    public int getCodDocumento() {
        return codDocumento;
    }

    @Override
    public int getIdTirocinio() {
        return idTirocinio;
    }

    @Override
    public void setCodDocumento(int codDocumento) {
        this.codDocumento=codDocumento;
    }

    @Override
    public void setIdTirocinio(int idTirocinio) {
        this.idTirocinio=idTirocinio;
    }

    @Override
    public InputStream getStreamDocumento() throws DataLayerException {
         try {
            return new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            throw new DataLayerException("Error opening image file", ex);
        }
    
    }

    @Override
    public void setStreamDocumento(InputStream is) throws DataLayerException {
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
    

  
    
}
