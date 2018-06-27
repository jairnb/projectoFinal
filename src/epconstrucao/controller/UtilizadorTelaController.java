package epconstrucao.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import epconstrucao.model.dao.UtilizadorDAO;
import epconstrucao.model.database.Database;
import epconstrucao.model.database.DatabaseFactory;
import epconstrucao.model.domain.Utilizador;
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


public class UtilizadorTelaController implements Initializable {

    @FXML
    private JFXTextField pesquisaTextFiled;
    @FXML
    private TableColumn<Utilizador, String> ColunaSenha;
    @FXML
    private TableColumn<Utilizador , Integer> ColunaCodigo;
    @FXML
    private TableView<Utilizador> TabelaUtilizador;
    @FXML
    private TableColumn<Utilizador, String> ColunaNome;
    
    @FXML
    private TableColumn<Utilizador, String> ColunaTipo;
    
    private List<Utilizador> listUtilizador;
    private ObservableList<Utilizador> verListaUtilizador;    
    private final UtilizadorDAO utilizadorDAO = new UtilizadorDAO();
    
    private final Database _bd =  DatabaseFactory.getDatabase();
    private final Connection conexao = _bd.conectar();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        utilizadorDAO.setConexao(conexao);
        
        CarregarTabelaUtilizador();
        
        pesquisaTextFiled.setOnKeyReleased((KeyEvent e) -> {
            pesquisarUtilizador();
        });
    }
    
    public void CarregarTabelaUtilizador(){
     ColunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
     ColunaSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
     ColunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
     ColunaCodigo.setCellValueFactory(new PropertyValueFactory<>("idUtilizador"));
     
     listUtilizador = utilizadorDAO.ListarUtilizador();
     verListaUtilizador = FXCollections.observableArrayList(listUtilizador);
     
     TabelaUtilizador.setItems(verListaUtilizador);
        
    }
    
    @FXML
    public void adicionarUtilizador() throws IOException{
        Utilizador utilizador = new Utilizador();
        
        boolean botaoConfirmarClick = showUtilizadoresAdd(utilizador);
        if(botaoConfirmarClick){
            boolean sucesso = utilizadorDAO.adicoanarUtilizador(utilizador);
            if(!sucesso){
                JOptionPane.showMessageDialog(null,"Não Possivel Concluir a operação");
            }else{
                JOptionPane.showMessageDialog(null,"Sucesso");
            }
            CarregarTabelaUtilizador();
        }
    }
    
    @FXML
    public void removerUtilizador(){
        Utilizador utilizador = TabelaUtilizador.getSelectionModel().getSelectedItem();
        
        if(utilizador != null ){
            int selectedOption = JOptionPane.showConfirmDialog(null,"Tens certeza que queres remover?","Remover Utilizador",JOptionPane.YES_NO_OPTION);
                if(selectedOption== JOptionPane.YES_OPTION){
                    utilizadorDAO.removerUtilizador(utilizador);
                    CarregarTabelaUtilizador();
                    
                }           
        }else {
           JOptionPane.showMessageDialog(null,"Selecionar Primeiro");
        }
    }
    
    @FXML
    public void editarUtilizador() throws IOException{
        Utilizador utilizador = TabelaUtilizador.getSelectionModel().getSelectedItem();
        
        if(utilizador != null){
            boolean botaoConfirmarClick = showUtilizadoresAdd(utilizador);
            if(botaoConfirmarClick){
                boolean sucesso = utilizadorDAO.editarUsuario(utilizador);
                if(!sucesso){
                    JOptionPane.showMessageDialog(null,"Nome existente");
                }
                CarregarTabelaUtilizador();
            }
        }else {
           JOptionPane.showMessageDialog(null,"Selecionar Primeiro");
        }        
    }
    
    public boolean showUtilizadoresAdd(Utilizador utilizador) throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(UtilizadorAdicionarTelaController.class.getResource("/epconstrucao/view/UtilizadorAdicionarTela.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Utilizador");
        dialogStage.setResizable(false);
        
        
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        
        UtilizadorAdicionarTelaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUtilizador(utilizador);
        
        
        dialogStage.setAlwaysOnTop(true);
        dialogStage.showAndWait();
        return controller.isBotaoConfirmar();
    }
    
    public void pesquisarUtilizador(){
        verListaUtilizador = FXCollections.observableArrayList();
        
        listUtilizador = utilizadorDAO.ListarUtilizador();
        
        for(int i = 0; i < listUtilizador.size(); i++){
            if(listUtilizador.get(i).getNome().toLowerCase().contains(pesquisaTextFiled.getText().toLowerCase())){
                verListaUtilizador.add(listUtilizador.get(i));
                TabelaUtilizador.setItems(verListaUtilizador);
            }
        }
    }
}
