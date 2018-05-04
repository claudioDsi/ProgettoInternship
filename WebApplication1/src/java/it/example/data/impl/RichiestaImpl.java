/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;

import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Richiesta;
import it.example.datamodel.Tirocinio;
import it.example.framework.data.DataLayerException;
import it.example.datamodel.Utente;

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
    private Utente studente;
    private Tirocinio tirocinio;
    private int idDocumento;
    private String nomeTutor;
    private String cognomeTutor;
    private String emailTutor;
    
    protected InternShipDataLayer ownLayer;
    
    //introdurre liste oggetti?
    
    public RichiestaImpl(InternShipDataLayer dataLayer){
        ownLayer=dataLayer;
        idStudente=0;
        idTirocinio=0;
        status="";
        progetto="";
        cdl="";
        idDocumento=0;
        nomeTutor="";
        cognomeTutor="";
        emailTutor="";
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
    public Utente getStudente() throws DataLayerException {
       return studente;
    }

    @Override
    public void setStudente(Utente studente) {
        this.studente=studente;
    }

    @Override
    public Tirocinio getTirocinio() throws DataLayerException {
       return tirocinio;
    }

    @Override
    public void setTirocinio(Tirocinio tirocinio) {
        this.tirocinio=tirocinio;
    }
    
    protected  void setIdStudente(int idStudente){
        this.idStudente=idStudente;
    }
    protected  void setIdTirocinio(int idTirocinio){
        this.idTirocinio=idTirocinio;
    }

    @Override
    public int getIdDocumento() {
        return idDocumento;
    }

    @Override
    public void setIdDocumento(int idDocumento) {
        this.idDocumento=idDocumento;
    }

    

    @Override
    public String getNomeTutor() {
       return nomeTutor;
    }

    @Override
    public void setNomeTutor(String nomeTutor) {
        this.nomeTutor=nomeTutor;
    }

    @Override
    public String getCognomeTutor() {
        return cognomeTutor;
    }

    @Override
    public void setCognomeTutor(String cognomeTutor) {
       this.cognomeTutor=cognomeTutor;
    }

    @Override
    public String getEmailTutor() {
        return emailTutor;
    }

    @Override
    public void setEmailTutor(String emailTutor) {
        this.emailTutor=emailTutor;
    }
    
}
