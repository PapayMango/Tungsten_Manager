package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

import static home.SceneTransition.sceneTransition;

public class Controller {

    private Stage stock_stage;

    @FXML
    private AnchorPane stock;

    @FXML
    private AnchorPane product;

    @FXML
    protected void OnClickedStock(MouseEvent event){
        System.out.println(event);
        stock.getScene().getWindow().hide();
        if(stock_stage == null){
            stock_stage = sceneTransition.transition("../stock/stock.fxml","在庫管理");
        }
        stock_stage.show();
    }

    @FXML
    protected void OnClickedProduct(MouseEvent event){
        System.out.println(event);
    }
}
