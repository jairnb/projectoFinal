/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.model.dao;

import epconstrucao.model.database.DataBaseMySQL;
import epconstrucao.model.domain.Funcionario;
import epconstrucao.model.domain.Sexo;
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
public class FuncionarioDAO {
    
    private Connection conexao;

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean adicoanarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario(nome, dataNascimento, email, sexo, estado, salario, nif, serieCarteira) VALUES(?,?,?,?,?,?,?,?)";        
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setDate(2, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(3, funcionario.getEmail());
            stmt.setString(4, funcionario.getSexo());
            stmt.setString(5, funcionario.getEstado());
            stmt.setFloat(6, funcionario.getSalario());
            stmt.setInt(7, funcionario.getNIF());
            stmt.setInt(8, funcionario.getSerieCarteira());
            stmt.execute();           
            return true;
        }catch(SQLException ex){
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;   
    }
    
    public List<Funcionario> ListarFuncionario(){        
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> funcionarios = new ArrayList<>();        
        try {           
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();            
            while(resultado.next()){
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(resultado.getInt("idFuncionario"));
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setDataNascimento(resultado.getDate("dataNascimento").toLocalDate());
                funcionario.setEmail(resultado.getString("email"));
                funcionario.setSexo(resultado.getString("sexo"));
                funcionario.setEstado(resultado.getString("estado"));
                funcionario.setSalario(resultado.getFloat("salario"));
                funcionario.setNIF(resultado.getInt("nif"));
                funcionario.setSerieCarteira(resultado.getInt("serieCarteira"));                
                
                funcionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return funcionarios;
    }
    
}
