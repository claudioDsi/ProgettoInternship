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
    
    private int idRichiesta;
    private int idStudente;
    private int idTirocinio;
    private String status;    
    private String cfu;
    private Utente studente;
    private Tirocinio tirocinio;    
    private String nomeTutor;
    private String cognomeTutor;
    private String emailTutor;
    protected Boolean dirty;
    protected InternShipDataLayer ownLayer;
    
    public RichiestaImpl(InternShipDataLayer dataLayer){
        ownLayer = dataLayer;
        idRichiesta = 0;
        idStudente = 0;
        idTirocinio = 0;
        status = "";        
        cfu = "";        
        nomeTutor = "";
        cognomeTutor = "";
        emailTutor = "";
        studente = null;
        tirocinio = null;
        this.dirty = false;
    }
    
    @Override
    public int getIdRichiesta() {
        return idRichiesta;
    }

    @Override
    public String getStatus() {
       return status;
    }

    @Override
    public void setStatus(String status) {
        this.status=status;
        this.dirty = true;
    }

    @Override
    public String getCfu() {
       return cfu;
    }

    @Override
    public void setCfu(String cfu) {
        this.cfu=cfu;
        this.dirty = true;
    }

    @Override
    public Utente getStudente() throws DataLayerException {
       return studente;
    }

    @Override
    public void setStudente(Utente studente) {
        this.studente=studente;
        this.dirty = true;
    }

    @Override
    public Tirocinio getTirocinio() throws DataLayerException {
       return tirocinio;
    }

    @Override
    public void setTirocinio(Tirocinio tirocinio) {
        this.tirocinio=tirocinio;
        this.dirty = true;
    }
    
    @Override
    public  void setIdStudente(int idStudente){
        this.idStudente=idStudente;
        this.dirty = true;
    }
    
    @Override
    public int getIdStudente() {
        return idStudente;
    }
    
    @Override
    public  void setIdTirocinio(int idTirocinio){
        this.idTirocinio=idTirocinio;
        this.dirty = true;
    }

    @Override
    public int getIdTirocinio() {
        return idTirocinio;
    }
    
    @Override
    public String getNomeTutor() {
       return nomeTutor;
    }

    @Override
    public void setNomeTutor(String nomeTutor) {
        this.nomeTutor=nomeTutor;
        this.dirty = true;
    }

    @Override
    public String getCognomeTutor() {
        return cognomeTutor;
    }

    @Override
    public void setCognomeTutor(String cognomeTutor) {
       this.cognomeTutor=cognomeTutor;
       this.dirty = true;
    }

    @Override
    public String getEmailTutor() {
        return emailTutor;
    }

    @Override
    public void setEmailTutor(String emailTutor) {
        this.emailTutor=emailTutor;
        this.dirty = true;
    }
    
    @Override
    public boolean isDirty() {
        return dirty;
    }
    
    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    @Override
    public void copyFrom(Richiesta richiesta) throws DataLayerException {
        idStudente = richiesta.getIdStudente();
        idTirocinio = richiesta.getIdTirocinio();
        status = richiesta.getStatus();
        cfu = richiesta.getCfu();
        nomeTutor = richiesta.getNomeTutor();
        cognomeTutor = richiesta.getCognomeTutor();
        emailTutor = richiesta.getEmailTutor();
        this.dirty = true;
    }

}
