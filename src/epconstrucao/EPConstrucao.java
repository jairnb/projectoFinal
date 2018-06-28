
package epconstrucao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class EPConstrucao extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        Image image = new Image("/epconstrucao/image/logo.png");
        Scene scene = new Scene(root);
        
        stage.setTitle("EP Construções");
        stage.getIcons().add(image);
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();
        
        
        
    }

    
    public static void main(String[] args) {
        
        //LocalDateTime dataSistema = LocalDateTime.now();   
       // if(!(dataSistema.getDayOfWeek().toString().equals("SUNDAY") || dataSistema.getDayOfWeek().toString().equals("SATURDAY")|| dataSistema.getHour() > 22 || dataSistema.getHour() < 7 )){
            launch(args);
        //}else{
        //    JOptionPane.showMessageDialog(null,"Dia ou Hora Invalido");
        //}
    }
    
}
