/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;

import it.example.datamodel.Studente;
import it.example.datamodel.InternShipDataLayer;
import java.util.Date;

/**
 *
 * @author claudio
 */
public class StudenteImpl implements Studente{
    private int idStudente;
    private int matricola;
    private String nome;
    private String cognome;
    private Date dataNasc;
    private String luogoNasc;
    private String residenza;
    private String codFisc;
    private String telefono;
    private String cdl;
    private Boolean handicap;
    protected InternShipDataLayer ownerdatalayer;
    public StudenteImpl(InternShipDataLayer data){
        this.ownerdatalayer=data;
        matricola=0;
        nome="";
        cognome="";
        data=null;
        luogoNasc="";
        residenza="";
        codFisc="";
        telefono="";
        cdl="";
        handicap=false;
        
    }
    


   
    /**
     * @return the idStudente
     */
    @Override
    public int getIdStudente() {
        return idStudente;
    }

    /**
     * @param idStudente the idStudente to set
     */
    @Override
    public void setIdStudente(int idStudente) {
        this.idStudente = idStudente;
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
    
}
