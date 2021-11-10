package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Button nextButton;

    @FXML
    void OnclickedNextButton(ActionEvent event){
//        nextButton.getScene().getWindow().hide();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,1000,800);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("transition");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
