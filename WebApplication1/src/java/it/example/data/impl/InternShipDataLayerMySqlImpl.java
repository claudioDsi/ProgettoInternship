/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;

//classe per le query

import it.example.datamodel.Azienda;
import it.example.datamodel.Convenzione;
import it.example.framework.data.DataLayerMysqlImpl;
import javax.sql.DataSource;
import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Richiesta;
import it.example.datamodel.Tirocinio;
import it.example.datamodel.Tutore;
import it.example.datamodel.Utente;
import it.example.framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author claudio
 */
public class InternShipDataLayerMySqlImpl extends DataLayerMysqlImpl implements InternShipDataLayer{
    
    private PreparedStatement iUtente, uUtente, dUtente;
    private PreparedStatement iAzienda, uAzienda, dAzienda;
    private PreparedStatement iTutore, uTutore,dTutore;
    private PreparedStatement iRichiesta, uRichiesta, dRichiesta;    
    private PreparedStatement iTirocinio, uTirocinio, dTirocinio;
    private PreparedStatement jUtenteRichiesta;
    
    public InternShipDataLayerMySqlImpl(DataSource ds) throws SQLException, NamingException {
        super(ds);
    }
    
    
    @Override
    public void init() throws DataLayerException{
        try{     
            super.init();
            iUtente=connection.prepareStatement("INSERT INTO Utente (Username,Password,Privilegi,Nome,Cognome,DataNasc,LuogoNasc,Residenza,CodiceFisc,Telefono,CorsoLaurea,Handicap,Laurea,Dottorato,ScuolaSpec) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iAzienda=connection.prepareStatement("INSERT INTO Azienda (Username,Password,Privilegi,Status,Nome,RagioneSociale,Indirizzo,PartitaIva,CodiceFiscale,NomeRappr,CognomeRappr,NomeResp,CognomeResp,TelefonoResp,EmailResp,Foro,Valutazione,CodConvenzione) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iTutore=connection.prepareStatement("INSERT INTO Tutore (Nome,Cognome,DataNasc,NumTirocini,Telefono,CodAzienda) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iTirocinio=connection.prepareStatement("INSERT INTO Tirocinio (Luogo,Orario,NumOre,NumMesi,Obiettivi,Modalità,Facilitazione,Settore,IdTutore,IdAzienda) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iRichiesta=connection.prepareStatement("INSERT INTO Richiesta (IdStud,IdTiro,Status, Progetto,Cfu,NomeTutor,CognomeTutor,EmailTutor) VALUES (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            jUtenteRichiesta=connection.prepareStatement("SELECT Nome,Cognome,Residenza,Status,Cfu FROM Utente,Richiesta WHERE IdUtente=IdStudente");
            
            uUtente=connection.prepareStatement("");
            //Tutti i prepared statement
        
        
        }
        catch(SQLException sqlEx){
            throw new DataLayerException("errore query",sqlEx);
        }
    }
    
    public String creaStringQuery(String[] campi){
        String sql="UPDATE Utente SET ";
        for(int i=0;i<campi.length;i++){
            if(i!=campi.length){
               sql+=campi[i]+" = ? ,"; 
            }
            else{
                sql+=campi[i]+" = ? "; 
            }         
        }
        sql+="WHERE IdUtente = ?";
        return sql;
    }
    public String creaQuery(String tab,String id){
        return  "SELECT * FROM "+tab+ " WHERE "+ id+" = ?";
    }

    @Override
    public Utente creaStudente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Azienda creaAzienda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tutore creaTutore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tirocinio creaTirocinio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Richiesta creaRichiesta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Convenzione creaDocumento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Utente getInfoStudente(int idStudente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Azienda getInfoAzienda(int idAzienda) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Richiesta getRichiesta(int idRichiesta) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tirocinio getInfoTirocinio(int idTirocinio) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Convenzione getDocumento(int idDocumento) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Richiesta> getListaRichiesteStudente(int idStudente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tirocinio> getListaTirocini() throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Richiesta> getListaRichiesteTirocinio(int idTirocinio) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
