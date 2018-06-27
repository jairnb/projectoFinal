/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.dao;

import epconstrucao.model.domain.Material;
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
public class MaterialDAO {
    private Connection conexao;

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    
    public List<Material> ListarMaterial(){
        
        String sql = "SELECT * FROM material";
        List<Material> materias = new ArrayList<>();
        
        try {           
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                Material material = new Material();
                material.setNome(resultado.getString("nome"));
                material.setIdMaterial(resultado.getInt("idMaterial"));
                material.setQuantidade(resultado.getInt("quantidade"));
                material.setUnidadeMedida(resultado.getString("unidadeMedida"));
                material.setPreco(resultado.getFloat("preco"));
                materias.add(material);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return materias;
    }
    
     public boolean adicoanarMaterial(Material material){
        String sql = "INSERT INTO material(nome, quantidade, unidadeMedida, preco) VALUES(?,?,?,?)";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, material.getNome());
            stmt.setInt(2, material.getQuantidade());
            stmt.setString(3, material.getUnidadeMedida());
            stmt.setFloat(4, material.getPreco());
            stmt.execute();           
            return true;
        }catch(SQLException ex){
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }    

    public boolean editarMaterial(Material material) {
        String sql = "UPDATE material SET nome=?, quantidade=?, preco=?, unidadeMedida=? WHERE idMaterial=?";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, material.getNome());
            stmt.setInt(2, material.getQuantidade());
            stmt.setFloat(3, material.getPreco());
            stmt.setString(4, material.getUnidadeMedida());
            stmt.setInt(5, material.getIdMaterial());
            stmt.execute();           
            return true;
        }catch(SQLException ex){
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;    
    }
    
    public boolean editarQuantidadeMaterial(Material material) {
        String sql = "UPDATE quantidade=?, WHERE idMaterial=?";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, material.getQuantidade());
            stmt.setInt(2, material.getIdMaterial());
            stmt.execute();           
            return true;
        }catch(SQLException ex){
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;    
    }
    
    public Material selecionarMaterialUnico(Material material){
        String sql = "SELECT * FROM material where idMaterial=?";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, material.getIdMaterial());
           
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                material.setNome(resultado.getString("nome"));
                material.setIdMaterial(resultado.getInt("idMaterial"));
                material.setQuantidade(resultado.getInt("quantidade"));
                material.setUnidadeMedida(resultado.getString("unidadeMedida"));
                material.setPreco(resultado.getFloat("preco"));
                return material;
            }
        }catch(SQLException ex){
            Logger.getLogger(MaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
