/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;
import it.example.datamodel.Tutore;
import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Tirocinio;
import java.util.Date;
/**
 *
 * @author claudio
 */


public class TutoreImpl implements Tutore{
    private int idTutore;
    private String nome;
    private String cognome;
    private String dataNasc;
    private InternShipDataLayer ownLayer;
    private int numTirocini;
    private String telefono;
    private int codAzienda;
    private Tirocinio tirocinio;
    
    
    
    
    public TutoreImpl(InternShipDataLayer data){
        idTutore=0;
        ownLayer=data;
        nome="";
        cognome="";
        dataNasc="";   
        numTirocini=0;
        telefono="";
        codAzienda=0;
        
    }
    

    @Override
    public int getIdTutore() {
       return idTutore;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
       this.nome=nome;
    }

    @Override
    public String getCognome() {
       return cognome;
    }

    @Override
    public void setCognome(String cognome) {
       this.cognome=cognome;
    }

    @Override
    public String getDataNasc() {
        return dataNasc;
    }

    @Override
    public void setDataNasc(String dataNasc) {
        this.dataNasc=dataNasc;
    }

    @Override
    public int getNumTirocini() {
        return numTirocini;
    }

    @Override
    public void setNumTirocini(int numTirocini) {
        this.numTirocini=numTirocini;
    }

    @Override
    public String getTelefono() {
       return telefono;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono=telefono;
    }

    @Override
    public int getCodAzienda() {
        return codAzienda;
    }

    @Override
    public void setCodAzienda(int codAzienda) {
        this.codAzienda=codAzienda;
    }
    
}
