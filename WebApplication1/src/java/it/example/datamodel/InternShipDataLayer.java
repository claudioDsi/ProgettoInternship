/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import it.example.framework.data.DataLayer;
import it.example.framework.data.DataLayerException;
import java.util.List;

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
    Convenzione creaDocumento();// da implementare
    
    Utente getInfoStudente(int idStudente) throws DataLayerException;
    Azienda getInfoAzienda(int idAzienda) throws DataLayerException;
    Richiesta getRichiesta(int idRichiesta)throws DataLayerException;
    Tirocinio  getInfoTirocinio(int idTirocinio)throws DataLayerException;    
    Convenzione getDocumento(int idDocumento) throws DataLayerException;
    
    List<Richiesta> getListaRichiesteStudente(int idStudente) throws DataLayerException;//per lo studente
    List<Tirocinio> getListaTirocini() throws DataLayerException; // pannello riepigolativo   
    List<Richiesta> getListaRichiesteTirocinio(int idTirocinio) throws DataLayerException;// per azienda pagina per tirocinio
    
    
    
    
    
    
    
    
}
