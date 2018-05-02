/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;
import it.example.datamodel.Azienda;
import it.example.datamodel.InternShipDataLayer;
/**
 *
 * @author claudio
 */
public class AziendaImpl implements Azienda{
    private int idAzienda;
    private String nomeAzienda;
    private String indirizzo;
    private String partitaIva;
    private String rappresentante;
    private String responsabile;
    private String emailResp;
    private String foro;
    private float valutazione;
    private Documento convenzione;
    private InternShipDataLayer ownLayer;
    
    public AziendaImpl(InternShipDataLayer dataLayer){
        ownLayer=dataLayer;
        idAzienda=0;
        nomeAzienda="";
        indirizzo="";
        partitaIva="";
        rappresentante="";
        responsabile="";
        emailResp="";
        foro="";
        valutazione=0;
        convenzione=new Documento();
      
    }

    /**
     * @return the idAzienda
     */
    @Override
    public int getIdAzienda() {
        return idAzienda;
    }

    /**
     * @param idAzienda the idAzienda to set
     */
     @Override
    public void setIdAzienda(int idAzienda) {
        this.idAzienda = idAzienda;
    }

    /**
     * @return the nomeAzienda
     */
     @Override
    public String getNomeAzienda() {
        return nomeAzienda;
    }

    /**
     * @param nomeAzienda the nomeAzienda to set
     */
     @Override
    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
    }

    /**
     * @return the indirizzo
     */
     @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * @param indirizzo the indirizzo to set
     */
     @Override
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * @return the partitaIva
     */
     @Override
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * @param partitaIva the partitaIva to set
     */
     @Override
    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    /**
     * @return the rappresentante
     */
     @Override
    public String getRappresentante() {
        return rappresentante;
    }

    /**
     * @param rappresentante the rappresentante to set
     */
     @Override
    public void setRappresentante(String rappresentante) {
        this.rappresentante = rappresentante;
    }

    /**
     * @return the responsabile
     */
     @Override
    public String getResponsabile() {
        return responsabile;
    }

    /**
     * @param responsabile the responsabile to set
     */
     @Override
    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    /**
     * @return the emailResp
     */
     @Override
    public String getEmailResp() {
        return emailResp;
    }

    /**
     * @param emailResp the emailResp to set
     */
     @Override
    public void setEmailResp(String emailResp) {
        this.emailResp = emailResp;
    }

    /**
     * @return the foro
     */
     @Override
    public String getForo() {
        return foro;
    }

    /**
     * @param foro the foro to set
     */
     @Override
    public void setForo(String foro) {
        this.foro = foro;
    }

    /**
     * @return the valutazione
     */
     @Override
    public float getValutazione() {
        return valutazione;
    }

    /**
     * @param valutazione the valutazione to set
     */
     @Override
    public void setValutazione(float valutazione) {
        this.valutazione = valutazione;
    }

    /**
     * @return the convenzione
     */
     @Override
    public Documento getConvenzione() {
        return convenzione;
    }

    /**
     * @param convenzione the convenzione to set
     */
     @Override
    public void setConvenzione(Documento convenzione) {
        this.convenzione = convenzione;
    }
    
}
