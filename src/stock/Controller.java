package stock;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private TableView result_tb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");
    }
}
