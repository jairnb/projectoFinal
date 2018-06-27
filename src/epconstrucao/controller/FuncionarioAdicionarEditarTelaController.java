/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import epconstrucao.model.domain.Funcionario;
import epconstrucao.model.domain.Sexo;
import static java.lang.Float.valueOf;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.ButtonGroup;

/**
 * FXML Controller class
 *
 * @author Jair
 */
public class FuncionarioAdicionarEditarTelaController implements Initializable {

    @FXML
    private DatePicker dataNascDate;
    @FXML
    private JFXTextField nomeTextField;
    @FXML
    private JFXTextField nifTextField;
    @FXML
    private JFXTextField salarioTextFiel;
    @FXML
    private JFXTextField emailTextfield;
    @FXML
    private RadioButton sexoMasRadioBtn;    
    @FXML
    private RadioButton sexoFemRadioBtn;
    @FXML
    private JFXTextField serieCartTextField;
    @FXML
    private JFXComboBox<String> estadoComboBox;

    private Stage dialogStage;
    private Funcionario funcionario;
    private boolean botaoConfirmar = false;
    private ObservableList<String> estadoObservableList;
    private final ToggleGroup groupo = new ToggleGroup();
    private RadioButton radioBtn;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboEstado();
        
        estadoComboBox.setValue("Activo");
        
        sexoFemRadioBtn.setToggleGroup(groupo);
        sexoMasRadioBtn.setToggleGroup(groupo);
    }    

    
    
    
    @FXML
    public void ConfirmarButton(){
            radioBtn = (RadioButton) groupo.getSelectedToggle();
            funcionario.setNome(nomeTextField.getText());
            funcionario.setDataNascimento(dataNascDate.getValue());
            funcionario.setEmail(emailTextfield.getText());
            funcionario.setNIF(Integer.parseInt(nifTextField.getText()));
            funcionario.setSalario(valueOf(salarioTextFiel.getText()));
            funcionario.setSerieCarteira(Integer.parseInt(serieCartTextField.getText()));
            funcionario.setEstado(estadoComboBox.getValue());
            funcionario.setSexo(radioBtn.getText());
            
            botaoConfirmar = true;            
            dialogStage.close();
    }
    
    public void carregarComboEstado(){
        estadoObservableList = FXCollections.observableArrayList("Activo", "Desativo");
        estadoComboBox.setItems(estadoObservableList);
    }
    
    
    
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    

    public boolean isBotaoConfirmar() {
        return botaoConfirmar;
    }

    public void setBotaoConfirmar(boolean botaoConfirmar) {
        this.botaoConfirmar = botaoConfirmar;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
    
    
    
}
