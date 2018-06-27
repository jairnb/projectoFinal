/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.database;

/**
 *
 * @author Jair
 */
public class DatabaseFactory {
    public static Database getDatabase(){        
        return new DataBaseMySQL();        
    }
}
