/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import epconstrucao.model.domain.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sándra Helena
 */
public class ClienteAdicionarTelaController implements Initializable {

    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField nifTextField;
    
    private Stage dialogStage;
    private Cliente cliente;
    private boolean botaoConfirmar = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       RequiredFieldValidator validator = new RequiredFieldValidator();
       NumberValidator numberValidator = new NumberValidator();
       
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
       
       nifTextField.getValidators().add(numberValidator);
       numberValidator.setMessage("NIF é um campo nunmerico");
       nifTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            
           @Override
           public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               if(!newValue){
                   nifTextField.validate();
               }
           }
       });
    }    

    
    @FXML
    public void cancelarButton(){
        dialogStage.close();
    }

    
    @FXML
    public void ConfirmarButton(){
            cliente.setNome(nomeTextField.getText());
            cliente.setNIF(Integer.parseInt(nifTextField.getText()));
            
            botaoConfirmar = true;            
            dialogStage.close();
    }
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isBotaoConfirmar() {
        return botaoConfirmar;
    }

    public void setBotaoConfirmar(boolean botaoConfirmar) {
        this.botaoConfirmar = botaoConfirmar;
    }

    
    
    
}
