/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.dao;

import epconstrucao.model.domain.Cliente;
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
public class ClienteDAO {
    
    private Connection conexao;

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    
    
    public List<Cliente> listarCliente(){
        String sgl = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conexao.prepareStatement(sgl);
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultado.getInt("idCliente"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setNIF(resultado.getInt("nif"));
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientes;
    }
    
    public boolean adicoanarCliente(Cliente cliente){
        String sql = "INSERT INTO cliente(nome, nif) VALUES(?,?)";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setInt(2, cliente.getNIF());
            stmt.execute();           
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public Cliente buscarCliente(Cliente cliente){
        String sgl = "SELECT * FROM cliente WHERE idCliente=?";
        Cliente clienteRetorno = new Cliente();
        try {
            PreparedStatement stmt = conexao.prepareStatement(sgl);
            stmt.setInt(1, cliente.getIdCliente());
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                cliente.setIdCliente(resultado.getInt("idCliente"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setNIF(resultado.getInt("nif"));
                clienteRetorno = cliente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clienteRetorno;
    }
}
