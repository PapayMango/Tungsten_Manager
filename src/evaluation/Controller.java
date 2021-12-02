package evaluation;

import com.sun.management.GarbageCollectionNotificationInfo;
import database.*;
import home.SceneTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.lang.management.GarbageCollectorMXBean;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements hasDataObject,Initializable{

    private ObservableList<Evaluation> observableList;

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

    @FXML
    private ComboBox<String> deodorize;

    @FXML
    private ComboBox<String> methylene;

    @FXML
    private ComboBox<String> cockroach;

    @FXML
    private TableColumn column_concentration;

    @FXML
    private TableColumn column_ph;

    @FXML
    private TableColumn column_additive;

    @FXML
    private TableColumn column_binder;

    @FXML
    private TableColumn column_deodorize;

    @FXML
    private TableColumn column_methylene;

    @FXML
    private TableColumn column_cockroach;

    @FXML
    private TableColumn column_shipment;

    @FXML
    private TableColumn column_date;

    @FXML
    private TableView result_tb;

    private ArrayList<ComboBox<String>> qualities = new ArrayList<>();

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
        concentration.setText(tungsten.getConcentration() + " wt%");
        location_.setText(tungsten.getLocation());
        ArrayList<? extends DataObject>arrayList = new ArrayList<>();

        try {
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Evaluation, Evaluation.createSelectSQL("lot = '" + tungsten.getLot() + "'"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(arrayList.size());

        observableList = FXCollections.observableList((ArrayList<Evaluation>)arrayList);
        result_tb.getColumns().clear();
        result_tb.setItems(observableList);
        result_tb.getColumns().addAll(column_concentration,column_ph,column_additive,column_binder,column_deodorize,column_methylene,column_cockroach,column_shipment,column_date);

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

        String[] qualitiy_str = {"-","A","B","C","D"};

        qualities.add(deodorize);
        qualities.add(methylene);
        qualities.add(cockroach);

        for (ComboBox<String> comboBox:qualities){
            comboBox.getItems().addAll(qualitiy_str);
            comboBox.getSelectionModel().selectFirst();
        }

//        ArrayList<? extends DataObject>arrayList = new ArrayList<>();
//
//        try {
//            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Evaluation, Evaluation.createSelectSQL());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(arrayList.size());
//
//        observableList = FXCollections.observableList((ArrayList<Evaluation>)arrayList);

        column_concentration.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("concentration"));
        column_ph.setCellValueFactory(new PropertyValueFactory<Evaluation,Integer>("ph"));
        column_additive.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("additive"));
        column_binder.setCellValueFactory(new PropertyValueFactory<Evaluation,Float>("binder"));
        column_deodorize.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("deodorize"));
        column_methylene.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("methylene"));
        column_cockroach.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("cockroach"));
        column_shipment.setCellValueFactory(new PropertyValueFactory<Evaluation,Integer>("shipment"));
        column_date.setCellValueFactory(new PropertyValueFactory<Evaluation, Date>("update_date"));

    }
}
