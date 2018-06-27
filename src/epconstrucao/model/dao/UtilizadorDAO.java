/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.dao;

import epconstrucao.model.database.DataBaseMySQL;
import epconstrucao.model.domain.Utilizador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jair
 */
public class UtilizadorDAO {
    
   // private final DataBaseMySQL _bd;
    private Connection conexao;

//    public UtilizadorDAO() {
//        this._bd = new DataBaseMySQL();
//        this.conexao = _bd.conectar();
//    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    
    
    public Utilizador SelecionarUsuario(Utilizador utilizador){
        String sql = "SELECT * FROM utilizador where nome=? and senha=?";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, utilizador.getNome());
            stmt.setString(2, utilizador.getSenha());
           
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                utilizador.setIdUtilizador(resultado.getInt("idutilizador"));
                utilizador.setTipo(resultado.getString("tipo")); 
                return utilizador;
            }
        }catch(SQLException ex){
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Utilizador> ListarUtilizador(){
        
        String sql = "SELECT * FROM utilizador";
        List<Utilizador> utilizadores = new ArrayList<>();
        
        try {
           
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                Utilizador utilizador = new Utilizador();
                utilizador.setNome(resultado.getString("nome"));
                utilizador.setSenha(resultado.getString("senha"));
                utilizador.setTipo(resultado.getString("tipo"));
                utilizador.setIdUtilizador(resultado.getInt("idutilizador"));
                utilizadores.add(utilizador);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return utilizadores;
    }
    
    
    public boolean removerUtilizador(Utilizador utilizador){
        String sql = "DELETE FROM utilizador WHERE idutilizador=?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,utilizador.getIdUtilizador());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean adicoanarUtilizador(Utilizador utilizador){
        String sql = "INSERT INTO utilizador(nome, senha, tipo) VALUES(?,?,?)";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, utilizador.getNome());
            stmt.setString(2, utilizador.getSenha());
            stmt.setString(3, utilizador.getTipo());
            stmt.execute();           
            return true;
        }catch(SQLException ex){
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean editarUsuario(Utilizador utilizador){
        String sql = "UPDATE utilizador SET nome=?, senha=?, tipo=? WHERE idUtilizador=?";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, utilizador.getNome());
            stmt.setString(2, utilizador.getSenha());
            stmt.setString(3, utilizador.getTipo());
            stmt.setInt(4, utilizador.getIdUtilizador());
            stmt.execute();           
            return true;
        }catch(SQLException ex){
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Utilizador buscarUtilizador(Utilizador utilizador) {
        String sql = "SELECT * FROM utilizador where idUtilizador=?";  
        Utilizador utilizadorRetorno = new Utilizador();
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, utilizador.getIdUtilizador());
           
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                utilizador.setIdUtilizador(resultado.getInt("idutilizador"));
                utilizador.setTipo(resultado.getString("tipo")); 
                utilizador.setNome(resultado.getString("nome"));                
                utilizador.setSenha(resultado.getString("senha"));
                utilizadorRetorno = utilizador;
            }
        }catch(SQLException ex){
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return utilizadorRetorno;
    
    }
}
