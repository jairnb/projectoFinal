/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static epconstrucao.controller.LoginController.userAtucalLogado;
import epconstrucao.model.dao.ClienteDAO;
import epconstrucao.model.dao.MaterialDAO;
import epconstrucao.model.database.Database;
import epconstrucao.model.database.DatabaseFactory;
import epconstrucao.model.domain.Cliente;
import epconstrucao.model.domain.Material;
import epconstrucao.model.domain.Pedido;
import epconstrucao.model.domain.Venda;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Sándra Helena
 */
public class SegundoInicialAtendController implements Initializable {

    @FXML
    private JFXTextField precoTotalTextField;
    @FXML
    private TableColumn<Pedido, Float> precoTotalColuna;
    @FXML
    private JFXComboBox clienteCombo;
    @FXML
    private JFXTextField precoTextField;
    @FXML
    private JFXButton removerProdutoButtom;
    @FXML
    private TableColumn<Pedido, Integer> codigoColuna;
    @FXML
    private Label totalLabel;
    @FXML
    private TableColumn<Pedido, String> nomeMaterialColuna;
    @FXML
    private JFXComboBox produtoCombo;
    @FXML
    private TableView<Pedido> vendaTabela;
    @FXML
    private TableColumn<Pedido, Float> precoColuna;
    @FXML
    private TableColumn<Pedido, Integer> quantidadeColuna;
    @FXML
    private JFXTextField quantidadeTextField;
    @FXML
    private JFXButton adicionarProdutoButton;

    private final Database bd = DatabaseFactory.getDatabase();
    private final Connection conexao = bd.conectar();
    private final MaterialDAO materialDAO = new MaterialDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    
    private List<Material> materialList;
    private List<Cliente> clienteList;
    private ObservableList<Cliente> clienteObserList;
    private ObservableList<Material> materialObserList;
    private ObservableList<Pedido> pedidoObserList;
    
    private Venda venda;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materialDAO.setConexao(conexao);
        clienteDAO.setConexao(conexao);
        
        carregarComboMaterial();
        carregarComboCliente();        
        
        precoTotalColuna.setCellValueFactory(new PropertyValueFactory<>("precoTotal"));
        quantidadeColuna.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        nomeMaterialColuna.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        precoColuna.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
        produtoCombo.setOnAction((Event e) -> {
            prencherMaterialPrecoUnitario();
        });
        
        quantidadeTextField.setOnKeyReleased((KeyEvent e) -> {
            prencherMaterialPrecoTotal();
        });
        
        adicionarProdutoButton.setOnMouseClicked((Event e) -> {
            //prencherTabelaVenda();
        });
    }    
    
    
    public void carregarComboMaterial(){
        materialList = materialDAO.ListarMaterial();
        materialObserList = FXCollections.observableArrayList(materialList);
        produtoCombo.setItems(materialObserList);
    }
    
    public void carregarComboCliente(){
        clienteList = clienteDAO.listarCliente();
        clienteObserList = FXCollections.observableArrayList(clienteList);
        clienteCombo.setItems(clienteObserList);
    }
    
    public void prencherMaterialPrecoUnitario(){
        Material materialCombo = (Material) produtoCombo.getValue();
        Material basedadosMaterial = materialDAO.selecionarMaterialUnico(materialCombo);
        precoTextField.setText(Float.toString(basedadosMaterial.getPreco()));
    }
    
    public void prencherMaterialPrecoTotal(){
        float total;
        int quantidade = Integer.parseInt(quantidadeTextField.getText());
        Material materialCombo = (Material) produtoCombo.getValue();
        Material basedadosMaterial = materialDAO.selecionarMaterialUnico(materialCombo);
        total = quantidade * basedadosMaterial.getPreco();
        if(quantidade > basedadosMaterial.getQuantidade()){
            JOptionPane.showMessageDialog(null, "Quandidade indisponivel \n Stock Atual "+basedadosMaterial.getQuantidade());
        }else{
            precoTotalTextField.setText(Float.toString(total));
        }        
    }
    
    public void prencherTabelaVenda(){
        
//        Pedido pedido = new Pedido();
//        int quantidade = Integer.parseInt(quantidadeTextField.getText());
//        if(produtoCombo.getSelectionModel().getSelectedItem() != null && Integer.parseInt(quantidadeTextField.getText()) >= 1){
//            Material materialCombo = (Material) produtoCombo.getSelectionModel().getSelectedItem();
//            if(quantidade <= materialCombo.getQuantidade()){
//                pedido.setMaterial(materialCombo);
//                pedido.setQuantidade(quantidade);
//                pedido.setValor(materialCombo.getPreco());
//           //     pedido.setPrecoTotal(quantidade * materialCombo.getPreco());
//                venda.getPedido().add(pedido);
////                venda.setUtilizador(userAtucalLogado);
//                venda.setCliente((Cliente) clienteCombo.getSelectionModel().getSelectedItem());
//                venda.setDate(LocalDate.now());
//                pedidoObserList = FXCollections.observableArrayList(venda.getPedido());
//                vendaTabela.setItems(pedidoObserList);
//            }
//        }
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    
}
