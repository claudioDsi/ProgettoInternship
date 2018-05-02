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
    
    Date getDataNasc();
    void setDataNasc(Date data);
    
    //num tirocini
    
    
    
    
    
    
}
