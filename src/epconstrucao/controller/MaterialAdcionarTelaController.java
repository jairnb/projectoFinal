/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import epconstrucao.model.domain.Material;
import static java.lang.Float.valueOf;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sándra Helena
 */
public class MaterialAdcionarTelaController implements Initializable {

    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField precoTextField;
    @FXML
    private JFXTextField quantidadeTextField;
    @FXML
    private JFXComboBox<String> unidadeCombo;
    
    private ObservableList<String> unidadeObservableList;
    
    private Stage dialogStage;
    private Material material;
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
       
       precoTextField.getValidators().add(validator);
       validator.setMessage("Os campos nao podem ser vazios");
       precoTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){            
           @Override
           public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               if(!newValue){
                   precoTextField.validate();
               }
           }
       });
       
       quantidadeTextField.getValidators().add(validator);
       validator.setMessage("Os campos nao podem ser vazios");
       quantidadeTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){            
           @Override
           public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               if(!newValue){
                   quantidadeTextField.validate();
               }
           }
       });
    }    
    
    public void carregarComboTipo(){
        unidadeObservableList = FXCollections.observableArrayList("Kg", "Litro", "Unidade");
        unidadeCombo.setItems(unidadeObservableList);
    }
    
    @FXML
    public void ConfirmarButton(){
        material.setNome(nomeTextField.getText());
        material.setPreco(valueOf(precoTextField.getText()));
        material.setQuantidade(Integer.parseInt(quantidadeTextField.getText()));
        material.setUnidadeMedida(unidadeCombo.getValue());

        botaoConfirmar = true;            
        dialogStage.close();
    }

    @FXML
    public void cancelarButton(){
        dialogStage.close();
    }
    
    
   
    public Stage getDialogStage() {
        return dialogStage;
    }

    public boolean isBotaoConfirmar() {
        return botaoConfirmar;
    }

    public void setBotaoConfirmar(boolean botaoConfirmar) {
        this.botaoConfirmar = botaoConfirmar;
    }
    
    

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
        this.nomeTextField.setText(material.getNome());
        this.precoTextField.setText(Float.toString(material.getPreco()));
        this.quantidadeTextField.setText(Integer.toString(material.getQuantidade()));
        this.unidadeCombo.setValue(material.getUnidadeMedida());
    }
    
    
 
    
    
}
