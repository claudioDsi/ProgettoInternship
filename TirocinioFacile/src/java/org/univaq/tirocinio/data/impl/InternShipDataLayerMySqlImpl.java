/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.univaq.tirocinio.data.impl;

//classe per le query

import org.univaq.tirocinio.datamodel.Azienda;
import org.univaq.tirocinio.framework.data.DataLayerMysqlImpl;
import javax.sql.DataSource;
import org.univaq.tirocinio.datamodel.InternShipDataLayer;
import org.univaq.tirocinio.datamodel.Richiesta;
import org.univaq.tirocinio.datamodel.Tirocinio;
import org.univaq.tirocinio.datamodel.Tutore;
import org.univaq.tirocinio.datamodel.Utente;
import org.univaq.tirocinio.framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import java.util.Map;
import org.univaq.tirocinio.framework.security.SecurityLayer;

/**
 *
 * @author claudio
 */
public class InternShipDataLayerMySqlImpl extends DataLayerMysqlImpl implements InternShipDataLayer{
    
    private PreparedStatement iUtente, uUtente, dUtente, sUtente, sUtenteLogin;
    private PreparedStatement iAzienda, uAzienda, dAzienda, sAzienda, sAziendaLogin;
    private PreparedStatement iTutore, uTutore,dTutore, sTutore, sTutoriByAzienda;
    private PreparedStatement iRichiesta, uRichiesta, dRichiesta, sRichiesta, sRichiesteByUser, sRichiesteByTirocinio,sRichiestaByStudTiro;    
    private PreparedStatement iTirocinio, uTirocinio, dTirocinio, sTirocinio, sTirociniByAzienda;
    private PreparedStatement jUtenteRichiesta;
    private PreparedStatement orderByDate, searchQuery;
   
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
            iTirocinio=connection.prepareStatement("INSERT INTO Tirocinio (Luogo,Orario,NumOre,NumMesi,Obiettivi,Modalità,Facilitazioni,Settore,Titolo,Status,CodTutore,CodAzienda) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iRichiesta=connection.prepareStatement("INSERT INTO Richiesta (CodStudente,CodTirocinio,Status,Cfu,NomeTutor,CognomeTutor,EmailTutor) VALUES (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
                     
            jUtenteRichiesta=connection.prepareStatement("SELECT Nome,Cognome,Residenza,Status,Cfu FROM Utente,Richiesta WHERE IdUtente=IdStudente"); 
            
            //nuove update
            String[] campiUtente={"Username","Password","Privilegi","Nome","Cognome","DataNasc","LuogoNasc","Residenza","CodiceFisc","Telefono","CorsoLaurea","Handicap","Laurea","Dottorato","ScuolaSpec"};                
            uUtente=connection.prepareStatement(creaQueryUpdate(campiUtente, "Utente", "IdUtente"));
            String[] campiAzienda = {"Username","Password","Privilegi","Nome","RagioneSociale","Indirizzo","PartitaIva","CodiceFiscale","NomeRappr","CognomeRappr","NomeResp","CognomeResp","Telefono","EmailResp","Foro"};
            uAzienda=connection.prepareStatement(creaQueryUpdate(campiAzienda, "Azienda", "IdAzienda"));
            //uAzienda=connection.prepareStatement("UPDATE Azienda SET Nome=?, RagioneSociale=?, Indirizzo=?, PartitaIva=?, CodiceFiscale=?, NomeRappr=?, CognomeRappr=?, NomeResp=?, CognomeResp=?, TelefonoResp=?, EmailResp=?, Foro=? WHERE IdAzienda=?");
            String[] campiTutore={"Nome","Cognome","DataNasc","NumTirocini","Telefono","CodAzienda"};
            uTutore=connection.prepareStatement(creaQueryUpdate(campiTutore, "Tutore", "IdTutore"));
            //uTutore=connection.prepareStatement("UPDATE Tutore SET Nome=?, Cognome=?, DataNasc=?, NumTirocini=?, Telefono=?, CodAzienda=? WHERE idTutore=?");            
            String[] campiTirocinio={"Luogo","Orario","NumOrario","NumOre","NumMesi","Obiettivi","Modalità","Facilitazioni","Settore","CodTutore","CodAzienda"};
            uTirocinio=connection.prepareStatement(creaQueryUpdate(campiTirocinio, "Tirocinio", "IdTirocinio"));            
            //uTirocinio=connection.prepareStatement("UPDATE Tirocinio SET Luogo=?, Orario=?, NumOre=?, NumMesi=?, Obiettivi=?, Modalità=?, Facilitazioni=?, Settore=?, CodTutore=?, CodAzienda=? WHERE idTirocinio=?");
            String[] campiRchiesta={"IdStudente","IdTirocinio","Status","Cfu","NomeTutor","EmailTutor"};
            uRichiesta=connection.prepareStatement(creaQueryUpdate(campiRchiesta, "Richiesta", "IdRichiesta"));
            //uRichiesta=connection.prepareStatement("UPDATE Richiesta SET IdStudente=?, IdTirocinio=?, Status=?, Cfu=?, NomeTutor=?, CognomeTutor=?, EmailTutor=? WHERE idRichiesta=?");
              
            sUtente=connection.prepareStatement(creaQuerySelect("Utente", "IdUtente"));
            sUtenteLogin=connection.prepareStatement("SELECT * FROM Utente WHERE Username = ? AND Password = ?");
            sAzienda=connection.prepareStatement(creaQuerySelect("Azienda", "IdAzienda"));
            sAziendaLogin=connection.prepareStatement("SELECT * FROM Azienda WHERE Username = ? AND Password = ?");
            sTutore=connection.prepareStatement("SELECT * FROM Tutore WHERE IdTutore = ?");
            sTutoriByAzienda=connection.prepareStatement("SELECT IdTutore FROM Tutore WHERE CodAzienda=?");
            sRichiesta=connection.prepareStatement("SELECT * FROM Richiesta WHERE IdRichiesta=?");
            sTirocinio=connection.prepareStatement(creaQuerySelect("Tirocinio","IdTirocinio"));
            sTirociniByAzienda=connection.prepareStatement("SELECT IdTirocinio FROM Tirocinio WHERE CodAzienda=?");
            
            orderByDate=connection.prepareStatement("SELECT * FROM Tirocinio,Azienda WHERE CodAzienda=IdAzienda ORDER BY IdTirocinio DESC LIMIT 10");
            sRichiesteByUser=connection.prepareStatement("SELECT * FROM Utente as u JOIN Richiesta as r ON u.IdUtente=r.CodTirocinio WHERE r.CodStudente=?");
            sRichiesteByTirocinio=connection.prepareStatement("SELECT * FROM Tirocinio as t JOIN Richiesta as r ON t.IdTirocinio=r.CodTirocinio WHERE r.CodTirocinio=?");
            sRichiestaByStudTiro=connection.prepareStatement("SELECT * FROM Richiesta WHERE CodStudente=? && CodTirocinio=?");
            //Tutti i prepared statement
        }
        catch(SQLException sqlEx){
            throw new DataLayerException("errore query",sqlEx);
        }
    }
    
