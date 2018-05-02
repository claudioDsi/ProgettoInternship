/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;

import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Richiesta;

/**
 *
 * @author claudio
 */
public class RichiestaImpl implements Richiesta{
    private int idStudente;
    private int idTirocinio;
    private String status;
    private String progetto;
    private String cdl;
    private InternShipDataLayer ownLayer;
    
    //introdurre liste oggetti?
    
    public RichiestaImpl(InternShipDataLayer dataLayer){
        ownLayer=dataLayer;
        idStudente=0;
        idTirocinio=0;
        status="";
        progetto="";
        cdl="";
    }

    @Override
    public int getIdStudente() {
        return idStudente;
    }

    @Override
    public int getIdTirocinio() {
        return idTirocinio;
    }

    @Override
    public String getStatus() {
       return status;
    }

    @Override
    public void setStatus(String status) {
        this.status=status;
    }

    @Override
    public String getProgetto() {
        return progetto;
    }

    @Override
    public void setProgetto(String progetto) {
       this.progetto=progetto;
    }

    @Override
    public String getCdl() {
       return cdl;
    }

    @Override
    public void setCdl(String cdl) {
        this.cdl=cdl;
    }

    @Override
    public void setIdStudente(int idStudente) {
        this.idStudente=idStudente;
    }

    @Override
    public void setIdTirocinio(int idTirocinio) {
        this.idTirocinio=idTirocinio;
    }
    
}
