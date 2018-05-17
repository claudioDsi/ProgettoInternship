/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;
import it.example.datamodel.Azienda;
import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Tirocinio;
import it.example.framework.data.DataLayerException;
import java.util.List;
/**
 *
 * @author claudio
 */
public class AziendaImpl implements Azienda{
    private int idAzienda;
    private String nomeAzienda;
    private String ragioneSociale;
    private String indirizzo;
    private String partitaIva;
    private String username;
    private String password;
    private int privilegi;
    private boolean status;
    private String codiceFisc;
    private String nomeRappr;
    private String cognomeRappr;
    private String responsabile;
    private String emailResp;
    private String foro;
    private float valutazione;
   
    
    private List<Tirocinio> listaTirocini;
    protected InternShipDataLayer ownLayer;
    
    public AziendaImpl(InternShipDataLayer dataLayer){
        ownLayer=dataLayer;
        idAzienda=0;
        nomeAzienda="";
        ragioneSociale="";        
        username="";
        password="";
        privilegi=0;
        status=false;
        indirizzo="";
        partitaIva="";
        codiceFisc="";
        nomeRappr="";
        cognomeRappr="";
        responsabile="";
        emailResp="";
        foro="";
        valutazione=0;        
        listaTirocini=null;
      
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
    public String getNomeRappr() {
        return nomeRappr;
    }

    /**
     * @param nomeRappr the rappresentante to set
     */
     @Override
    public void setNomeRappr(String nomeRappr) {
        this.nomeRappr=nomeRappr;
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
    public List<Tirocinio> getListaTirocini()throws DataLayerException{
       return listaTirocini;
       //aggiungere controlli datalayer
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
    public boolean getStatus() {
        return status;
    }

    @Override
    public void setStatus(boolean status) {
        this.status=status;
    }

    @Override
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    @Override
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale=ragioneSociale;
    }

    @Override
    public String getCodiceFisc() {
        return codiceFisc;
    }

    @Override
    public void setCodiceFisc(String codiceFisc) {
        this.codiceFisc=codiceFisc;
    }

    @Override
    public String getCognomeRappr() {
        return cognomeRappr;
    }

    @Override
    public void setCognomeRappr(String cognomeRappr) {
        this.cognomeRappr=cognomeRappr;
    }
    
}
