/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXTextField;
import epconstrucao.model.domain.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
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
        // TODO
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
