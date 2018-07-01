/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.datamodel;

import java.util.Date;
import org.univaq.tirocinio.framework.data.DataLayer;
import org.univaq.tirocinio.framework.data.DataLayerException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author claudio
 */
public interface InternShipDataLayer extends DataLayer{
    //create functions
    Utente creaStudente();
    Azienda creaAzienda();
    Tutore creaTutore();
    Tirocinio creaTirocinio();
    Richiesta creaRichiesta();
    
    Utente getInfoUtente(int idUtente) throws DataLayerException;
    Utente getInfoUtenteByLogin(String username , String password) throws DataLayerException;
    Azienda getInfoAzienda(int idAzienda) throws DataLayerException;
    Azienda getInfoAziendaByLogin(String username , String password) throws DataLayerException;
    Tutore getInfoTutore(int idTutore) throws DataLayerException;
    Richiesta getInfoRichiesta(int idRichiesta)throws DataLayerException;
    Tirocinio getInfoTirocinio(int idTirocinio)throws DataLayerException;    
    List<Azienda> showListaAziende() throws DataLayerException;
    List<Richiesta> getListaRichiesteStudente(int idStudente) throws DataLayerException;//per lo studente
    List<Tirocinio> getListaTirocini() throws DataLayerException; // pannello riepilogativo
    List<Tirocinio> getListaTirociniByAzienda(Azienda azienda) throws DataLayerException;
    List<Tirocinio> getListaTirociniApprovatiByStudente(int idStudente) throws DataLayerException;
    List<Richiesta> getListaRichiesteTirocinio(int idTirocinio) throws DataLayerException;// per azienda pagina per tirocinio
    List<Tutore> getListaTutoriAzienda(Azienda azienda) throws DataLayerException;//lista tutori di un'azienda
    Richiesta getRichiestaStudenteTirocinio(int idStudente, int idTirocinio) throws DataLayerException;
    
    void storeStudente(Utente utente) throws DataLayerException; //inserimento o modifica studente
    void storeAzienda(Azienda azienda) throws DataLayerException; //inserimento o modifica azienda
    void storeTutore(Tutore tutore) throws DataLayerException; //inserimento o modifica tutore
    void storeTirocinio(Tirocinio tirocinio) throws DataLayerException; //inserimento o modifica tirocinio
    void storeRichiesta(Richiesta richiesta) throws DataLayerException; //inserimento o modifica richiesta
    
    List<Tirocinio> searchTirocini(Map<String, Object> parametri) throws DataLayerException; //ricerca tirocini
    void modifyRequestStatus(Richiesta richiesta) throws DataLayerException;
    void modifyTirocinioStatus(Tirocinio tirocinio, int codTutore) throws DataLayerException;
    void rejectAllRequests(Richiesta richiesta, Tirocinio tirocinio) throws DataLayerException;
    List<Azienda> getBestCompanies() throws DataLayerException;
    List<Tutore> getBestTutors() throws DataLayerException;
    List<Azienda> getCompaniesWithMoreStages() throws DataLayerException;
    void activateAzienda(Azienda azienda) throws DataLayerException;
    void updateDateTirocinio(Date dataInizio, Date dataFine, Tirocinio tirocinio) throws DataLayerException;
    void updateNumTiroAzienda(int num, Azienda azienda) throws DataLayerException;
    void updateNumTiroTutore(int num, Tutore tutore) throws DataLayerException;
    void updateStatusVoto(Tirocinio tirocinio) throws DataLayerException;
    void updateValutazione(Azienda azienda) throws DataLayerException;
    String creaQueryUpdate(String[] campi,String tab,String id);
    
    void modificaUtente(String sql,Utente u) throws DataLayerException;
    Utente showAdminInfo() throws DataLayerException;
    List<Tirocinio> showTirocini() throws DataLayerException;
    List<String> getUsernameAzienda() throws DataLayerException;
    List<String> getUsernameUtenti() throws DataLayerException;
}
