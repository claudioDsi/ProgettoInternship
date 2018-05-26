/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;

import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Richiesta;
import it.example.framework.data.DataLayerException;

import java.util.List;
import it.example.datamodel.Utente;

/**
 *
 * @author claudio
 */
public class UtenteImpl implements Utente{
    private int idUtente;
    
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private int privilegi;
    private String dataNasc;
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
    protected Boolean dirty;
    
    
    public UtenteImpl(InternShipDataLayer data){
        this.ownerdatalayer=data;
        idUtente=0;        
        username="";
        password="";
        privilegi=0;
        nome="";
        cognome="";
        dataNasc="";
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
        dirty = false;
    }
    


   
    /**
     * @return the idStudente
     */
    @Override
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * @param idUtente the idStudente to set
     */
    @Override
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
        this.dirty = true;
    }

    /**
     * @return the dataNasc
     */
     @Override
    public String getDataNasc() {
        return dataNasc;
    }

    /**
     * @param dataNasc the dataNasc to set
     */
     @Override
    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
        this.dirty = true;
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
        this.dirty = true;
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
        this.dirty = true;
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
        this.dirty = true;
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
        this.dirty = true;
    }

    /**
     * @return the handicap
     */
     @Override
    public Boolean getHandicap() {
        return handicap;
    }
    
    @Override
    public void setHandicap(boolean handicap) {
        this.handicap=handicap;
        this.dirty = true;
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
        this.dirty = true;
    }

    @Override
    public String getPassword() {
       return password;
    }

    @Override
    public void setPassword(String password) {
        this.password=password;
        this.dirty = true;
    }

    @Override
    public int getPrivilegi() {
        return privilegi;
    }

    @Override
    public void setPrivilegi(int privilegi) {
        this.privilegi=privilegi;
        this.dirty = true;
    }

    @Override
    public String getResidenza() {
        return residenza;
    }

    @Override
    public void setResidenza(String residenza) {
        this.residenza=residenza;
        this.dirty = true;
    }

    @Override
    public String getLaurea() {
        return laurea;
    }

    @Override
    public void setLaurea(String laurea) {
        this.laurea=laurea;
        this.dirty = true;
    }

    @Override
    public String getDottorato() {
        return dottorato;
    }

    @Override
    public void setDottorato(String dottorato) {
        this.dottorato=dottorato;
        this.dirty = true;
    }

    @Override
    public String getSpecializzazione() {
        return specializzazione;
    }

    @Override
    public void setSpecializzazione(String specializzazione) {
        this.specializzazione=specializzazione;
        this.dirty = true;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome=nome;
        this.dirty = true;
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome=cognome;
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
    public void copyFrom(Utente utente) throws DataLayerException {
        idUtente = utente.getIdUtente();
        username = utente.getUsername();
        password = utente.getPassword();
        privilegi = utente.getPrivilegi();
        nome = utente.getNome();
        cognome = utente.getCognome();
        dataNasc = utente.getDataNasc();
        luogoNasc = utente.getLuogoNasc();
        residenza = utente.getResidenza();
        codFisc = utente.getCodFisc();
        telefono = utente.getTelefono();
        cdl = utente.getCdl();
        handicap = utente.getHandicap();
        laurea = utente.getLaurea();
        dottorato = utente.getDottorato();
        specializzazione = utente.getSpecializzazione();
        this.dirty = true;
    }
}
