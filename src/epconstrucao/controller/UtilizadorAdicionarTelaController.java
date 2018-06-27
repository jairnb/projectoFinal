/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import epconstrucao.model.domain.Utilizador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sándra Helena
 */
public class UtilizadorAdicionarTelaController implements Initializable {

    @FXML
    private JFXPasswordField senhaTextField;
    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXComboBox<String> tipoUtilizadorCombo;    
    
    private ObservableList<String> tipoObservableList;
    private Stage dialogStage;
    private Utilizador utilizador;
    private boolean botaoConfirmar = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboTipo();
        
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
    }    
    
    
    public void carregarComboTipo(){
        tipoObservableList = FXCollections.observableArrayList("Administrador", "Vendedor", "Tecnico");
        tipoUtilizadorCombo.setItems(tipoObservableList);
    }
    
    @FXML
    public void cancelarButton(){
        dialogStage.close();
    }

    
    @FXML
    public void ConfirmarButton(){
        //if(ValidarDados()){
            utilizador.setNome(nomeTextField.getText());
            utilizador.setSenha(senhaTextField.getText());
            utilizador.setTipo(tipoUtilizadorCombo.getValue());
            
            botaoConfirmar = true;            
            dialogStage.close();
       // }
    }
    
    
    private boolean ValidarDados(){
        String errorMessage = "";
        
        if(nomeTextField.getText() == null || nomeTextField.getLength() == 0){
            errorMessage += "Nome Invalido\n";
        }
        if(senhaTextField.getText() == null || senhaTextField.getLength() == 0){
            errorMessage += "Senha Invalido\n";
        }
        if(tipoUtilizadorCombo.getValue() == null){
            errorMessage += "Tipo Invalido\n";
        }
        if(errorMessage.length() == 0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao adicionar");
            alert.setHeaderText("Campos Invalidos");
            alert.setContentText(errorMessage);            
            alert.show();
            return false;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
        this.nomeTextField.setText(utilizador.getNome());
        this.senhaTextField.setText(utilizador.getSenha());
        this.tipoUtilizadorCombo.setValue(utilizador.getTipo());        
    }

    public boolean isBotaoConfirmar() {
        return botaoConfirmar;
    }

    public void setBotaoConfirmar(boolean botaoConfirmar) {
        this.botaoConfirmar = botaoConfirmar;
    }
    
    
    
    
}
