/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.example.datamodel;

import java.util.Date;
/**
 *
 * @author claudio
 */
public interface Tutore {
    int getIdTutore();
    
    String getNome();
    void setNome(String nome);
    
    String getCognome();
    void setCognome(String cognome);
    
    String getDataNasc();
    void setDataNasc(String data);
    
    int getNumTirocini();
    void setNumTirocini(int numTirocini);
    
    String getTelefono();
    void setTelefono(String telefono);
    
    int getCodAzienda();
    void setCodAzienda(int codAzienda);
    
    //num tirocini
    
    
    
    
    
    
}
