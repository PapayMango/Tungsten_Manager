package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import static home.SceneTransition.sceneTransition;

public class Controller {

    private Stage stock_stage;

    @FXML
    private AnchorPane stock;

    @FXML
    private AnchorPane product;

    @FXML
    public AnchorPane evaluation;

    @FXML
    public AnchorPane shipment;

    @FXML
    protected void OnClickedStock(MouseEvent event){
        System.out.println(event);
        stock.getScene().getWindow().hide();
        if(stock_stage == null){
            stock_stage = sceneTransition.transition("../stock/stock.fxml","在庫管理",(Stage) stock.getScene().getWindow());
        }
        stock_stage.show();
    }

    @FXML
    protected void OnClickedProduct(MouseEvent event){
        System.out.println(event);
    }

    public void OnClickedEvaluation(MouseEvent mouseEvent) {
        stock.getScene().getWindow().hide();
        if(stock_stage == null){
            stock_stage = sceneTransition.transition("../evaluation/evaluationMain.fxml","評価管理",(Stage) evaluation.getScene().getWindow());
        }
        stock_stage.show();
    }

    public void OnClickedShipment(MouseEvent mouseEvent) {
    }
}
