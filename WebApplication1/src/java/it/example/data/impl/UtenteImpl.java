/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;

import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Richiesta;
import it.example.framework.data.DataLayerException;
import java.util.Date;
import java.util.List;
import it.example.datamodel.Utente;

/**
 *
 * @author claudio
 */
public class UtenteImpl implements Utente{
    private int idUtente;
    private int matricola; //forse da togliere
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private int privilegi;
    private Date dataNasc;
    private String luogoNasc;
    private String residenza;
    private String codFisc;
    private String telefono;
    private String cdl;
    private String laurea;
    private String dottorato;
    private String specializzazione;
    private Boolean handicap;
    private List<Richiesta> listaRichieste;
    protected InternShipDataLayer ownerdatalayer;
    
    
    public UtenteImpl(InternShipDataLayer data){
        this.ownerdatalayer=data;
        idUtente=0;
        matricola=0;
        username="";
        password="";
        privilegi=0;
        nome="";
        cognome="";
        data=null;
        luogoNasc="";
        residenza="";
        codFisc="";
        telefono="";
        cdl="";
        laurea="";
        dottorato="";
        specializzazione="";
        handicap=false;
        listaRichieste=null;
        
    }
    


   
    /**
     * @return the idStudente
     */
    @Override
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * @param idStudente the idStudente to set
     */
    @Override
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * @return the dataNasc
     */
     @Override
    public Date getDataNasc() {
        return dataNasc;
    }

    /**
     * @param dataNasc the dataNasc to set
     */
     @Override
    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    /**
     * @return the luogoNasc
     */
     @Override
    public String getLuogoNasc() {
        return luogoNasc;
    }

    /**
     * @param luogoNasc the luogoNasc to set
     */
     @Override
    public void setLuogoNasc(String luogoNasc) {
        this.luogoNasc = luogoNasc;
    }

    /**
     * @return the codFisc
     */
     @Override
    public String getCodFisc() {
        return codFisc;
    }

    /**
     * @param codFisc the codFisc to set
     */
     @Override
    public void setCodFisc(String codFisc) {
        this.codFisc = codFisc;
    }

    /**
     * @return the telefono
     */
     @Override
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
     @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the cdl
     */
     @Override
    public String getCdl() {
        return cdl;
    }

    /**
     * @param cdl the cdl to set
     */
     @Override
    public void setCdl(String cdl) {
        this.cdl = cdl;
    }

    /**
     * @return the handicap
     */
     @Override
    public Boolean getHandicap() {
        return handicap;
    }

    @Override
    public List<Richiesta> getListaRichieste() throws DataLayerException {
        return listaRichieste;
    }

   

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username=username;
    }

    @Override
    public String getPassword() {
       return password;
    }

    @Override
    public void setPassword(String password) {
        this.password=password;
    }

    @Override
    public int getPrivilegi() {
        return privilegi;
    }

    @Override
    public void setPrivilegi(int privilegi) {
        this.privilegi=privilegi;
    }

    @Override
    public String getResidenza() {
        return residenza;
    }

    @Override
    public void setResidenza(String residenza) {
        this.residenza=residenza;
    }

    @Override
    public void setHandicap(boolean handicap) {
        this.handicap=handicap;
    }

    @Override
    public String getLaurea() {
        return laurea;
    }

    @Override
    public void setLaurea(String laurea) {
        this.laurea=laurea;
    }

    @Override
    public String getDottorato() {
        return dottorato;
    }

    @Override
    public void setDottorato(String dottorato) {
        this.dottorato=dottorato;
    }

    @Override
    public String getSpecializzazione() {
        return specializzazione;
    }

    @Override
    public void setSpecializzazione(String specializzazione) {
        this.specializzazione=specializzazione;
    }
    
}
