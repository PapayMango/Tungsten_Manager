package evaluation;

import database.DataObject;
import database.Tungsten;
import database.hasDataObject;
import home.SceneTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements hasDataObject {

    @FXML
    private AnchorPane main;

    @FXML
    private Label lot;

    @FXML
    private Label date;

    @FXML
    private Label product;

    @FXML
    private Label ph;

    @FXML
    private Label concentration;

    @FXML
    private Label shipment;

    @FXML
    private Label location;

    private Tungsten tungsten;

    private Stage parent;

    @FXML
    private void changePage(){
        SceneTransition.sceneTransition.changeScene("../stock/stock.fxml",main.getScene());
    }

    @Override
    public boolean setDataObject(DataObject dataObject) {
        if(dataObject  instanceof Tungsten){
            this.tungsten = (Tungsten) dataObject;
            setParameters(tungsten);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public DataObject getDataObject() {
        return tungsten;
    }

    private void setParameters(Tungsten tungsten){
        lot.setText(tungsten.getLot());
        date.setText(tungsten.getProduct_date().toString());
        product.setText("" + tungsten.getQuantity());
        ph.setText("" + tungsten.getPh());
        shipment.setText("" + tungsten.getShipment());
    }
}
