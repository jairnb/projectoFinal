/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXButton;
import static epconstrucao.controller.LoginController.nomeUsuerActualLogado;
import static epconstrucao.controller.LoginController.tipoUsuerActualLogado;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author ne
 */
public class SegundoInicialAdminController implements Initializable {
    
    @FXML
    private AnchorPane SegundoInicialTela;
    @FXML
    private Label nomeUserActual;
    
    @FXML
    private Label tipoLabel;
    
    @FXML
    private AnchorPane PaginaInicialPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomeUserActual.setText(nomeUsuerActualLogado);
        tipoLabel.setText(tipoUsuerActualLogado);
    }

    public void PaginaInicial() throws IOException  {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/segundoInicialAdmin.fxml"));
        SegundoInicialTela.getChildren().setAll(a);
    }
    
    public void Funcionario() throws IOException  {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/FuncionarioTela.fxml"));
        PaginaInicialPane.getChildren().setAll(a);
    }
 
    public void Fornecedor() throws IOException  { 
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/FornecedorTela.fxml"));
        PaginaInicialPane.getChildren().setAll(a);    
    }
      
    public void Material() throws IOException  {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/MaterialTela.fxml"));
        PaginaInicialPane.getChildren().setAll(a);
    }
    
    public void Utilizador() throws IOException  {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/UtilizadorTela.fxml"));
        PaginaInicialPane.getChildren().setAll(a);
    }
    
    
     public void Cliente() throws IOException  {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/ClienteTela.fxml"));
        PaginaInicialPane.getChildren().setAll(a);
    }
     
      public void Venda() throws IOException  {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/VendaTela.fxml"));
        PaginaInicialPane.getChildren().setAll(a);
    }
    
    
    public void Sair() throws IOException  {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/Login.fxml"));
        SegundoInicialTela.getChildren().setAll(a);
    }
    
    
    
    
    
}
