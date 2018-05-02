/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;

import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Richiesta;
import it.example.datamodel.Studente;
import it.example.datamodel.Tirocinio;

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
    private Studente studente;
    private Tirocinio tirocinio;
    protected InternShipDataLayer ownLayer;
    
    //introdurre liste oggetti?
    
    public RichiestaImpl(InternShipDataLayer dataLayer){
        ownLayer=dataLayer;
        idStudente=0;
        idTirocinio=0;
        status="";
        progetto="";
        cdl="";
        studente=null;
        tirocinio=null;
                
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
    public Studente getStudente() {
       return studente;
    }

    @Override
    public void setStudente(Studente studente) {
        this.studente=studente;
    }

    @Override
    public Tirocinio getTirocinio() {
       return tirocinio;
    }

    @Override
    public void setTirocinio(Tirocinio tirocinio) {
        this.tirocinio=tirocinio;
    }
    
    protected  void setIdStudente(int idStudente){
        
    }
    protected  void setIdTirocinio(int idTirocinio){
        
    }
    
}
