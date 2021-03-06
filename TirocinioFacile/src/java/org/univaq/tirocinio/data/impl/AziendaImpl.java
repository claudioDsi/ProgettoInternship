/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.data.impl;
import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import java.util.List;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.datamodel.Tutore;
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
    private String nomeResp;
    private String cognomeResp;
    private String telefonoResp;
    private String emailResp;
    private String foro;
    private float valutazione;
    private int numeroTirocini;
    private int numTiroCompletati;
    private boolean statusConvenzione;
    private int idConvenzione;
    protected Boolean dirty;
    private List<Tirocinio> listaTirocini;
    private List<Tutore> listaTutori;
    protected InternShipDataLayer ownLayer;
    
    public AziendaImpl(InternShipDataLayer dataLayer){
        ownLayer = dataLayer;
        idAzienda = 0;
        nomeAzienda = "";
        ragioneSociale = "";        
        username = "";
        password = "";
        privilegi = 0;
        status = false;
        indirizzo = "";
        partitaIva = "";
        codiceFisc = "";
        nomeRappr = "";
        cognomeRappr = "";
        nomeResp = "";
        cognomeResp = "";
        telefonoResp = "";
        emailResp = "";
        foro = "";
        valutazione = 0;
        numeroTirocini = 0;
        numTiroCompletati = 0;
        statusConvenzione = false;
        idConvenzione = 0;
        listaTirocini = null;
        listaTutori = null;
        dirty = false;
    }

    @Override
    public int getIdAzienda() {
        return idAzienda;
    }

    @Override
    public void setIdAzienda(int idAzienda) {
        this.idAzienda = idAzienda;
        this.dirty = true;
    }

    @Override
    public String getNomeAzienda() {
        return nomeAzienda;
    }

    @Override
    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
        this.dirty = true;
    }

    @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    @Override
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
        this.dirty = true;
    }

    @Override
    public String getPartitaIva() {
        return partitaIva;
    }

    @Override
    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
        this.dirty = true;
    }

    @Override
    public String getNomeRappr() {
        return nomeRappr;
    }

    @Override
    public void setNomeRappr(String nomeRappr) {
        this.nomeRappr=nomeRappr;
        this.dirty = true;
    }
    
    @Override
    public String getCognomeRappr() {
        return cognomeRappr;
    }

    @Override
    public void setCognomeRappr(String cognomeRappr) {
        this.cognomeRappr=cognomeRappr;
        this.dirty = true;
    }

    @Override
    public String getNomeResp() {
        return nomeResp;
    }

    @Override
    public void setNomeResp(String nomeResp) {
        this.nomeResp = nomeResp;
        this.dirty = true;
    }
    
    @Override
    public String getCognomeResp() {
        return cognomeResp;
    }

    @Override
    public void setCognomeResp(String cognomeResp) {
        this.cognomeResp = cognomeResp;
        this.dirty = true;
    }

    @Override
    public String getEmailResp() {
        return emailResp;
    }

    @Override
    public void setEmailResp(String emailResp) {
        this.emailResp = emailResp;
        this.dirty = true;
    }
    
    @Override
    public String getTelefonoResp() {
        return telefonoResp;
    }

    @Override
    public void setTelefonoResp(String telefonoResp) {
        this.telefonoResp = telefonoResp;
        this.dirty = true;
    }

    @Override
    public String getForo() {
        return foro;
    }

    @Override
    public void setForo(String foro) {
        this.foro = foro;
        this.dirty = true;
    }

    @Override
    public float getValutazione() {
        return valutazione;
    }

    @Override
    public void setValutazione(float valutazione) {
        this.valutazione = valutazione;
        this.dirty = true;
    }
    
    @Override
    public int getNumeroTirocini() {
        return numeroTirocini;
    }

    @Override
    public void setNumeroTirocini(int numeroTirocini) {
        this.numeroTirocini = numeroTirocini;
        this.dirty = true;
    }
    
    @Override
    public int getNumTiroCompletati() {
        return numTiroCompletati;
    }

    @Override
    public void setNumTiroCompletati(int numTiroCompletati) {
        this.numTiroCompletati = numTiroCompletati;
        this.dirty = true;
    }
    
    @Override
    public List<Tirocinio> getListaTirocini() throws DataLayerException{
        if(listaTirocini==null){
            listaTirocini = ownLayer.getListaTirociniByAzienda(this);
        }
        return listaTirocini;
    }
    
    @Override
    public void setListaTirocini(List<Tirocinio> listaTirocini) {
        this.listaTirocini = listaTirocini;
        this.dirty = true;
    }
    
    @Override
    public List<Tutore> getListaTutori() throws DataLayerException{
        if(listaTutori==null){
            listaTutori = ownLayer.getListaTutoriAzienda(this);
        }
        return listaTutori;
    }
    
    @Override
    public void setListaTutori(List<Tutore> listaTutori) {
        this.listaTutori = listaTutori;
        this.dirty = true;
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
    public boolean getStatus() {
        return status;
    }

    @Override
    public void setStatus(boolean status) {
        this.status=status;
        this.dirty = true;
    }
    
    @Override
    public boolean getStatusConvenzione() {
        return statusConvenzione;
    }

    @Override
    public void setStatusConvenzione(boolean statusConvenzione) {
        this.statusConvenzione=statusConvenzione;
        this.dirty = true;
    }

    @Override
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    @Override
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale=ragioneSociale;
        this.dirty = true;
    }

    @Override
    public String getCodiceFisc() {
        return codiceFisc;
    }

    @Override
    public void setCodiceFisc(String codiceFisc) {
        this.codiceFisc=codiceFisc;
        this.dirty = true;
    }
    
    @Override
    public int getIdConvenzione() {
        return idConvenzione;
    }

    @Override
    public void setIdConvenzione(int idConvenzione) {
        this.idConvenzione = idConvenzione;
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
    public void copyFrom(Azienda azienda) throws DataLayerException {
        idAzienda = azienda.getIdAzienda();
        username = azienda.getUsername();
        password = azienda.getPassword();
        privilegi = azienda.getPrivilegi();
        nomeAzienda = azienda.getNomeAzienda();
        ragioneSociale = azienda.getRagioneSociale();
        indirizzo = azienda.getIndirizzo();
        partitaIva = azienda.getPartitaIva();
        status = azienda.getStatus();
        statusConvenzione = azienda.getStatusConvenzione();
        codiceFisc = azienda.getCodiceFisc();
        nomeRappr = azienda.getNomeRappr();
        cognomeRappr = azienda.getCognomeRappr();
        nomeResp = azienda.getNomeResp();
        cognomeResp = azienda.getCognomeResp();
        telefonoResp = azienda.getTelefonoResp();
        emailResp = azienda.getEmailResp();
        foro = azienda.getForo();
        valutazione = azienda.getValutazione();
        numeroTirocini = azienda.getNumeroTirocini();
        numTiroCompletati = azienda.getNumTiroCompletati();
        idConvenzione = azienda.getIdConvenzione();
        this.dirty = true;
    }
}
