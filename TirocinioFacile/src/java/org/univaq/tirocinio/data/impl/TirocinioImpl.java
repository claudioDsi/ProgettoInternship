/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.data.impl;
import java.util.Date;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.datamodel.Tutore;
import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.framework.data.DataLayerException;
import java.util.List;
/**
 *
 * @author claudio
 */
public class TirocinioImpl implements Tirocinio{
    
    private int idTirocinio;
    private String luogo;
    private String orario;
    private int mesi;
    private int numOre;
    private String obiettivi;
    private String facilitazioni;
    private String modalita;
    private String settore;
    private String titolo;
    private boolean status;
    private boolean statusVoto;
    private Date dataInizio;
    private Date dataFine;
    private boolean statusProgetto;
    private int idProgetto;
    private String descrizione;
    private String risultato;
    private boolean statusResoconto;
    //riferimenti alle altre tabelle
    private Tutore tutore;    
    private int idTutore;
    private Azienda azienda;
    private int idAzienda;
    protected Boolean dirty;   
    private List<Richiesta> listaRichieste;
    //data layer
    protected InternShipDataLayer ownLayer;
    
    public TirocinioImpl(InternShipDataLayer dataLayer){
        ownLayer = dataLayer;
        idTirocinio = 0;
        luogo = "";
        orario = "";
        mesi = 0;
        settore = "";
        titolo = "";
        numOre = 0;
        obiettivi = "";
        facilitazioni = "";
        modalita = "";
        descrizione = "";
        risultato = "";
        statusResoconto = false;
        dataInizio = null;
        dataFine = null;
        statusProgetto = false;
        idProgetto = 0;
        status = false;
        statusVoto = false;
        tutore = null;
        idTutore = 0;
        azienda = null;
        idAzienda = 0;
        listaRichieste = null;
        dirty = false;
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
        this.luogo = luogo;
        this.dirty = true;
    }

    @Override
    public String getOrario() {
       return orario;
    }

    @Override
    public void setOrario(String orario) {
        this.orario = orario;
        this.dirty = true;
    }

    @Override
    public int getMesi(){
        return mesi;
    }

    @Override
    public void setMesi(int mesi){
        this.mesi = mesi;
        this.dirty = true;
    }
    
    @Override
    public String getTitolo(){
        return titolo;
    }

    @Override
    public void setTitolo(String titolo) {
        this.titolo = titolo;
        this.dirty = true;
    }

    @Override
    public int getNumOre() {
       return numOre;
    }

    @Override
    public void setNumOre(int numOre) {
       this.numOre = numOre;
       this.dirty = true;
    }

    @Override
    public String getObiettivi() {
        return obiettivi;
    }

    @Override
    public void setObiettivi(String obiettivi) {
        this.obiettivi = obiettivi;
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
    public boolean getStatusVoto() {
        return statusVoto;
    }

    @Override
    public void setStatusVoto(boolean statusVoto) {
        this.statusVoto=statusVoto;
        this.dirty = true;
    }

    @Override
    public String getModalita() {
        return modalita;
    }

    @Override
    public void setModalita(String modalita) {
        this.modalita = modalita;
        this.dirty = true;
    }

    @Override
    public String getFacilitazioni() {
        return facilitazioni;
    }

    @Override
    public void setFacilitazioni(String facilitazioni) {
        this.facilitazioni = facilitazioni;
        this.dirty = true;
    }

    @Override
    public void setIdTirocinio(int idTirocinio) {
        this.idTirocinio=idTirocinio;
        this.dirty = true;
    }

    @Override
    public Azienda getAzienda() throws DataLayerException{
        return azienda;
    }

    @Override
    public void setAzienda(Azienda azienda) {
        this.azienda=azienda;
        this.dirty = true;
    }

    @Override
    public Tutore getTutore() throws DataLayerException {
        return tutore;
    }

    @Override
    public void setTutore(Tutore tutore) {
        this.tutore=tutore;
        this.dirty = true;
    }

    @Override
    public List<Richiesta> getListaRichieste() throws DataLayerException{
       return listaRichieste;
    }
    
    @Override
    public int getIdTutore(){
        return idTutore;
    }
    
    @Override
    public void setIdTutore(int idTutore){
        this.idTutore=idTutore;
        this.dirty = true;
    }
    
    @Override
    public void setIdAzienda(int idAzienda){
        this.idAzienda=idAzienda;
        this.dirty = true;
    }
    
    @Override
    public int getIdAzienda(){
        return idAzienda;
    }

    @Override
    public String getSettore() {
        return settore;
    }

    @Override
    public void setSettore(String settore) {
       this.settore=settore;
       this.dirty = true;
    }
    
    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) {
       this.descrizione=descrizione;
       this.dirty = true;
    }
    
    @Override
    public String getRisultato() {
        return risultato;
    }

    @Override
    public void setRisultato(String risultato) {
       this.risultato=risultato;
       this.dirty = true;
    }
    
    @Override
    public Date getDataInizio() {
        return dataInizio;
    }

    @Override
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
        this.dirty = true;
    }
    
    @Override
    public Date getDataFine() {
        return dataFine;
    }

    @Override
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
        this.dirty = true;
    }
    
    @Override
    public boolean getStatusProgetto() {
        return statusProgetto;
    }

    @Override
    public void setStatusProgetto(boolean statusProgetto) {
        this.statusProgetto=statusProgetto;
        this.dirty = true;
    }
    
    @Override
    public boolean getStatusResoconto() {
        return statusResoconto;
    }

    @Override
    public void setStatusResoconto(boolean statusResoconto) {
        this.statusResoconto=statusResoconto;
        this.dirty = true;
    }
    
    @Override
    public int getIdProgetto(){
        return idProgetto;
    }
    
    @Override
    public void setIdProgetto(int idProgetto){
        this.idProgetto=idProgetto;
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
    public void copyFrom(Tirocinio tirocinio) throws DataLayerException {
        idTirocinio = tirocinio.getIdTirocinio();
        luogo = tirocinio.getLuogo();
        orario = tirocinio.getOrario();
        numOre = tirocinio.getNumOre();
        mesi = tirocinio.getMesi();
        obiettivi = tirocinio.getObiettivi();
        modalita = tirocinio.getModalita();
        facilitazioni = tirocinio.getFacilitazioni();
        settore = tirocinio.getSettore();
        titolo = tirocinio.getTitolo();
        status = tirocinio.getStatus();
        statusVoto = tirocinio.getStatusVoto();
        idTutore = tirocinio.getIdTutore();
        idAzienda = tirocinio.getIdAzienda();
        dataInizio = tirocinio.getDataInizio();
        dataFine = tirocinio.getDataFine();
        statusProgetto = tirocinio.getStatusProgetto();
        idProgetto = tirocinio.getIdProgetto();
        descrizione = tirocinio.getDescrizione();
        risultato = tirocinio.getRisultato();
        statusResoconto = tirocinio.getStatusResoconto();
        this.dirty = true;
    }
    
}
