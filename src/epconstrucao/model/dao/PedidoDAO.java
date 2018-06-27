/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.dao;

import epconstrucao.model.domain.Material;
import epconstrucao.model.domain.Pedido;
import epconstrucao.model.domain.Venda;
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
public class PedidoDAO {
    private Connection conexao;

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    
    
    public Pedido buscarPedido(Pedido pedido){
        String sql = "SELECT * FROM pedido WHERE idPedido=?";
        Pedido pedidoRetorno = new Pedido();
        
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, pedido.getIdPedido());
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                Material material = new Material();
                Venda venda = new Venda();
                
                pedido.setIdPedido(resultado.getInt("idPedido"));
                pedido.setValor(resultado.getFloat("valor"));
                pedido.setQuantidade(resultado.getInt("quantidade"));
                
                material.setIdMaterial(resultado.getInt("idMaterial"));
                venda.setIdVenda(resultado.getInt("idVenda"));
                
                MaterialDAO materialDAO = new MaterialDAO();
                materialDAO.setConexao(conexao);
                material = materialDAO.selecionarMaterialUnico(material);
                
                VendaDAO vendaDAO = new VendaDAO();
                vendaDAO.setConexao(conexao);
                venda = vendaDAO.buscarVenda(venda);
                
                pedido.setMaterial(material);
                pedido.setVenda(venda);
                
                pedidoRetorno = pedido;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return pedidoRetorno;
    }
    
    
    
     public List<Pedido> buscarPorVenda(Venda venda){
        String sql = "SELECT * FROM pedido WHERE idVenda=?";
        List<Pedido> pedidoRetorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, venda.getIdVenda());
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                Material material = new Material();
                Venda v = new Venda();
                Pedido pedido = new Pedido();
                
                pedido.setIdPedido(resultado.getInt("idPedido"));
                pedido.setValor(resultado.getFloat("valor"));
                pedido.setQuantidade(resultado.getInt("quantidade"));
                
                material.setIdMaterial(resultado.getInt("idMaterial"));
                v.setIdVenda(resultado.getInt("idVenda"));
                
                MaterialDAO materialDAO = new MaterialDAO();
                materialDAO.setConexao(conexao);
                material = materialDAO.selecionarMaterialUnico(material);
                
                //VendaDAO vendaDAO = new VendaDAO();
                //vendaDAO.setConexao(conexao);
                //venda = vendaDAO.buscarVenda(venda);
                
                pedido.setMaterial(material);
                pedido.setVenda(v);
                
                pedidoRetorno.add(pedido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return pedidoRetorno;
    }

    public boolean inserir(Pedido p) {
        String sql = "INSERT INTO pedido(quantidade, valor, idMaterial, idVenda) VALUES(?,?,?,?)";
        
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, p.getQuantidade());
            stmt.setFloat(1, p.getValor());
            stmt.setInt(3, p.getMaterial().getIdMaterial());
            stmt.setInt(1, p.getVenda().getIdVenda());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
