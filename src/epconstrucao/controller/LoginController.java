/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import epconstrucao.model.dao.UtilizadorDAO;
import epconstrucao.model.database.Database;
import epconstrucao.model.database.DatabaseFactory;
import epconstrucao.model.domain.Utilizador;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Jair
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane loginAnchorPane;
    
    @FXML
    private JFXTextField nomeTextField;
    
    @FXML
    private JFXPasswordField senhaTextField;
      
    @FXML
    private Button entrarButton;
    private final UtilizadorDAO utilizadorDAO = new UtilizadorDAO();
    private Utilizador utilizador = new Utilizador();
    private final Database database = DatabaseFactory.getDatabase();
    private final Connection conexao = database.conectar();
    public static String nomeUsuerActualLogado;
    public static String tipoUsuerActualLogado;
    public static Utilizador userAtucalLogado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//Inicio de validacao de campo*****************************************************************************************
       RequiredFieldValidator validator = new RequiredFieldValidator();
       
       nomeTextField.getValidators().add(validator);
       validator.setMessage("Os campos nao podem ser vazios");
       
       nomeTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            
           @Override
           public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               if(!newValue){
                   nomeTextField.validate();
               }
           }
       });
       senhaTextField.getValidators().add(validator);
       validator.setMessage("Os campos nao podem ser vazios");
       
       senhaTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            
           @Override
           public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               if(!newValue){
                   senhaTextField.validate();
               }
           }
       });
       
//FIM DE VALIDACAO DE CAMPO*******************************************************************
       
        utilizadorDAO.setConexao(conexao);
        
        entrarButton.setOnMouseClicked((MouseEvent e) -> {
            try {
                login();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }  
    public void login() throws IOException{
        
        Utilizador utilizadorForm = new Utilizador();
        utilizadorForm.setNome( nomeTextField.getText());
        utilizadorForm.setSenha(senhaTextField.getText());
        
        
        
        utilizador = utilizadorDAO.SelecionarUsuario(utilizadorForm);
        nomeUsuerActualLogado = nomeTextField.getText().toUpperCase();
        tipoUsuerActualLogado = utilizador.getTipo().toUpperCase();
        userAtucalLogado = utilizador;
        
        if(utilizador != null){
            if(utilizador.getSenha().equals(utilizadorForm.getSenha()) && utilizador.getNome().equals(utilizadorForm.getNome())){
                if(utilizador.getTipo().equals("Administrador")){
                  AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/segundoInicialAdmin.fxml"));
                  loginAnchorPane.getChildren().setAll(a);
                }else if(utilizador.getTipo().equals("Vendedor")){
                 AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/epconstrucao/view/SegundoInicialAtend.fxml"));
                 loginAnchorPane.getChildren().setAll(a);
                }             
            } 
        }else{
            JOptionPane.showMessageDialog(null,"Nome ou Senha Invalido");
        }
        
        
    }
}
