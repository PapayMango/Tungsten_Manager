package evaluation;

import database.Tungsten;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label label;

    private Tungsten target;

    @FXML
    private void screen_(MouseEvent mouseEvent){
        System.out.println(mouseEvent);
        label.getScene().getWindow().centerOnScreen();
        label.getScene().getWindow().setX(1.1);
        label.getScene().getWindow().setY(1.1);
        System.out.println(getTungsten());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init evaluation");
    }

    public void setTungsten(Tungsten tungsten){
        this.target = tungsten;
    }

    public Tungsten getTungsten(){
        return this.target;
    }
}
