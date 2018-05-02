/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;
import it.example.datamodel.Tirocinio;
import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Tutore;
import it.example.datamodel.Azienda;
/**
 *
 * @author claudio
 */
public class TirocinioImpl implements Tirocinio{
    
    private int idTirocinio;
    private String luogo;
    private String orario;
    private String mesi;
    private int numOre;
    private String obiettivi;
    private String facilitazioni;
    private String modalità;
    
    //riferimenti alle altre tabelle
    private Tutore tutore;    
    private int idTutore;
    
    private Azienda azienda;
    private int idAzienda;
    //data layer
    private InternShipDataLayer ownLayer;
    
    public TirocinioImpl(InternShipDataLayer dataLayer){
        ownLayer=dataLayer;
        idTirocinio=0;
        luogo="";
        orario="";
        mesi="";
        numOre=0;
        obiettivi="";
        facilitazioni="";
        modalità="";
        
    }

    @Override
    public int getIdTirocinio() {
        return idTirocinio;
    }

    @Override
    public String getLuogo() {
       return luogo;   
    }

    @Override
    public void setLuogo(String luogo) {
        this.luogo=luogo;
    }

    @Override
    public String getOrario() {
       return orario;
    }

    @Override
    public void setOrario(String orario) {
        this.orario=orario;
    }

    @Override
    public String getMesi() {
        return mesi;
    }

    @Override
    public void setMesi(String mesi) {
        this.mesi=mesi;
    }

    @Override
    public int getNumOre() {
       return numOre;
    }

    @Override
    public void setNumOre(int numOre) {
       this.numOre=numOre;
    }

    @Override
    public String getObiettivi() {
        return obiettivi;
    }

    @Override
    public void setObiettivi(String obiettivi) {
        this.obiettivi=obiettivi;
    }

    @Override
    public String getModalità() {
        return modalità;
    }

    @Override
    public void setModalità(String modalità) {
        this.modalità=modalità;
    }

    @Override
    public String getFacilitazioni() {
        return facilitazioni;
    }

    @Override
    public void setFacilitazioni(String facilitazioni) {
        this.facilitazioni=facilitazioni;
    }

    @Override
    public void setIdTirocinio(int idTirocinio) {
        this.idTirocinio=idTirocinio;
    }

    @Override
    public int getIdTutore() {
        return idTutore;
    }

    @Override
    public void setIdTutore(int idTutore) {
       this.idTutore=idTutore;
    }

    @Override
    public int getIdAzienda() {
        return idAzienda;
    }

    @Override
    public void setIdAzienda(int idAzienda) {
        this.idAzienda=idAzienda;
    }

    @Override
    public Azienda getAzienda() {
        return azienda;
    }

    @Override
    public void setAzienda(Azienda azienda) {
        this.azienda=azienda;
    }

    @Override
    public Tutore getTutore() {
        return tutore;
    }

    @Override
    public void setTutore(Tutore tutore) {
        this.tutore=tutore;
    }

    
    
}
