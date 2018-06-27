/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.dao;

import epconstrucao.model.domain.Cliente;
import epconstrucao.model.domain.Pedido;
import epconstrucao.model.domain.Utilizador;
import epconstrucao.model.domain.Venda;
import java.sql.Connection;
import java.sql.Date;
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
public class VendaDAO {
    private Connection conexao;

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    
    public List<Venda> ListarVenda(){        
        String sql = "SELECT * FROM venda";
        List<Venda> vendas = new ArrayList<>();       
        
        try {           
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                Venda venda = new Venda();
                Cliente cliente = new Cliente();
                Utilizador utilizador = new Utilizador();
                List<Pedido> pedidos = new ArrayList<>();
                
                venda.setIdVenda(resultado.getInt("idVenda"));
                venda.setDate(resultado.getDate("data").toLocalDate());
                venda.setValor(resultado.getFloat("preco"));
                
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConexao(conexao);
                cliente = clienteDAO.buscarCliente(cliente);
                
                PedidoDAO pedidoDAO = new PedidoDAO();
                pedidoDAO.setConexao(conexao);
                pedidos = pedidoDAO.buscarPorVenda(venda);
                
                UtilizadorDAO utilizadorDAO = new UtilizadorDAO();
                utilizadorDAO.setConexao(conexao);
                utilizador = utilizadorDAO.buscarUtilizador(utilizador);
                
                venda.setCliente(cliente);
                venda.setPedido(pedidos);
                venda.setUtilizador(utilizador);
                
                vendas.add(venda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return vendas;
    }
    
    public Venda buscarVenda(Venda venda){
        String sql = "SELECT * FROM venda WHERE idVenda=?";
        Venda vendaRetorno = new Venda();        
        
        try {           
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, venda.getIdVenda());
            ResultSet resultado = stmt.executeQuery();            
            while(resultado.next()){
                Cliente cliente = new Cliente();
                Utilizador utilizador = new Utilizador();
                venda.setIdVenda(resultado.getInt("idVenda"));
                venda.setDate(resultado.getDate("data").toLocalDate());
                venda.setValor(resultado.getFloat("valor"));
                
                cliente.setIdCliente(resultado.getInt("idCliente"));
                utilizador.setIdUtilizador(resultado.getInt("idUtilizador"));
                
                venda.setCliente(cliente);
                venda.setUtilizador(utilizador);
                
                vendaRetorno = venda;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return vendaRetorno;    
    }
 
    
    public boolean inserirVenda(Venda venda){
        String sql = "INSERT INTO venda (data, idCiente, idUtilizador, preco) VALUES (?,?,?,?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(venda.getDate()));
            stmt.setInt(2, venda.getCliente().getIdCliente());
            stmt.setInt(3, venda.getUtilizador().getIdUtilizador());
            stmt.setFloat(4, venda.getValor());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Venda buscarUltimaVenda(){
        String sql = "SELECT max(idVenda) FROM venda";
        Venda venda = new Venda();
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();     
            if(resultado.next()){
                venda.setIdVenda(resultado.getInt("idVenda"));
                return venda;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return venda;
    }
}
