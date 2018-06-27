/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.database;

import java.sql.Connection;

/**
 *
 * @author Jair
 */
public interface Database {
    public Connection conectar();
    public void desconectar(Connection conn);
}
