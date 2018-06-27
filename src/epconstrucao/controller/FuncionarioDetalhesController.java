/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import epconstrucao.model.domain.Funcionario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jair
 */
public class FuncionarioDetalhesController implements Initializable {

    @FXML
    private Label datanascLabel;
    @FXML
    private Label nifLabel;
    @FXML
    private Label salarioLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label estadoLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label sexoLabel;
    @FXML
    private Label serieCarteira;
    @FXML
    private Button okButton;
        
    private Stage dialogStage;
    private Funcionario funcionario;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        okButton.setOnMouseClicked((MouseEvent e) -> {
            dialogStage.close();
        });
    } 

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        this.datanascLabel.setText(funcionario.getDataNascimento().toString());
        this.emailLabel.setText(funcionario.getEmail());
        this.estadoLabel.setText(funcionario.getEstado());
        this.nifLabel.setText(Integer.toString(funcionario.getNIF()));
        this.nomeLabel.setText(funcionario.getNome());
        this.salarioLabel.setText(Float.toString(funcionario.getSalario()));
        this.sexoLabel.setText(funcionario.getSexo());
        this.serieCarteira.setText(Integer.toString(funcionario.getSerieCarteira()));
    }
    
    
    
    
}
