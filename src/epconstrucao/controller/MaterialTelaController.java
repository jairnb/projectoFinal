/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epconstrucao.controller;

import com.jfoenix.controls.JFXTextField;
import epconstrucao.model.dao.MaterialDAO;
import epconstrucao.model.database.Database;
import epconstrucao.model.database.DatabaseFactory;
import epconstrucao.model.domain.Material;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Sándra Helena
 */
public class MaterialTelaController implements Initializable {

    @FXML
    private TableColumn<Material, String> nomeColuna;
    @FXML
    private TableColumn<Material, Integer> quantidadeColuna;
    @FXML
    private TableColumn<Material, String> medidaColuna;
    @FXML
    private TableColumn<Material, Float> precoColuna;
    @FXML
    private TableView<Material> materialTabela;
    @FXML
    private TableColumn<Material, Integer> codigoColuna;
    @FXML
    private JFXTextField pesquisaTextFiled;

    private List<Material> materialLista;
    private ObservableList<Material> materialObservList;
    MaterialDAO materialDAO = new MaterialDAO();
    
    private final Database _bd = DatabaseFactory.getDatabase();
    private Connection conexao = _bd.conectar();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materialDAO.setConexao(conexao);
        
        carregarTabela();
        
        pesquisaTextFiled.setOnKeyReleased((KeyEvent e) -> {
            pesquisarMaterial();
        });
    }    
    
    @FXML
    public void adicionarMaterialBtn() throws IOException{
        Material material = new Material();
        
        boolean botaoConfirmarClick = showAddEditMaterial(material);
        if(botaoConfirmarClick){
            boolean sucesso = materialDAO.adicoanarMaterial(material);
            if(!sucesso){
                JOptionPane.showMessageDialog(null,"Não Possivel Concluir a operação");
            }else{
                JOptionPane.showMessageDialog(null,"Sucesso");
            }
            carregarTabela();
        }
    }
    
    public void carregarTabela(){
        codigoColuna.setCellValueFactory(new PropertyValueFactory<>("idMaterial"));
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        quantidadeColuna.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        precoColuna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        medidaColuna.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        
        materialLista = materialDAO.ListarMaterial();
        materialObservList = FXCollections.observableArrayList(materialLista);
        materialTabela.setItems(materialObservList);
    }
    
    @FXML
    public void editarMaterial() throws IOException{
        Material material = materialTabela.getSelectionModel().getSelectedItem();
        
        if(material != null){
            boolean botaoConfirmarClick = showAddEditMaterial(material);
            if(botaoConfirmarClick){
                boolean sucesso = materialDAO.editarMaterial(material);
                if(!sucesso){
                    JOptionPane.showMessageDialog(null,"Nome existente");
                }
                carregarTabela();
            }
        }else {
           JOptionPane.showMessageDialog(null,"Selecionar Primeiro");
        }        
    }
    
    
    public boolean showAddEditMaterial(Material material) throws IOException{        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MaterialAdcionarTelaController.class.getResource("/epconstrucao/view/MaterialAdcionarTela.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setResizable(false);
        dialogStage.setTitle("Material");
        
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        
        MaterialAdcionarTelaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMaterial(material);
        
        dialogStage.setAlwaysOnTop(true);
        dialogStage.showAndWait();   
        
        return controller.isBotaoConfirmar();
    }
    
    public void pesquisarMaterial(){
        materialObservList = FXCollections.observableArrayList();
        
        materialLista = materialDAO.ListarMaterial();
        
        for(int i = 0; i < materialLista.size(); i++){
            if(materialLista.get(i).getNome().toLowerCase().contains(pesquisaTextFiled.getText().toLowerCase())){
                materialObservList.add(materialLista.get(i));
                materialTabela.setItems(materialObservList);
            }
        }
    }
}
