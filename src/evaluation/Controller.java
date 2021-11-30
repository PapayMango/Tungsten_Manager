package evaluation;

import com.sun.management.GarbageCollectionNotificationInfo;
import database.DataObject;
import database.Tungsten;
import database.hasDataObject;
import home.SceneTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.lang.management.GarbageCollectorMXBean;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements hasDataObject,Initializable{

    @FXML
    private AnchorPane main;

    @FXML
    private AnchorPane details;

    @FXML
    private AnchorPane filter;

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
    private Label location_;

    private Tungsten tungsten;

    private Stage currentWindow;

    @FXML
    private void changePage(){
        System.out.println(location_.getScene().getWindow());
        location_.getScene().getWindow().hide();
        SceneTransition.sceneTransition.getPrevious().show();
    }

    @Override
    public boolean setDataObject(DataObject dataObject) {
        if(dataObject  instanceof Tungsten){
            this.tungsten = (Tungsten) dataObject;
            System.out.println("set data : " + tungsten);
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
        System.out.println(tungsten);
        System.out.println(tungsten.getLot());
        System.out.println(lot);
        lot.setText(tungsten.getLot());
        date.setText(tungsten.getProduct_date().toString());
        product.setText(tungsten.getQuantity() + "kg");
        ph.setText("" + tungsten.getPh());
        shipment.setText(tungsten.getShipment() + "kg");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList details_observableList = ((VBox)details.getChildren().get(0)).getChildren();
        for (int i = 0; i < details_observableList.size() ; i++) {
            ((HBox)details_observableList.get(i)).getStyleClass().add("details");
        }
        ObservableList filter_observableList = ((VBox)filter.getChildren().get(0)).getChildren();
        for (int i = 0; i < filter_observableList.size() ; i++) {
            ((HBox)filter_observableList.get(i)).getStyleClass().add("filter");
        }
    }
}
