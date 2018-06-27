/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import epconstrucao.model.dao.FuncionarioDAO;
import epconstrucao.model.database.Database;
import epconstrucao.model.database.DatabaseFactory;
import epconstrucao.model.domain.Funcionario;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Sándra Helena
 */
public class FuncionarioTelaController implements Initializable {
    
    @FXML
    private TableView<Funcionario> FuncionarioTabela;
    @FXML
    private TableColumn<Funcionario, String> nomeColuna;
    @FXML
    private TableColumn<Funcionario, String> sexoColuna;
    @FXML
    private TableColumn<Funcionario, String> dataNascColuna;
    @FXML
    private TableColumn<Funcionario, String> estadoColuna;    
    @FXML
    private TableColumn<Funcionario, Integer> codigoColuna;    
    
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private List<Funcionario> funcionarioList;
    private ObservableList<Funcionario> funcionarioObsevableList;
    
    private final Database _bd =  DatabaseFactory.getDatabase();
    private final Connection conexao = _bd.conectar();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       funcionarioDAO.setConexao(conexao);
       
       CarregarTabelaFuncionario();
        
    } 
    
    public void CarregarTabelaFuncionario(){
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        sexoColuna.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        dataNascColuna.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        estadoColuna.setCellValueFactory(new PropertyValueFactory<>("estado"));
        codigoColuna.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));

        funcionarioList = funcionarioDAO.ListarFuncionario();
        funcionarioObsevableList = FXCollections.observableArrayList(funcionarioList);

        FuncionarioTabela.setItems(funcionarioObsevableList);
    }
    
    @FXML
    public void adicionarFuncionario() throws IOException{
        Funcionario funcionario = new Funcionario();
        
        boolean botaoConfirmarClick = showFuncionario(funcionario);
        if(botaoConfirmarClick){
            boolean sucesso = funcionarioDAO.adicoanarFuncionario(funcionario);
            if(!sucesso){
                JOptionPane.showMessageDialog(null,"Não Possivel Concluir a operação");
            }
            CarregarTabelaFuncionario();
        }
    }
    
    @FXML
    public void detalhesAlunos() {
        Funcionario funcionario = FuncionarioTabela.getSelectionModel().getSelectedItem();
        
        if(funcionario != null){
            try {
                shoowDetahlesFuncionario(funcionario);
            } catch (IOException ex) {
                Logger.getLogger(FuncionarioTelaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);            
            alert.setContentText("Escolha um aluno");
            alert.show();
        }
    }
   
    public boolean showFuncionario(Funcionario funcionario) throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(FuncionarioAdicionarEditarTelaController.class.getResource("/epconstrucao/view/FuncionarioAdicionarEditarTela.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Funcionario");
        dialogStage.setResizable(false);
        
        
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        
        FuncionarioAdicionarEditarTelaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFuncionario(funcionario);
        
        
        dialogStage.setAlwaysOnTop(true);
        dialogStage.showAndWait();
        return controller.isBotaoConfirmar();
    }

    public void shoowDetahlesFuncionario(Funcionario funcionario) throws IOException{        
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(FuncionarioDetalhesController.class.getResource("/epconstrucao/view/FuncionarioDetalhes.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setResizable(false);
        dialogStage.setTitle("Detalhes Funcionario");
        
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        
        FuncionarioDetalhesController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFuncionario(funcionario);
        
        dialogStage.setAlwaysOnTop(true);
        dialogStage.showAndWait();      
    }
    
    
}
