/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import epconstrucao.model.dao.ClienteDAO;
import epconstrucao.model.database.Database;
import epconstrucao.model.database.DatabaseFactory;
import epconstrucao.model.domain.Cliente;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
public class ClienteTelaController implements Initializable {

    @FXML
    private TableColumn<Cliente, Integer> nifColuna;
    @FXML
    private TableColumn<Cliente, String> nomeColuna;    
    @FXML
    private TableView<Cliente> clienteTabela;
    @FXML
    private TableColumn<Cliente, Integer> codigoColuna;
    
    
    private List<Cliente> clienteList;
    private ObservableList<Cliente> clienteObserList;
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    private final Database bd = DatabaseFactory.getDatabase();
    private final Connection conexao = bd.conectar();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConexao(conexao);
        
        carregarTabela();
    }   
    
    public void carregarTabela(){
        nifColuna.setCellValueFactory(new PropertyValueFactory<>("NIF"));
        codigoColuna.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        clienteList = clienteDAO.listarCliente();
        
        clienteObserList = FXCollections.observableArrayList(clienteList);
        clienteTabela.setItems(clienteObserList);
    }
    
    @FXML
    public void adicionarCliente() throws IOException{
        Cliente cliente = new Cliente();
        
        boolean botaoConfirmarClick = showClienteAdd(cliente);
        if(botaoConfirmarClick){
            boolean sucesso = clienteDAO.adicoanarCliente(cliente);
            if(!sucesso){
                JOptionPane.showMessageDialog(null,"Não Possivel Concluir a operação");
            }else{
                JOptionPane.showMessageDialog(null,"Sucesso");
            }
            carregarTabela();
        }
    }
    
    public boolean showClienteAdd(Cliente cliente) throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(ClienteAdicionarTelaController.class.getResource("/epconstrucao/view/ClienteAdicionarTela.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Utilizador");
        dialogStage.setResizable(false);
        
        
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        
        ClienteAdicionarTelaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);
        
        
        dialogStage.setAlwaysOnTop(true);
        dialogStage.showAndWait();
        return controller.isBotaoConfirmar();
    }
}
