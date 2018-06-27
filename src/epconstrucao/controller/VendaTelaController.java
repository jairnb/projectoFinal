/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXButton;
import epconstrucao.model.dao.MaterialDAO;
import epconstrucao.model.dao.PedidoDAO;
import epconstrucao.model.dao.VendaDAO;
import epconstrucao.model.database.Database;
import epconstrucao.model.database.DatabaseFactory;
import epconstrucao.model.domain.Material;
import epconstrucao.model.domain.Pedido;
import epconstrucao.model.domain.Venda;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sándra Helena
 */
public class VendaTelaController implements Initializable {
    
    @FXML
    private JFXButton btnAdicionar;
    @FXML
    private TableColumn<Venda, String> dataColuna;
    @FXML
    private TableColumn<Venda, Float> valorColuna;
    @FXML
    private AnchorPane VendaAnchorPane;
    @FXML
    private TableView<Venda> vendaTabela;
    @FXML
    private TableColumn<Venda, String> funcionarioColuna;
    @FXML
    private TableColumn<Venda, Integer> codigoColuna;
    @FXML
    private TableColumn<Venda, String> clienteColuna;

    private final Database bd = DatabaseFactory.getDatabase();
    private final Connection conexao = bd.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();    
    private final MaterialDAO materialDAO = new MaterialDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    
    private List<Venda> vendaList;
    private ObservableList<Venda> vendaObserList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConexao(conexao);
        
        carregartabela();
    } 
    
    
    public void carregartabela(){
        dataColuna.setCellValueFactory(new PropertyValueFactory<>("date"));
        funcionarioColuna.setCellValueFactory(new PropertyValueFactory<>("utilizador"));
        codigoColuna.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        clienteColuna.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        valorColuna.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
        vendaList = vendaDAO.ListarVenda();
        vendaObserList = FXCollections.observableArrayList(vendaList);
        vendaTabela.setItems(vendaObserList);        
    }
    
    @FXML
    public void addVenda() throws IOException{
        Venda venda = new Venda();
        List<Pedido> pedidos = new ArrayList<>();
        venda.setPedido(pedidos);
        boolean btnConfimar = showVendaAdd(venda);
        if(btnConfimar){
            try {
                conexao.setAutoCommit(false);
                vendaDAO.setConexao(conexao);
                vendaDAO.inserirVenda(venda);
                pedidoDAO.setConexao(conexao);
                materialDAO.setConexao(conexao);
                for(Pedido p : venda.getPedido()){
                    Material material = p.getMaterial();
                    p.setVenda(vendaDAO.buscarUltimaVenda());
                    pedidoDAO.inserir(p);
                    material.setQuantidade(material.getQuantidade() - p.getQuantidade());
                    materialDAO.editarMaterial(material);
                }
            } catch (SQLException ex) {
                Logger.getLogger(VendaTelaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 
    
     public boolean showVendaAdd(Venda venda) throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(VendaAdicionarTelaController.class.getResource("/epconstrucao/view/VendaAdicionarTela.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Venda");
        dialogStage.setResizable(false);
        
        
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        
        VendaAdicionarTelaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVenda(venda);
        
        
        dialogStage.setAlwaysOnTop(true);
        dialogStage.showAndWait();
        return controller.isBotaoConfirmar();
    }
}
