/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.data.impl;

//classe per le query

import it.example.datamodel.Azienda;
import it.example.framework.data.DataLayerMysqlImpl;
import javax.sql.DataSource;
import it.example.datamodel.InternShipDataLayer;
import it.example.datamodel.Richiesta;
import it.example.datamodel.Tirocinio;
import it.example.datamodel.Tutore;
import it.example.datamodel.Utente;
import it.example.framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author claudio
 */
public class InternShipDataLayerMySqlImpl extends DataLayerMysqlImpl implements InternShipDataLayer{
    
    private PreparedStatement iUtente, uUtente, dUtente, sUtente;
    private PreparedStatement iAzienda, uAzienda, dAzienda, sAzienda;
    private PreparedStatement iTutore, uTutore,dTutore;
    private PreparedStatement iRichiesta, uRichiesta, dRichiesta, sRichiesta;    
    private PreparedStatement iTirocinio, uTirocinio, dTirocinio, sTirocinio;
    private PreparedStatement jUtenteRichiesta;
    


    
    public InternShipDataLayerMySqlImpl(DataSource ds) throws SQLException, NamingException {
        super(ds);
    }
    
    
    @Override
    public void init() throws DataLayerException{
        try{     
            super.init();
            iUtente=connection.prepareStatement("INSERT INTO Utente (Username,Password,Privilegi,Nome,Cognome,DataNasc,LuogoNasc,Residenza,CodiceFisc,Telefono,CorsoLaurea,Handicap,Laurea,Dottorato,ScuolaSpec) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iAzienda=connection.prepareStatement("INSERT INTO Azienda (Username,Password,Privilegi,Status,Nome,RagioneSociale,Indirizzo,PartitaIva,CodiceFiscale,NomeRappr,CognomeRappr,NomeResp,CognomeResp,TelefonoResp,EmailResp,Foro,Valutazione) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iTutore=connection.prepareStatement("INSERT INTO Tutore (Nome,Cognome,DataNasc,NumTirocini,Telefono,CodAzienda) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iTirocinio=connection.prepareStatement("INSERT INTO Tirocinio (Luogo,Orario,NumOre,NumMesi,Obiettivi,Modalità,Facilitazione,Settore,CodTutore,CodAzienda) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iRichiesta=connection.prepareStatement("INSERT INTO Richiesta (CodStudente,CodTirocinio,Status, Progetto,Cfu,NomeTutor,CognomeTutor,EmailTutor) VALUES (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
                       
            jUtenteRichiesta=connection.prepareStatement("SELECT Nome,Cognome,Residenza,Status,Cfu FROM Utente,Richiesta WHERE IdUtente=IdStudente"); 
            
            uUtente=connection.prepareStatement("UPDATE Utente SET Nome=?,Cognome=?,DataNasc=?,LuogoNasc=?, Residenza=?, CodiceFisc=?, Telefono=?, CorsoLaurea=?, Handicap=?, Laurea=?, Dottorato=?, ScuolaSpec=? WHERE idUtente=?");
            sUtente=connection.prepareStatement("SELECT * FROM utente WHERE idUtente = ?");
            sAzienda=connection.prepareStatement("SELECT * FROM Azienda WHERE IdAzienda = ?");
            sRichiesta=connection.prepareStatement("SELECT * FROM Richiesta WHERE CodStudente = ? AND CodTirocinio = ?");
            sTirocinio=connection.prepareStatement("SELECT * FROM Tirocinio WHERE IdTirocinio = ?");
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
        return new UtenteImpl(this);
    }
     public Utente creaStudente(ResultSet rs) throws DataLayerException {
        UtenteImpl u=new UtenteImpl(this);
        try{
         
          u.setUsername(rs.getString("Username")); 
          u.setPassword(rs.getString("Password"));
          u.setPrivilegi(rs.getInt("Privilegi"));
          u.setNome(rs.getString("Nome"));
          u.setCognome(rs.getString("Cognome"));
          u.setDataNasc(rs.getString("DataNasc"));
          u.setLuogoNasc(rs.getString("LuogoNasc"));
          u.setResidenza(rs.getString("Residenza"));
          u.setCodFisc(rs.getString("CodiceFisc"));
          u.setTelefono(rs.getString("Telefono"));
          u.setCdl(rs.getString("CorsoLaurea"));
          u.setHandicap(rs.getBoolean("Handicap"));
          u.setLaurea(rs.getString("Laurea"));
          u.setDottorato(rs.getString("Dottorato"));
          u.setSpecializzazione(rs.getString("ScuolaSpec"));
        }
        catch (SQLException ex) {
            throw new DataLayerException("Unable to create user object form ResultSet", ex);
        }       
         
        return u;
    }

    @Override
    public Azienda creaAzienda() {
        return new AziendaImpl(this);
    }
    
    public Azienda creaAzienda(ResultSet rs)throws DataLayerException{
        AziendaImpl az=new AziendaImpl(this);
        try{
            az.setUsername(rs.getString("Username"));
            az.setPassword(rs.getString("Password"));
            az.setPrivilegi(rs.getInt("Privilegi"));
            az.setNomeAzienda(rs.getString("Nome"));
            az.setRagioneSociale(rs.getString("RagioneSociale"));
            az.setIndirizzo(rs.getString("Indirizzo"));
            az.setPartitaIva(rs.getString("PartitaIva"));
            az.setCodiceFisc(rs.getString("CodiceFiscale"));
            az.setNomeRappr(rs.getString("NomeRappr"));
            az.setCognomeRappr(rs.getString("CognomeRappr"));
            az.setResponsabile(rs.getString("Responsabile"));
            az.setEmailResp(rs.getString("EmailResp"));
            az.setForo(rs.getString("Foro"));
            az.setValutazione(rs.getFloat("Valutazione"));
           
        }
        catch (SQLException sqlEx){
            sqlEx.getMessage();
        }
        
        return az;
    }

    @Override
    public Tutore creaTutore() {
        return new TutoreImpl(this);
    }
    
    public Tutore creaTutore(ResultSet rs) throws DataLayerException{
        TutoreImpl t=new TutoreImpl(this);
        try{
            t.setNome(rs.getString("Nome"));
            t.setCognome(rs.getString("Cognome"));
            t.setDataNasc(rs.getString("DataNasc"));
            t.setNumTirocini(rs.getInt("NumTirocini"));
            t.setTelefono(rs.getString("Telefono"));
            t.setCodAzienda(rs.getInt("CodAzienda"));        
            
            
        }
        catch(SQLException sqlEx){
               sqlEx.getMessage();
        }
        return t;
    }

    @Override
    public Tirocinio creaTirocinio() {
        return new TirocinioImpl(this);
    }
    
    public Tirocinio creaTirocinio(ResultSet rs) throws DataLayerException{
        TirocinioImpl tiro=new TirocinioImpl(this);
        try{
            tiro.setLuogo(rs.getString("Luogo"));
            tiro.setOrario(rs.getString("Orario"));
            tiro.setNumOre(rs.getInt("NumOre"));           
            tiro.setMesi(rs.getString("NumMesi"));
            tiro.setObiettivi(rs.getString("Obiettivi"));
            tiro.setModalità(rs.getString("Modalità"));
            tiro.setModalità(rs.getString("Modalità"));
            tiro.setSettore(rs.getString("Settore"));
            tiro.setIdTutore(rs.getInt("CodTutore"));
            tiro.setIdAzienda(rs.getInt("CodAzienda"));
            
        }
        catch(SQLException sqlEx){
            sqlEx.getMessage();
        }
        return tiro;
    }

    @Override
    public Richiesta creaRichiesta() {
        return new RichiestaImpl(this);
    }
    
    public Richiesta creaRichiesta(ResultSet rs)throws DataLayerException{
        RichiestaImpl r=new RichiestaImpl(this);
        try{
            r.setIdStudente(rs.getInt("CodStudente"));
            r.setIdTirocinio(rs.getInt("CodTirocinio"));
            r.setStatus(rs.getString("Status"));
            r.setCfu(rs.getString("Cfu"));
            r.setNomeTutor(rs.getString("NomeTutor"));
            r.setCognomeTutor(rs.getString("CognomeTutor"));
            r.setEmailTutor(rs.getString("EmailTutor"));
            
        }
        catch(SQLException sqlEx){
            sqlEx.getMessage();
        }
        return r;
    }

   

    @Override
    public Utente getInfoUtente(int idUtente) throws DataLayerException {
        try{
            sUtente.setInt(1, idUtente);
             try(ResultSet rs=sUtente.executeQuery()){
                 while(rs.next()){
                     return creaStudente(rs);
                 }
            }
             catch(SQLException sqlEx){
                 sqlEx.getMessage();
             }
        }
        catch(SQLException ex){
            ex.getMessage();
        }
        
    return null;   
    }

    @Override
    public Azienda getInfoAzienda(int idAzienda) throws DataLayerException {
        try{
            sAzienda.setInt(1, idAzienda);
            try(ResultSet rs=sAzienda.executeQuery()){
                while(rs.next()){
                    return creaAzienda(rs);
                }
            }
            catch(SQLException ex){
                ex.getMessage();
            }
        }
        catch(SQLException ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public Richiesta getInfoRichiesta(int idStudente,int idTirocinio) throws DataLayerException {
        try{
            sRichiesta.setInt(1,idStudente);
            sRichiesta.setInt(2, idTirocinio);
            try(ResultSet rs= sRichiesta.executeQuery()){
                while(rs.next()){
                    return creaRichiesta(rs);
                }
            }
            catch(SQLException ex){
                ex.getMessage();
            }
        }
        catch(SQLException ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public Tirocinio getInfoTirocinio(int idTirocinio) throws DataLayerException {
        try{
            sTirocinio.setInt(1,idTirocinio);            
            try(ResultSet rs= sTirocinio.executeQuery()){
                while(rs.next()){
                    return creaTirocinio(rs);
                }
            }
            catch(SQLException ex){
                ex.getMessage();
            }
        }
        catch(SQLException ex){
            ex.getMessage();
        }
        return null;
    
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
    
    @Override
    public void storeStudente(Utente utente) throws DataLayerException {
        int key = utente.getIdUtente();
        try {
            if (utente.getIdUtente() > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!utente.isDirty()) {
                    return;
                }
                uUtente.setString(1, utente.getNome());
                uUtente.setString(2, utente.getCognome());
                uUtente.setString(3, utente.getDataNasc());
                uUtente.setString(4, utente.getLuogoNasc());
                uUtente.setString(5, utente.getResidenza());
                uUtente.setString(6, utente.getCodFisc());
                uUtente.setString(7, utente.getTelefono());
                uUtente.setString(8, utente.getCdl());
                uUtente.setBoolean(9, utente.getHandicap());
                uUtente.setString(10, utente.getLaurea());
                uUtente.setString(11, utente.getDottorato());
                uUtente.setString(12, utente.getSpecializzazione());
                uUtente.setInt(13, utente.getIdUtente());
                uUtente.executeUpdate();
                
            } else { //insert
                
                iUtente.setString(1, utente.getUsername());
                iUtente.setString(2, utente.getPassword());
                iUtente.setInt(3, utente.getPrivilegi());
                iUtente.setString(4, utente.getNome());
                iUtente.setString(5, utente.getCognome());
                iUtente.setString(6, utente.getDataNasc());
                iUtente.setString(7, utente.getLuogoNasc());
                iUtente.setString(8, utente.getResidenza());
                iUtente.setString(9, utente.getCodFisc());
                iUtente.setString(10, utente.getTelefono());
                iUtente.setString(11, utente.getCdl());
                iUtente.setBoolean(12, utente.getHandicap());
                iUtente.setString(13, utente.getLaurea());
                iUtente.setString(14, utente.getDottorato());
                iUtente.setString(15, utente.getSpecializzazione());
                if (iUtente.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iUtente.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            //restituiamo l'oggetto appena inserito RICARICATO
            //dal database tramite le API del modello. In tal
            //modo terremo conto di ogni modifica apportata
            //durante la fase di inserimento
            //we return the just-inserted object RELOADED from the
            //database through our API. In this way, the resulting
            //object will ambed any data correction performed by
            //the DBMS
            if (key > 0) {
                utente.copyFrom(getInfoUtente(key));
            }
            utente.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store user", ex);
        }
    }
    
}
