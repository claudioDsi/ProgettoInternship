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
    private String cfu;
    private Utente studente;
    private Tirocinio tirocinio;    
    private String nomeTutor;
    private String cognomeTutor;
    private String emailTutor;
    
    protected InternShipDataLayer ownLayer;
    
    
    public RichiestaImpl(InternShipDataLayer dataLayer){
        ownLayer=dataLayer;
        idStudente=0;
        idTirocinio=0;
        status="";        
        cfu="";        
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
    public String getCfu() {
       return cfu;
    }

    @Override
    public void setCfu(String cfu) {
        this.cfu=cfu;
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

    @Override
    public int getIdStudente() {
        return idStudente;
    }

    @Override
    public int getIdTirocinio() {
        return idTirocinio;
    }

   
}