    public String creaQueryUpdate(String[] campi,String tab,String id){
        String sql="UPDATE "+tab+ " SET ";
        for(int i=0;i<campi.length;i++){
            if(i!=campi.length-1){
               sql+=campi[i]+" = ? ,"; 
            }
            else{
                sql+=campi[i]+" = ? "; 
            }         
        }
        sql+="WHERE "+id+ " = ?";
        return sql;
    }
   
    public String creaQuerySelect(String tab,String id){
        return  "SELECT * FROM "+tab+ " WHERE "+ id+" = ?";
    }

    @Override
    public Utente creaStudente() {
        return new UtenteImpl(this);
    }
    
    public Utente creaStudente(ResultSet rs) throws DataLayerException {
        UtenteImpl u = new UtenteImpl(this);
        try{
            u.setIdUtente(rs.getInt("IdUtente"));
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
        AziendaImpl az = new AziendaImpl(this);
        try{
            az.setIdAzienda(rs.getInt("IdAzienda"));
            az.setUsername(rs.getString("Username"));
            az.setPassword(rs.getString("Password"));
            az.setPrivilegi(rs.getInt("Privilegi"));
            az.setNomeAzienda(rs.getString("Nome"));
            az.setRagioneSociale(rs.getString("RagioneSociale"));
            az.setIndirizzo(rs.getString("Indirizzo"));
            az.setPartitaIva(rs.getString("PartitaIva"));
            az.setStatus(rs.getBoolean("Status"));
            az.setCodiceFisc(rs.getString("CodiceFiscale"));
            az.setNomeRappr(rs.getString("NomeRappr"));
            az.setCognomeRappr(rs.getString("CognomeRappr"));
            az.setNomeResp(rs.getString("NomeResp"));
            az.setCognomeResp(rs.getString("CognomeResp"));
            az.setTelefonoResp(rs.getString("TelefonoResp"));
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
        TutoreImpl t = new TutoreImpl(this);
        try{
            t.setIdTutore(rs.getInt("IdTutore"));
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
        TirocinioImpl tiro = new TirocinioImpl(this);
        try{
            tiro.setIdTirocinio(rs.getInt("IdTirocinio"));
            tiro.setLuogo(rs.getString("Luogo"));
            tiro.setOrario(rs.getString("Orario"));
            tiro.setNumOre(rs.getInt("NumOre"));           
            tiro.setMesi(rs.getString("NumMesi"));
            tiro.setObiettivi(rs.getString("Obiettivi"));
            tiro.setModalità(rs.getString("Modalità"));
            tiro.setFacilitazioni(rs.getString("Facilitazioni"));
            tiro.setSettore(rs.getString("Settore"));
            tiro.setTitolo(rs.getString("Titolo")); 
            tiro.setStatus(rs.getBoolean("Status"));
            tiro.setIdTutore(rs.getInt("CodTutore"));
            tiro.setIdAzienda(rs.getInt("CodAzienda"));
            tiro.setAzienda(getInfoAzienda(rs.getInt("CodAzienda")));       
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
        RichiestaImpl r = new RichiestaImpl(this);
        try{
            r.setIdRichiesta(rs.getInt("IdRichiesta"));
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
    public Utente getInfoUtenteByLogin(String username, String password) throws DataLayerException {
        try{
            sUtenteLogin.setString(1, username);
            sUtenteLogin.setString(2, password);
            try(ResultSet rs = sUtenteLogin.executeQuery()){
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
            try(ResultSet rs = sAzienda.executeQuery()){
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
    public Azienda getInfoAziendaByLogin(String username, String password) throws DataLayerException {
        try{
            sAziendaLogin.setString(1, username);
            sAziendaLogin.setString(2, password);
            try(ResultSet rs = sAziendaLogin.executeQuery()){
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
    public Tutore getInfoTutore(int idTutore) throws DataLayerException {
        try{
            sTutore.setInt(1, idTutore);
            try(ResultSet rs = sTutore.executeQuery()){
                while(rs.next()){
                    return creaTutore(rs);
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
    public Richiesta getInfoRichiesta(int idRichiesta) throws DataLayerException {
        try{
            sRichiesta.setInt(1,idRichiesta);
            try(ResultSet rs = sRichiesta.executeQuery()){
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
            try(ResultSet rs = sTirocinio.executeQuery()){
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
        List<Richiesta> result = new ArrayList();
        try{
            sRichiesteByUser.setInt(1, idStudente);
            try (ResultSet rs = sRichiesteByUser.executeQuery()) {
                while (rs.next()) {
                    result.add(getInfoRichiesta(rs.getInt("IdRichiesta")));
                }
            }
        }catch(SQLException ex) {
            throw new DataLayerException("Unable to load requests by tirocinio", ex);
        }
        return result;    }

    @Override
    public List<Tirocinio> getListaTirocini() throws DataLayerException {
        List<Tirocinio> result = new ArrayList();
        try{
            try(ResultSet rs = orderByDate.executeQuery()){
                while(rs.next()){
                    result.add(creaTirocinio(rs));
                }
            }catch(SQLException ex){
                ex.getMessage();
            }
        }catch(DataLayerException ex){
            ex.getMessage();
        }
        return result;   
    }
    
    @Override
    public List<Tirocinio> getListaTirociniByAzienda(Azienda azienda) throws DataLayerException {
        List<Tirocinio> result = new ArrayList();
        try {
            sTirociniByAzienda.setInt(1, azienda.getIdAzienda());
            try (ResultSet rs = sTirociniByAzienda.executeQuery()) {
                while (rs.next()) {
                    result.add(getInfoTirocinio(rs.getInt("IdTirocinio")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load tirocini by company", ex);
        }
        return result;
    }

    @Override
    public List<Richiesta> getListaRichiesteTirocinio(int idTirocinio) throws DataLayerException {
        List<Richiesta> result = new ArrayList();
        try{
            sRichiesteByTirocinio.setInt(1, idTirocinio);
            try (ResultSet rs = sRichiesteByTirocinio.executeQuery()) {
                while (rs.next()) {
                    result.add(getInfoRichiesta(rs.getInt("IdRichiesta")));
                }
            }
        }catch(SQLException ex) {
            throw new DataLayerException("Unable to load requests by tirocinio", ex);
        }
        return result;
    }
    
    @Override
    public Richiesta getRichiestaStudenteTirocinio(int idStudente, int idTirocinio) throws DataLayerException{
        try{            
            sRichiestaByStudTiro.setInt(1, idStudente);
            sRichiestaByStudTiro.setInt(2, idTirocinio);
            try (ResultSet rs = sRichiestaByStudTiro.executeQuery()) {
                while(rs.next()){
                    return creaRichiesta(rs);
                }
            }
        }catch(SQLException ex) {
            throw new DataLayerException("Unable to load requests by tirocinio", ex);
        }
        return null;
    }
    
    @Override 
    public List<Tutore> getListaTutoriAzienda(Azienda azienda) throws DataLayerException{
    List<Tutore> result = new ArrayList();
        try {
            sTutoriByAzienda.setInt(1, azienda.getIdAzienda());
            try (ResultSet rs = sTutoriByAzienda.executeQuery()) {
                while (rs.next()) {
                    result.add(getInfoTutore(rs.getInt("IdTutore")));
                }
            }
        } catch(SQLException ex) {
            throw new DataLayerException("Unable to load tutors by company", ex);
        }
        return result;
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
    
    @Override
    public void storeAzienda(Azienda azienda) throws DataLayerException {
        int key = azienda.getIdAzienda();
        try {
            if (azienda.getIdAzienda() > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!azienda.isDirty()) {
                    return;
                }
                uAzienda.setString(1, azienda.getNomeAzienda());
                uAzienda.setString(2, azienda.getRagioneSociale());
                uAzienda.setString(3, azienda.getIndirizzo());
                uAzienda.setString(4, azienda.getPartitaIva());
                uAzienda.setString(5, azienda.getCodiceFisc());
                uAzienda.setString(6, azienda.getNomeRappr());
                uAzienda.setString(7, azienda.getCognomeRappr());
                uAzienda.setString(8, azienda.getNomeResp());
                uAzienda.setString(9, azienda.getCognomeResp());
                uAzienda.setString(10, azienda.getTelefonoResp());
                uAzienda.setString(11, azienda.getEmailResp());
                uAzienda.setString(12, azienda.getForo());
                uAzienda.setInt(13, azienda.getIdAzienda());
                uAzienda.executeUpdate();
                
            } else { //insert
                iAzienda.setString(1, azienda.getUsername());
                iAzienda.setString(2, azienda.getPassword());
                iAzienda.setInt(3, azienda.getPrivilegi());
                iAzienda.setBoolean(4, azienda.getStatus());
                iAzienda.setString(5, azienda.getNomeAzienda());
                iAzienda.setString(6, azienda.getRagioneSociale());
                iAzienda.setString(7, azienda.getIndirizzo());
                iAzienda.setString(8, azienda.getPartitaIva());
                iAzienda.setString(9, azienda.getCodiceFisc());
                iAzienda.setString(10, azienda.getNomeRappr());
                iAzienda.setString(11, azienda.getCognomeRappr());
                iAzienda.setString(12, azienda.getNomeResp());
                iAzienda.setString(13, azienda.getCognomeResp());
                iAzienda.setString(14, azienda.getTelefonoResp());
                iAzienda.setString(15, azienda.getEmailResp());
                iAzienda.setString(16, azienda.getForo());
                iAzienda.setFloat(17, azienda.getValutazione());
                if (iAzienda.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iAzienda.getGeneratedKeys()) {
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
                azienda.copyFrom(getInfoAzienda(key));
            }
            azienda.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store azienda", ex);
        }
    }
    
    @Override
    public void storeTutore(Tutore tutore) throws DataLayerException {
        int key = tutore.getIdTutore();
        try {
            if (tutore.getIdTutore() > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!tutore.isDirty()) {
                    return;
                }
                uTutore.setString(1, tutore.getNome());
                uTutore.setString(2, tutore.getCognome());
                uTutore.setString(3, tutore.getDataNasc());
                uTutore.setInt(4, tutore.getNumTirocini());
                uTutore.setString(5, tutore.getTelefono());
                uTutore.setInt(6, tutore.getCodAzienda());
                uTutore.setInt(7, tutore.getIdTutore());
                uTutore.executeUpdate();
                
            } else { //insert
                iTutore.setString(1, tutore.getNome());
                iTutore.setString(2, tutore.getCognome());
                iTutore.setString(3, tutore.getDataNasc());
                iTutore.setInt(4, tutore.getNumTirocini());
                iTutore.setString(5, tutore.getTelefono());
                iTutore.setInt(6, tutore.getCodAzienda());
                if (iTutore.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iTutore.getGeneratedKeys()) {
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
                tutore.copyFrom(getInfoTutore(key));
            }
            tutore.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store tutore", ex);
        }
    }
    
    @Override
    public void storeTirocinio(Tirocinio tirocinio) throws DataLayerException {
        int key = tirocinio.getIdTirocinio();
        try {
            if (tirocinio.getIdTirocinio() > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!tirocinio.isDirty()) {
                    return;
                }
                uTirocinio.setString(1, tirocinio.getLuogo());
                uTirocinio.setString(2, tirocinio.getOrario());
                uTirocinio.setInt(3, tirocinio.getNumOre());
                uTirocinio.setString(4, tirocinio.getMesi());
                uTirocinio.setString(5, tirocinio.getObiettivi());
                uTirocinio.setString(6, tirocinio.getModalità());
                uTirocinio.setString(7, tirocinio.getFacilitazioni());
                uTirocinio.setString(8, tirocinio.getSettore());
                uTirocinio.setString(9, tirocinio.getTitolo());
                uTirocinio.setBoolean(10, tirocinio.getStatus());
                uTirocinio.setInt(11, tirocinio.getIdTutore());
                uTirocinio.setInt(12, tirocinio.getIdAzienda());
                uTirocinio.setInt(13, tirocinio.getIdTirocinio());
                uTirocinio.executeUpdate();        
            } else { //insert
                iTirocinio.setString(1, tirocinio.getLuogo());
                iTirocinio.setString(2, tirocinio.getOrario());
                iTirocinio.setInt(3, tirocinio.getNumOre());
                iTirocinio.setString(4, tirocinio.getMesi());
                iTirocinio.setString(5, tirocinio.getObiettivi());
                iTirocinio.setString(6, tirocinio.getModalità());
                iTirocinio.setString(7, tirocinio.getFacilitazioni());
                iTirocinio.setString(8, tirocinio.getSettore());
                iTirocinio.setString(9, tirocinio.getTitolo());
                iTirocinio.setBoolean(10, tirocinio.getStatus());
                iTirocinio.setInt(11, tirocinio.getIdTutore());
                iTirocinio.setInt(12, tirocinio.getIdAzienda());
                if (iTirocinio.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iTirocinio.getGeneratedKeys()) {
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
                tirocinio.copyFrom(getInfoTirocinio(key));
            }
            tirocinio.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to store tirocinio", ex);
        }
    }
    
    @Override
    public void storeRichiesta(Richiesta richiesta) throws DataLayerException {
        int key = richiesta.getIdRichiesta();
        try {
            if (richiesta.getIdRichiesta() > 0) { //update
                //non facciamo nulla se l'oggetto non ha subito modifiche
                //do not store the object if it was not modified
                if (!richiesta.isDirty()) {
                    return;
                }
                uRichiesta.setInt(1, richiesta.getIdStudente());
                uRichiesta.setInt(2, richiesta.getIdTirocinio());
                uRichiesta.setString(3, richiesta.getStatus());
                uRichiesta.setString(4, richiesta.getCfu());
                uRichiesta.setString(5, richiesta.getNomeTutor());
                uRichiesta.setString(6, richiesta.getCognomeTutor());
                uRichiesta.setString(7, richiesta.getEmailTutor());
                uRichiesta.setInt(8, richiesta.getIdRichiesta());
                uRichiesta.executeUpdate();
            } else { //insert
                iRichiesta.setInt(1, richiesta.getIdStudente());
                iRichiesta.setInt(2, richiesta.getIdTirocinio());
                iRichiesta.setString(3, richiesta.getStatus());
                iRichiesta.setString(4, richiesta.getCfu());
                iRichiesta.setString(5, richiesta.getNomeTutor());
                iRichiesta.setString(6, richiesta.getCognomeTutor());
                iRichiesta.setString(7, richiesta.getEmailTutor());
                if (iRichiesta.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iRichiesta.getGeneratedKeys()) {
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
                richiesta.copyFrom(getInfoRichiesta(key));
            }
            richiesta.setDirty(false);
        }catch (SQLException ex) {
            throw new DataLayerException("Unable to store richiesta", ex);
        }
    }
    
    @Override
    public List<Tirocinio> searchTirocini(Map<String, Object> parametri) throws DataLayerException{
        try{
            List<Tirocinio> result = new ArrayList();
            String query = "SELECT * FROM Tirocinio as t JOIN Azienda as a ON t.CodAzienda=a.IdAzienda";
            String azienda = (String)parametri.get("azienda");
            String luogo = (String)parametri.get("luogo");
            String settore = (String)parametri.get("settore");
            String mesi = (String)parametri.get("nummesi");
            String ore = (String)parametri.get("numore");
            int num_mesi, num_ore;
            if(mesi.equals("")){
                num_mesi = 0;
            }else{
                num_mesi = SecurityLayer.checkNumeric(mesi);

            }
            if(ore.equals("")){
                num_ore = 0;
            }else{
                num_ore = SecurityLayer.checkNumeric(ore);
            }            
            if(!azienda.isEmpty() || !luogo.isEmpty() || !settore.isEmpty() || num_mesi!=0 || num_ore!=0){
                query = query +" WHERE";
                if(!azienda.isEmpty()){
                    query = query + " a.Nome='"+azienda+"',";
                }
                if(!luogo.isEmpty()){
                    query = query + " t.Luogo='"+luogo+"',";
                }
                if(!settore.isEmpty()){
                    query = query + " t.Settore='"+settore+"',";
                }
                if(num_ore!=0){
                    query = query + " t.NumOre="+num_ore+",";
                }
                if(num_mesi!=0){
                    query = query + " t.NumMesi="+num_mesi+",";
                }
                query = query.substring(0, query.length() - 1);
            }
            searchQuery = connection.prepareStatement(query);
            try(ResultSet rs = searchQuery.executeQuery()){
                while(rs.next()){
                    result.add(creaTirocinio(rs));
                }
                return result;
            }
            catch(SQLException ex){
                ex.getMessage();
            }        }catch(SQLException ex) {
            throw new DataLayerException("Unable to search tirocini", ex);
        }    
        return null;
    }
    
    
    
    /*@Override
    public void destroy() {
        try {
            //iUtente.close();
            //uUtente.close();
            //dUtente.close();
            //sUtente.close();
            //sUtenteLogin.close();
            
            //iAzienda.close();
            //uAzienda.close();
            //dAzienda.close();
            //sAzienda.close();
            //sAziendaLogin.close();
            
            //iTutore.close();
            //uTutore.close();
            dTutore.close();
            sTutore.close();
            sTutoriByAzienda.close();
            
            iRichiesta.close();
            uRichiesta.close();
            dRichiesta.close();
            sRichiesta.close();

            iTirocinio.close();
            uTirocinio.close();
            dTirocinio.close();
            sTirocinio.close();
            sTirociniByAzienda.close();
            
            jUtenteRichiesta.close();
            orderByDate.close();
            searchQuery.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }*/
    
}